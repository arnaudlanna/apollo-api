package model;

import javax.persistence.*;

public class PlaylistItemViewModel {

    private int id;
    private Integer playlist_id;
    private Integer episode_id;

    public PlaylistItemViewModel(PlaylistItem createdItem) {
        this.playlist_id = createdItem.getPlaylist().getId();
        this.episode_id = createdItem.getEpisode().getId();
        this.id = createdItem.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPlaylistID() {
        return playlist_id;
    }

    public void setPlaylistID(Integer playlist_id) {
        this.playlist_id = playlist_id;
    }

    public Integer getEpisodeID() {
        return episode_id;
    }

    public void setEpisodeID(Integer episode_id) {
        this.episode_id = episode_id;
    }
}
