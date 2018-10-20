package pl.coderslab;

import pl.coderslab.model.*;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
/*
//test : dodaj użytkownika
        User user = new User("Michal Kop", "m.kop@gazeta.pl", "blabla", userGroup);
        try (Connection connection = DbUtil.getConnection()) {
            user.saveToDB(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

// test załąduj po id :
        try (Connection connection = DbUtil.getConnection()) {
            User userLoad = User.loadUserById(connection,3);
            if (userLoad != null) {
                System.out.println(userLoad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/

/*
// test   load all oraz modyfikuj.
        try (Connection connection = DbUtil.getConnection()) {
          User[] allUsers = User.loadAllUsers(connection);
          for (User i: allUsers) {
              System.out.println("user["+i.getId()+"]: "+i.getName()+" | "+i.getEmail()+" | "+i.getPassword()+" ;");
          }
          allUsers[2].delete (connection);
    } catch (SQLException e) {
            e.printStackTrace();
        }
//test czy del. ok.
        try (Connection connection = DbUtil.getConnection()) {
            User[] allUsers = User.loadAllUsers(connection);
            for (User i: allUsers) {
                System.out.println("user["+i.getId()+"]: "+i.getName()+" | "+i.getEmail()+" | "+i.getPassword()+" ;");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/

    }
}
