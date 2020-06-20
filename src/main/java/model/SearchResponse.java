package model;

public class SearchResponse {
    private Object episodes;
    private Object podcasts;
    private Object playlists;

    public SearchResponse(Object episodes, Object podcasts, Object playlists) {
        this.episodes = episodes;
        this.podcasts = podcasts;
        this.playlists = playlists;
    }

    public Object getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Object episodes) {
        this.episodes = episodes;
    }

    public Object getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(Object podcasts) {
        this.podcasts = podcasts;
    }

    public Object getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Object playlists) {
        this.playlists = playlists;
    }
}
