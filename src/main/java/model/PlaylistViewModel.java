package model;

public class PlaylistViewModel {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Boolean getPrivates() {
        return privates;
    }

    public void setPrivates(Boolean privates) {
        this.privates = privates;
    }

    private Integer user_id;
    private String description;
    private String image;
    private Integer followers;
    private Boolean privates;


    public PlaylistViewModel(Playlist playlist) {
        this.id = playlist.getId();
        this.name = playlist.getName();
        this.user_id = playlist.getUser().getId();
        this.description = playlist.getDescription();
        this.image = playlist.getImage();
        this.followers = playlist.getFollowers();
        this.privates = playlist.getPrivates();
    }


}
