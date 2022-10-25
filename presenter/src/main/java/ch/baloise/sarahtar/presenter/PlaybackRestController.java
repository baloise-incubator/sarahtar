package ch.baloise.sarahtar.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;

@RestController
@RequestMapping("play/")
public class PlaybackRestController {

    Logger logger = LoggerFactory.getLogger(PlaybackRestController.class);

    VideoPlayer videoPlayer;

    @GetMapping("/{id}/{videoid}/{language}")
    public String playVideo(@PathVariable int id, @PathVariable int videoid, @PathVariable String language) throws IOException {
        return playVideoById(id, videoid, language);
    }

    @PostConstruct
    public void init() throws IOException {
        Runtime.getRuntime().exec("sh /usr/bin/sarahtar/initdisplay.sh");
    }

    private String playVideoById(int id, int videoid, String language) throws IOException {
        logger.info("Received play command: video: " + id +", avatar: " + videoid + " to start once");
        return videoPlayer.playVideo("Avatar"+id+"_Video"+videoid+"_"+language+".mp4");
    }
}