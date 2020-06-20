package model;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = false, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private String password;

    @Column(unique = false)
    private String profilePicture;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Podcast> subscriptions = new ArrayList<>();

    public User(){}

    public User(Integer id) {
        this.id = id;
    }

    public User(String email, String name, String password) throws NoSuchAlgorithmException {
        this.email = email;
        this.name = name;
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hashedPassword = md.digest(this.password.getBytes(StandardCharsets.UTF_8));
        this.password = new String(hashedPassword);
    }

    public void updateUser(User user) {
        this.setName(user.getName());
        this.setPassword(user.getPassword());
        this.setEmail(user.getEmail());
        this.setProfilePicture(user.getProfilePicture());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<Podcast> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Podcast> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
