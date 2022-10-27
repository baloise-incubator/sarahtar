package ch.baloise.sarahtar.presenter.service;

import com.coremedia.iso.IsoFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VideoPlayer {

    private static final String MPLAYER_START = "mplayer -slave -nolirc -fs -fixed-vo -idle -quiet -input file=player_in > mplayer.log";
    private static final String BASE_DIR = "/usr/bin/sarahtar/";
    private static final String VIDEO_DIR = BASE_DIR+"videos/";

    private Process mplayer = null;

    Logger logger = LoggerFactory.getLogger(VideoPlayer.class);

    public VideoPlayer() {
        logger.info("Starting Video Service");
        ProcessBuilder pb = new ProcessBuilder(MPLAYER_START.split( " "));
        // local display
        pb.environment().put("DISPLAY", ":0");
        try {
            mplayer = pb.start();
            logger.info("Mplayer process started with pid: " + mplayer.pid());
            mplayerDo("set_property loop 0");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void playVideo(String speechFileName, String idleFileName) {
        logger.info("Received play command for video: " + speechFileName);

        if (!isUnix()) {
            return;
        }
        long duration = getMp4Duration(speechFileName);
        logger.info("Duration of Video: "+duration);

        mplayerDo("loadfile "+VIDEO_DIR+speechFileName);

        logger.info("Sleeping for : "+duration+ "milliseconds");
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        mplayerDo("loadfile "+VIDEO_DIR+idleFileName);
    }

    private long getMp4Duration(String speechFileName) {
        File file = new File(VIDEO_DIR+speechFileName);
        if (Files.notExists(file.toPath()) || !Files.isReadable(file.toPath())) {
            logger.warn(" The file path does not exist or is unreadable  {}", file.toPath());
            return 0;
        }
        try {
            IsoFile isoFile = new IsoFile(file.getPath());
            long duration = isoFile.getMovieBox().getMovieHeaderBox().getDuration();
            long timescale = isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
            long dur = duration / timescale;
            logger.info("Sleeping speech video for duration: "+ dur*1000);
            return dur*1000;
        } catch (IOException e) {
            logger.error(" Read MP4 Error in file length ", e);
            return 0;
        }
    }


    private static boolean isUnix() {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.contains("nix") || OS.contains("nux"));
    }

    private void mplayerDo(String command) {
        logger.info("Using command: " + command);

        Path fileName = Path.of(BASE_DIR+"player_in");

        try {
            Files.writeString(fileName, command+"\n");
        } catch (IOException e) {
            logger.error("Could not write into fifo file");
            throw new RuntimeException(e);
        }

    }


}