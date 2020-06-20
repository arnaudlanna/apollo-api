package model;

import javax.persistence.*;

@Entity
@Table(name = "Playlist")

public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Column(unique = false, length = 1024)
    private String name;

    @Column(unique = false, length = 1024)
    private String description;

    @Column(unique = false, length = 1024)
    private String image;

    @Column(unique = false)
    private Integer followers;

    @Column(unique = false)
    private Boolean privates;

    @ManyToOne(optional = false)
    private User user;

    public Playlist() {
    }

    public Playlist(PlaylistViewModel input) {
        this.name = input.getName();
        this.description = input.getDescription();
        this.image = input.getImage();
    }

    public Integer getId() {
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
