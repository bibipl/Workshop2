package pl.coderslab;

import pl.coderslab.model.*;
import pl.coderslab.util.DbUtil;

import java.sql.Date;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

 /*       UserGroup usG = new UserGroup("Grupa 4, Tester dla mam");
        try (Connection connection = DbUtil.getConnection()) {
            usG.saveToDB(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // test   load all oraz modyfikuj.
        try (Connection connection = DbUtil.getConnection()) {
            UserGroup[] allUsG = UserGroup.loadAllUserGroups(connection);
            for (UserGroup i: allUsG) {
                System.out.println(i);
            }
            System.out.println("##############");
            *//*allUsG[1].setName("Jednak bedzie tylko spiewac");
            allUsG[1].saveToDB(connection);
            UserGroup[] allUsG2 = UserGroup.loadAllUserGroups(connection);
            for (UserGroup i: allUsG2) {
                System.out.println(i);
            }*//*
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
//test : dodaj użytkownika

/*
        try (Connection connection = DbUtil.getConnection()) {
            User user = new User("Józef Śpąk", "j.spo@onet.pl", "stary", UserGroup.loadUserGroupById(connection,4));
            user.saveToDB(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
/*

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
              System.out.println("user["+i.getId()+"]: "+i.getName()+" | "+i.getEmail()+" | "+i.getGroup().getName()+" | "+i.getPassword()+" ;");
          }
    } catch (SQLException e) {
            e.printStackTrace();
        }*/
/*
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
/*

// dodaj exercise
        try (Connection connection = DbUtil.getConnection()) {
            Exercise exercise=new Exercise ("Projekt końcowy","Samodzielnie wykonany projekt");
            exercise.saveToDB(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
// załaduj wszystkie ćwiczenia.

        try (Connection connection = DbUtil.getConnection()) {
            Exercise[] allExercises = Exercise.loadAllExercises(connection);
            for (Exercise i: allExercises ) {
                System.out.println(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
*//*
        String str="2015-03-31";
        Date date=Date.valueOf(str);//converting string into sql date
        System.out.println(date);
*/
   /*     // dodaj solution
        try (Connection connection = DbUtil.getConnection()) {
            Solution solution=new Solution (Date.valueOf("2018-10-12"),Date.valueOf("2018-10-22"),"Zaliczone 12.5/10",3,2);
            solution.saveToDB(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
// załaduj wszystkie solutions

        try (Connection connection = DbUtil.getConnection()) {
            Solution[] allSolutions = Solution.loadAllsolutions(connection);
            for (Solution i: allSolutions ) {
                System.out.println(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

*/
        try (Connection connection = DbUtil.getConnection()) {
            Solution[] wynik = Solution.loadAllByUserId(connection,3);
            for (Solution i : wynik) {
                System.out.println(i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
