package model;

public class    PodcastViewModel {
    private Integer id;
    private String name;

    public PodcastViewModel(Podcast podcast) {
        this.id = podcast.getId();
        this.name = podcast.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
