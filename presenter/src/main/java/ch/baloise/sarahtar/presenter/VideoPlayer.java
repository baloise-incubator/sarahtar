package ch.baloise.sarahtar.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class VideoPlayer {

    private static final String SPEECH_PLACEHOLDER = "<<speech>>";
    private static final String IDLE_PLACEHOLDER = "<<idle>>";
    private static final String RPI_COMMAND = "mplayer -fs "+SPEECH_PLACEHOLDER+" -fs -idle -fixed-vo "+IDLE_PLACEHOLDER+" -loop 0";
    private static final String HOMEDIR = "/usr/bin/sarahtar/videos/";

    Logger logger = LoggerFactory.getLogger(VideoPlayer.class);

    public String playVideo(String speachFileName, String idleFileName) throws IOException {
        logger.info("Received play command for video: " + speachFileName);

        if (!isUnix()) {
            return "Unsupported operating system: Video not started";
        }

        String cmdString = RPI_COMMAND
                .replace(SPEECH_PLACEHOLDER,HOMEDIR + speachFileName)
                .replace(IDLE_PLACEHOLDER,HOMEDIR + idleFileName);

        logger.info("Using command: " + cmdString);

        ProcessBuilder pb = new ProcessBuilder(cmdString.split( " "));
        // local display
        pb.environment().put("DISPLAY", ":0");

        pb.start();
        return "Started Video: " + speachFileName;
    }

    private static boolean isUnix() {
        String OS = System.getProperty("os.name").toLowerCase();
        return (OS.contains("nix") || OS.contains("nux"));
    }

}