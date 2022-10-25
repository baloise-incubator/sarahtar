package ch.baloise.sarahtar.presenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    VideoPlayer videoPlayer;

    @PostConstruct
    public void initApplication() throws IOException {
        playVideoById(1,1,"DE");
    }

    @GetMapping("/{id}/{videoid}/{language}")
    public String playVideo(@PathVariable int id, @PathVariable int videoid, @PathVariable String language) throws IOException {
        return playVideoById(id, videoid, language);
    }

    private String playVideoById(int id, int videoid, String language) throws IOException {
        logger.info("Received play command: video: " + id +", avatar: " + videoid + " to start once");
        return videoPlayer.playVideo("Avatar"+id+"_Video"+videoid+"_"+language+".mp4");
    }
}