package ch.baloise.sarahtar.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class PresenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PresenterApplication.class, args);
    }

}
