package ch.baloise.sarahtar.presenter.model;

import java.util.ArrayList;
import java.util.List;

public class Avatar {

    private final String name;
    private final List<Speech> speeches;

    public Avatar(String name) {
        this.name = name;
        this.speeches = new ArrayList<>();
    }

    public List<Speech> getSpeeches() {
        return speeches;
    }

    public String getName() {
        return name;
    }
}
