package ch.baloise.sarahtar.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VideoPlayer {

    private static final String MPLAYER_START = "mplayer -slave -nolirc -fs -fixed-vo -idle -quiet -input file=player_in > mplayer.log";
    private static final String HOMEDIR = "/usr/bin/sarahtar/videos/";

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
    public void playVideo(String speechFileName, String idleFileName) throws IOException {
        logger.info("Received play command for video: " + speechFileName);

        if (!isUnix()) {
            return;
        }

        mplayerDo("loadfile /usr/bin/sarahtar/videos/"+speechFileName);

        logger.info("Sleeping");
        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        //mplayerDo("set_property loop 0");

        mplayerDo("loadfile /usr/bin/sarahtar/videos/"+idleFileName);
    }


    private static boolean isUnix() {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.contains("nix") || OS.contains("nux"));
    }

    private void mplayerDo(String command) {
        logger.info("Using command: " + command);

        Path fileName = Path.of("/usr/bin/sarahtar/player_in");

        try {
            Files.writeString(fileName, command+"\n");
        } catch (IOException e) {
            logger.error("Could not write into fifo file");
            throw new RuntimeException(e);
        }

    }


}