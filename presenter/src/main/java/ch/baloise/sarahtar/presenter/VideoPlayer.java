package ch.baloise.sarahtar.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class VideoPlayer {

    private static final String RPI_COMMAND = "mplayer -fs -vfm ffmpeg -idle -fixed-vo";
    private static final String HOMEDIR = "/usr/bin/sarahtar/videos/";

    Logger logger = LoggerFactory.getLogger(VideoPlayer.class);

    public String playVideo(String fileName) throws IOException {
        logger.info("Received play command for video: " + fileName);
        String rpiCommandPlayback = RPI_COMMAND + " " + HOMEDIR + fileName;
        Runtime.getRuntime().exec(rpiCommandPlayback);
        return "Started Video: " + fileName;
    }
}