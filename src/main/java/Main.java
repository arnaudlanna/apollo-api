import repository.User;

public class Main {
    public static void main(String[] args) {
        User user = new repository.User();
        user.createUser("Sábio", "sabio@apollocast.live", "123456");
    }
}
