package pl.coderslab;

import pl.coderslab.model.*;
import pl.coderslab.util.DbUtil;

import java.sql.Date;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
//uwaga problemy ze skanerem. Jak go zamykam to mi się wywala program (np w main scan, w static scanner. po zamknięciu scanner, scan w main też nie działa.
/*
Program po uruchomieniu wyświetli na konsoli listę wszystkich użytkowników.

        Następnie wyświetli

        "Wybierz jedną z opcji:

        add – dodanie użytkownika,
        edit – edycja użytkownika,
        delete – usunięcie użytkownika,
        quit – zakończenie programu."
*/

public class UserAdmin {

    public static void main(String[] args) {
        boolean programEnd = false;
        Scanner scan = new Scanner(System.in);
        while (!programEnd) {
            // wyświetla wszystkich użytkowników
            System.out.println("Lista wszystkich użytkowników :\n");
            try (Connection connection = DbUtil.getConnection()) {
                User[] allUsers = User.loadAllUsers(connection);
                for (User i : allUsers) {
                    System.out.println(i);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            boolean correctOption = false;
            String option = "";

// Wczytuję opcję do wykonania
            while (!correctOption) {
                System.out.println("\n Wybierz jedną z opcji:\n");
                System.out.println("add – dodanie użytkownika");
                System.out.println("edit – edycja użytkownika");
                System.out.println("delete – usunięcie użytkownika");
                System.out.println("quit – zakończenie programu");
                option = scan.nextLine();
                switch (option) {
                    case "add":
                        correctOption = true;
                        break;
                    case "edit":
                        correctOption = true;
                        break;
                    case "delete":
                        correctOption = true;
                        break;
                    case "quit":
                        correctOption = true;
                        break;
                    default: {
                        System.out.println("\nWybierz prawidłową opcję pls.\n");
                        System.out.println("Lista wszystkich użytkowników :\n");
                        try (Connection connection = DbUtil.getConnection()) {
                            User[] allUsers = User.loadAllUsers(connection);
                            for (User i : allUsers) {
                                System.out.println(i);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } // switch
            }

            switch (option) {
                case "add": {
                    User user = UserReadConsole();
                    try (Connection connection = DbUtil.getConnection()) {
                        user.saveToDB(connection);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "edit": {
                    System.out.println("Wybrełeś edit. Podaj nr Użytkownika do poprawy :");
                    int rightId = giveMeRightId();
                    if (rightId != -1) {
                        try (Connection connection = DbUtil.getConnection()) {
                            User user = User.loadUserById(connection, rightId);
                            int id = user.getId();
                            System.out.println("\nWybrałeś :" + user.showPrintUser());
                            System.out.println("\n Podaj nowe dane :");
                            user = UserReadConsole();  // uwaga na błędy wczytywania stringa ??
                            user.saveToDB(connection);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "delete": {
                    System.out.println("Wybrełeś delete. Podaj nr Użytkownika do skasowania :");
                    int rightId = giveMeRightId();
                    if (rightId != -1) {
                        try (Connection connection = DbUtil.getConnection()) {
                            User user = User.loadUserById(connection, rightId);
                            System.out.println("\nWybrałeś :" + user.showPrintUser());
                            System.out.println("\n Czy na pewno chcesz skasować ? (y/n):");
                            Scanner sc = new Scanner(System.in);
                            if (sc.nextLine().equals("y")) {
                                user.delete(connection);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                case "quit": {
                    programEnd = true;
                    break;
                }
            } // switch

        } // main while//
        scan.close();
    } // psvm

    private static User UserReadConsole() {
        Scanner scanner = new Scanner(System.in);
        User usr = new User();
        System.out.println("Podaj Nazwę użytkownika");
        usr.setName(scanner.nextLine());
        System.out.println("Podaj email użytkownika");
        usr.setEmail(scanner.nextLine());
        System.out.println("Podaj hasło");
        usr.setPassword(scanner.nextLine());


        boolean rightGroup = false;
        while (!rightGroup) {
            try (Connection connection = DbUtil.getConnection()) {   // pokazujemy grupy :
                System.out.println("/nWybierz grupę podając jej numer : ");
                UserGroup[] allUsG = UserGroup.loadAllUserGroups(connection);
                for (UserGroup i : allUsG) {
                    System.out.println(i);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int id=0;
            while (!scanner.hasNextInt()) {
                id = scanner.nextInt();
            }
            id=scanner.nextInt();
            scanner.nextLine(); // clear buffer
            try (Connection connection = DbUtil.getConnection()) {   // pokazujemy grupy :
                UserGroup usG = UserGroup.loadUserGroupById(connection, id);
                System.out.println("Wybrałeś grupę :");
                System.out.println(usG.toString());
                rightGroup = true;
                usr.setGroup(usG);
            } catch (SQLException e) {
                System.out.println("Nie ma takiej grupy. Spróbuj ponownie");
            }
        }
        return usr;
    }

    private static int giveMeRightId() {
        int id = -1;
        Scanner scanner = new Scanner(System.in);
        boolean rightId = false;
        while (!rightId) { // get right id
            while (!scanner.hasNextInt()) {  //get int number
                System.out.println("Podaj id użytkownika :");
                id = scanner.nextInt();
                scanner.nextLine(); // clear buffer
            } // we have int number
            id=scanner.nextInt();
            scanner.nextLine();
            try (Connection connection = DbUtil.getConnection()) {
                User user = User.loadUserById(connection, id);
                rightId = true; // wczytano bez błędu czyli jest taki użytkownik
            } catch (SQLException e) {
                System.out.println("Nie ma użytkownika o takim id");
                id = -1;
            }
        } // while 1
        return id;
    }

}// last one Class
