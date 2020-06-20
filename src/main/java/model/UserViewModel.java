package model;

public class UserViewModel {
    private Integer id;
    private String email;
    private String name;
    private String profilePicture;

    public UserViewModel(User updatedUser) {
        this.name = updatedUser.getName();
        this.email = updatedUser.getEmail();
        this.id = updatedUser.getId();
        this.profilePicture = updatedUser.getProfilePicture();
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
