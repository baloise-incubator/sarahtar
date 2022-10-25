package ch.baloise.sarahtar.presenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class PresenterApplication {

    VideoPlayer videoPlayer;

    @PostConstruct
    public void initApplication() throws IOException {
        videoPlayer.playVideo("Avatar1_Video1_DE.mp4");
    }

    public static void main(String[] args) {
        SpringApplication.run(PresenterApplication.class, args);
    }

}
