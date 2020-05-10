package model;

public class EpisodeViewModel {
    private Integer id;
    private String title;
    private String description;
    private Integer duration;
    private Integer views;
    private Integer likes;
    private String banner;

    public EpisodeViewModel(Episode episode) {
        this.id = episode.getId();
        this.title = episode.getTitle();
        this.description = episode.getDescription();
        this.duration = episode.getDuration();
        this.views = episode.getViews();
        this.likes = episode.getLikes();
        this.banner = episode.getBanner();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
