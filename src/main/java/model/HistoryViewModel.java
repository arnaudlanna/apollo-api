package model;

public class HistoryViewModel {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getEpisode_id() {
        return episode_id;
    }

    public void setEpisode_id(Integer episode_id) {
        this.episode_id = episode_id;
    }

    private Integer user_id;
    private Integer episode_id;

    public HistoryViewModel(History history) {
        this.id = history.getId();
        this.user_id = history.getUser().getId();
        this.episode_id = history.getEpisode().getId();
    }
}
