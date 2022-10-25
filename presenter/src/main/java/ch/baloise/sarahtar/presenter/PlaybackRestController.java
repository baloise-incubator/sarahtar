@RestController
@RequestMapping("play/")
public class PlaybackRestController {

    @GetMapping("/{id}/{videoid}/{language}", produces = "application/text")
    public String playVideo(@PathVariable int id) {
        return playVideoById(id, videoid, language);
    }

    private String playVideoById(int id, String videoid, String language) {
        String rpiCommand = "mplayer -fs -vfm ffmpeg -idle -fixed-vo mplayer -fs"
        String filename = "Avatar"+id+"_Video"+videoid+"_"+language;
        String homedir = "/usr/bin/sarahtar/videos/";
        String rpiCommandPlayback = rpiCommand + " " + homedir + filename;
        Process process = Runtime.getRuntime().exec(rpiCommandPlayback);
        return "Video: " + id +", avatar: " + avatarName + " has been startet once";
    }

}