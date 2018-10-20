package pl.coderslab;

import pl.coderslab.model.*;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserGroup userGroup = new UserGroup(1);
        User user = new User("Artur", "artur.hacia@coderslab.pl", "qwerty", userGroup);


        try (Connection connection = DbUtil.getConnection()) {
            user.saveToDB(connection);
            User user1 = User.loadUserById(connection,2);
            if (user1!=null) {
                System.out.println(user1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
