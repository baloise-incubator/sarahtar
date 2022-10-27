package ch.baloise.sarahtar.presenter.service;

import ch.baloise.sarahtar.presenter.controller.VideoInfoRestController;
import ch.baloise.sarahtar.presenter.model.Avatar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


class VideoInfoTest {


    @Test
    void contollerShouldBeAbleToDealWithVideoNamesRead() throws Exception {
        List<Avatar> avatars = new ArrayList<>();
        VideoInfo service = new VideoInfo();

        String name1 = "ahmed_welcome_FR.mp4";
        service.fillAvatarsFromFilename(avatars, name1);
        assertFalse(avatars.isEmpty());

        String name2 = "ahmed_idle.mp4";
        service.fillAvatarsFromFilename(avatars, name2);
        assertEquals(1, avatars.size());
        assertEquals(2, avatars.get(0).getSpeeches().size());

        String name3 = "sarah_welcome_DE.mp4";
        service.fillAvatarsFromFilename(avatars, name3);
        assertEquals(2, avatars.size());
        assertEquals(1, avatars.get(1).getSpeeches().size());

        String name4 = "sarah_idle.mp4";
        service.fillAvatarsFromFilename(avatars, name4);
        assertEquals(2, avatars.size());
        assertEquals(2, avatars.get(1).getSpeeches().size());

        String name5 = "sarah_baloise_DE.mp4";
        service.fillAvatarsFromFilename(avatars, name5);
        assertEquals(2, avatars.size());
        assertEquals(3, avatars.get(1).getSpeeches().size());

        String wrongName = "sarahbaloiseDE.mp4";
        service.fillAvatarsFromFilename(avatars, wrongName);
        assertEquals(2, avatars.size());
    }
}