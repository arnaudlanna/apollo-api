package model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "History")

public class Playlist {

    private int id;
    private String name;
    private String description;
    private String image;
    private Integer followers;
    private Boolean privates;
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
