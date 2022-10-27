package ch.baloise.sarahtar.presenter.controller;

import ch.baloise.sarahtar.presenter.model.Avatar;
import ch.baloise.sarahtar.presenter.service.VideoInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("info/")
public class VideoInfoRestController {

    Logger logger = LoggerFactory.getLogger(VideoInfoRestController.class);

    @Autowired
    VideoInfo videoInfo;

    @GetMapping("/speeches")
    List<Avatar> getAllSpeaches() {
        return listAllVideos();
    }

    @ResponseBody
    private List<Avatar> listAllVideos() {
        File folder = new File("/usr/bin/sarahtar/videos/");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        return videoInfo.getAvatarsFromFilelist(listOfFiles);
    }

}