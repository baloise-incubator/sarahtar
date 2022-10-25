package ch.baloise.sarahtar.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class VideoPlayer {

    //private static final String RPI_COMMAND = "mplayer -fs -vfm ffmpeg -idle -fixed-vo";
    private static final String HOMEDIR = "/usr/bin/sarahtar/videos/";

    Logger logger = LoggerFactory.getLogger(VideoPlayer.class);

    public String playVideo(String fileName, String idleName) throws IOException {
        logger.info("Received play command for video: " + fileName);

        if (!isUnix()) {
            return "Unsupported operating system: Video not started";
        }

        String[] rpiCommandPlayback = {
                "/usr/bin/mplayer",
                "-fs",
                HOMEDIR + fileName,
                "-fs",
                "-fixed-vo",
                "-idle",
                HOMEDIR + idleName,
                "-loop 0",
                ""
        };

        ProcessBuilder pb = new ProcessBuilder(rpiCommandPlayback);
        pb.environment().put("DISPLAY", ":0");

        pb.start();
        return "Started Video: " + fileName;
    }

    private static boolean isUnix() {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.contains("nix") || OS.contains("nux"));
    }

}