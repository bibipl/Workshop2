package pl.coderslab;

import pl.coderslab.model.*;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        /*UserGroup usG = new UserGroup("Grupa nr4, ktora bedzie sie uczyc pisac i czytac.");
        try (Connection connection = DbUtil.getConnection()) {
            usG.saveToDB(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
        // test   load all oraz modyfikuj.
        try (Connection connection = DbUtil.getConnection()) {
            UserGroup[] allUsG = UserGroup.loadAllUserGroups(connection);
            for (UserGroup i: allUsG) {
                System.out.println(i);
            }
            System.out.println("##############");
            allUsG[1].setName("Jednak bedzie tylko spiewac");
            allUsG[1].saveToDB(connection);
            UserGroup[] allUsG2 = UserGroup.loadAllUserGroups(connection);
            for (UserGroup i: allUsG2) {
                System.out.println(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
