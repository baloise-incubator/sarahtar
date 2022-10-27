package ch.baloise.sarahtar.presenter.controller;

import ch.baloise.sarahtar.presenter.service.VideoPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;

@RestController
@EnableAsync
@RequestMapping("play/")
public class PlaybackRestController {

    Logger logger = LoggerFactory.getLogger(PlaybackRestController.class);

    @Autowired
    VideoPlayer videoPlayer;

    @PostConstruct
    public void initApplication() throws IOException {
        playVideoById("sarah","welcome","DE");
    }

    @GetMapping("/{id}/{videoid}/{language}")
    String playVideo(@PathVariable String id, @PathVariable String videoid, @PathVariable String language) throws IOException {
        playVideoById(id, videoid, language);
        return "videoid";
    }

    private String playVideoById(String avatarname, String videotag, String language) throws IOException {
        logger.info("Received play command: video: " + avatarname +", videotag: " + videotag + " to start once");
        videoPlayer.playVideo(avatarname+"_"+videotag+"_"+language+".mp4", avatarname+"_idle.mp4");
        return videotag;
    }
}