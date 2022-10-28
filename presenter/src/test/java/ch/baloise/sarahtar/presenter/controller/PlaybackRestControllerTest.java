package ch.baloise.sarahtar.presenter.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PlaybackRestControllerTest {

    private PlaybackRestController controller;

    @BeforeEach
    void setUp() {
        controller = new PlaybackRestController();
    }

    @Test
    void getRandomVideoFiles() {
        List<File> allVideoFiles = Arrays.asList(
                new File("my-test-video-file"),
                new File("my-test-idle-file"),
                new File("my-test-other-file"),
                new File("my-test-codecamp-file"),
                new File("my-test-sarah-file")
        );
        List<String> randomVideoFileNames = controller.getRandomVideoFiles(allVideoFiles).stream()
                .map(File::getName)
                .collect(Collectors.toList());

        assertEquals(3, randomVideoFileNames.size());
        assertFalse(randomVideoFileNames.contains("my-test-idle-file"), "idle");
        assertFalse(randomVideoFileNames.contains("my-test-codecamp-file"), "codecamp");
    }

}