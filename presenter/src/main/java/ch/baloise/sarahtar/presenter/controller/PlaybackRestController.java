package ch.baloise.sarahtar.presenter.controller;

import ch.baloise.sarahtar.presenter.model.Avatar;
import ch.baloise.sarahtar.presenter.model.Speech;
import ch.baloise.sarahtar.presenter.service.VideoInfo;
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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@EnableAsync
@RequestMapping("play/")
public class PlaybackRestController {

    Logger logger = LoggerFactory.getLogger(PlaybackRestController.class);

    @Autowired
    VideoPlayer videoPlayer;

    @Autowired
    VideoInfo videoInfo;

    private final Random rand = new Random(System.currentTimeMillis());

    @PostConstruct
    public void initApplication() throws IOException {
        playVideoById("sarah","welcome","DE");
    }

    @GetMapping("/random")
    String playRandomVideo() throws IOException {
        List<File> files = videoInfo.listAllVideoFiles();
        files.removeIf(file -> file.getName().contains("idle"));
        File randomFile = files.get(rand.nextInt(files.size()));

        List<Avatar> avatars = new ArrayList<>();
        videoInfo.fillAvatarsFromFilename(avatars, randomFile.getName());
        Avatar avatar = avatars.get(0);
        Speech speech = avatar.getSpeeches().get(0);
        return playVideoById(avatar.getName(), speech.getName(), speech.getLanguageTag());
    }

    @GetMapping("/{id}/{videoid}/{language}")
    String playVideo(@PathVariable String id, @PathVariable String videoid, @PathVariable String language) throws IOException {
        return playVideoById(id, videoid, language);
    }

    private String playVideoById(String avatarname, String videotag, String language) throws IOException {
        logger.info("Received play command: video: " + avatarname +", videotag: " + videotag + " to start once");
        videoPlayer.playVideo(avatarname+"_"+videotag+"_"+language+".mp4", avatarname+"_idle.mp4");
        return "playing " + videotag;
    }
}