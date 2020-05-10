package model;

import java.util.ArrayList;
import java.util.List;

public class    PodcastViewModel {
    private Integer id;
    private String name;
    private List<EpisodeViewModel> episodes;

    public PodcastViewModel(Podcast podcast) {
        this.id = podcast.getId();
        this.name = podcast.getName();
        this.episodes = new ArrayList<>();
        for (model.Episode episode : podcast.getEpisodes()) {
            this.episodes.add(new EpisodeViewModel(episode));
        }
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

    public List<EpisodeViewModel> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<EpisodeViewModel> episodes) {
        this.episodes = episodes;
    }
}
