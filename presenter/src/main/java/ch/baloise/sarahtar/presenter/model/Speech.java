package ch.baloise.sarahtar.presenter.model;

public class Speech {

    private final String name;
    private final String languageTag;

    public String getName() {
        return name;
    }

    //needed for json serializer
    @SuppressWarnings("unused")
    public String getLanguageTag() {
        return languageTag;
    }

    public Speech(String name, String languageTag) {
        this.name = name;
        this.languageTag = languageTag;
    }


}
