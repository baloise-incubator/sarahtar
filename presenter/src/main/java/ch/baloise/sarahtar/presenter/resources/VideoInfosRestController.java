package ch.baloise.sarahtar.presenter.resources;

import ch.baloise.sarahtar.presenter.model.Avatar;
import ch.baloise.sarahtar.presenter.model.Speech;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("info/")
public class VideoInfosRestController {

    Logger logger = LoggerFactory.getLogger(VideoInfosRestController.class);

    @GetMapping("/speeches")
    List<Avatar> getAllSpeaches() {
        return listAllVideos();
    }

    @ResponseBody
    private List<Avatar> listAllVideos() {
        File folder = new File("/usr/bin/sarahtar/videos/");
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        return getAvatarsFromFilelist(listOfFiles);
    }

    private List<Avatar> getAvatarsFromFilelist(File[] listOfFiles) {
        List<Avatar> avatars = new ArrayList<>();
        logger.info(String.valueOf(listOfFiles));
        for (File file : listOfFiles) {
            if (file.isFile()) {
                fillAvatarsFromFilename(avatars, file.getName());
            }
        }
        return avatars;
    }

    protected void fillAvatarsFromFilename(List<Avatar> avatars, String fileName) {
        logger.info("Search found filename: "+ fileName);
        if (isFilenameSarahtorVideoName(fileName)) {
            String[] infos = fileName.split("_");
            String avatar = infos[0];
            String speech = infos[1];
            String language = "idle";
            if (!speech.equals("idle.mp4")) {
                language = infos[2].split("\\.")[0];
            } else {
                speech = speech.split("\\.")[0];
            }
            logger.info("Search found: avatarName: "+avatar+", speeachName: "+speech+" , language: "+language);
            fillAvatarsListWithReadFileInfos(avatars, avatar, speech, language);
        } else {
            logger.info("Search found fileName that is not using Sarahtor Naming convention");
        }
    }

    private boolean isFilenameSarahtorVideoName(String name) {
        String[] infos = name.split("_");
        if (name.endsWith(".mp4")) {
            if (infos.length == 2 || infos.length == 3) {
                return true;
            } else {
                logger.info("Search: filename: "+name+", can not be split to 2 or 3 readable values:  splitted: "+ Arrays.toString(infos));
            }
        } else {
            logger.info("Search: filename is not a .mp4 file - ignoring");
        }
        return false;
    }

    private void fillAvatarsListWithReadFileInfos(List<Avatar> avatars, String avatar, String speach, String language) {
        Optional<Avatar> alreadyListedAvatar = avatars.stream().filter(f -> f.getName().equals(avatar)).findAny();
        Speech speach1New = new Speech(speach, language);
        if (alreadyListedAvatar.isPresent()) {
            alreadyListedAvatar.get().getSpeeches().add(speach1New);
        } else {
            Avatar avatarNew = new Avatar(avatar);
            avatarNew.getSpeeches().add(speach1New);
            avatars.add(avatarNew);
        }
    }
}