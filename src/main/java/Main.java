import model.Category;
import repository.User;
import utils.Hibernate;

public class Main {
    public static void main(String[] args) {
        Hibernate.getSessionFactory();
        Category category = new Category();
    }
}
