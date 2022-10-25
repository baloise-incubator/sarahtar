package ch.baloise.sarahtar.presenter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;

@RestController
@RequestMapping("play/")
public class PlaybackRestController {

    @GetMapping("/{id}/{videoid}/{language}")
    public String playVideo(@PathVariable int id, @PathVariable int videoid, @PathVariable String language) throws IOException {
        return playVideoById(id, videoid, language);
    }

    @PostConstruct
    public void init() throws IOException {
        Runtime.getRuntime().exec("sh export DISPLAY=:0");
    }

    private String playVideoById(int id, int videoid, String language) throws IOException {
        String rpiCommand = "mplayer -fs -vfm ffmpeg -idle -fixed-vo";
        String filename = "Avatar"+id+"_Video"+videoid+"_"+language;
        String homedir = "/usr/bin/sarahtar/videos/";
        String rpiCommandPlayback = rpiCommand + " " + homedir + filename;
        Process process = Runtime.getRuntime().exec(rpiCommandPlayback);
        return "Video: " + id +", avatar: " + videoid + " has been startet once";
    }
}