package pl.coderslab;
/*
Program po uruchomieniu wyświetli na konsoli listę wszystkich grup.

        Następnie wyświetli w konsoli napis

        "Wybierz jedną z opcji:

        add – dodanie grupy,
        edit – edycja grupy,
        delete – edycja grupy,
        quit – zakończenie programu."
*/

import pl.coderslab.model.UserGroup;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class AdminGroups {
    public static void main(String[] args) {
        boolean programEnd = false;
        Scanner scan = new Scanner(System.in);
        while (!programEnd) {
            // wyświetla wszystkie ćwiczenia
            System.out.println("\n\nLista wszystkich GRUP :\n");
            try (Connection connection = DbUtil.getConnection()) {
                UserGroup[] allUserGroups = UserGroup.loadAllUserGroups(connection);
                for (UserGroup i : allUserGroups) {
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
                System.out.println("add – dodanie GRUPY");
                System.out.println("edit – edycja GRUPY");
                System.out.println("delete – usunięcie GRUPY");
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
                        System.out.println("Lista wszystkich GRUP :\n");
                        try (Connection connection = DbUtil.getConnection()) {
                            UserGroup[] allUserGroups = UserGroup.loadAllUserGroups(connection);
                            for (UserGroup i : allUserGroups) {
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
                    UserGroup group = new UserGroup();
                    try (Connection connection = DbUtil.getConnection()) {
                        group.saveToDB(connection);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println("\nNowe dane to :");
                    System.out.println(group);
                    break;
                }
                case "edit": {
                    System.out.println("Wybrełeś edit. Podaj nr GRUPY do edycji :");
                    int rightId = giveMeRightId();
                    if (rightId != -1) {
                        try (Connection connection = DbUtil.getConnection()) {
                            UserGroup group = UserGroup.loadUserGroupById(connection, rightId);
                            System.out.println("\nWybrałeś :" + group.showPrintUserGroup());
                            System.out.println("\nPodaj nowe dane :");
                            group = groupReadConsole(group);  // uwaga na błędy wczytywania stringa ??
                            group.saveToDB(connection);
                            System.out.println("\nNowe dane to :");
                            System.out.println(group);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                }
                case "delete": {
                    System.out.println("Wybrełeś delete. Podaj nr GRUPY do skasowania :");
                    int rightId = giveMeRightId();
                    if (rightId != -1) {
                        try (Connection connection = DbUtil.getConnection()) {
                            UserGroup group = UserGroup.loadUserGroupById(connection, rightId);
                            System.out.println("\nWybrałeś :" + group.showPrintUserGroup());
                            System.out.println("\n Czy na pewno chcesz skasować ? (y/n):");
                            Scanner sc = new Scanner(System.in);
                            if (sc.nextLine().equals("y")) {
                                group.delete(connection);
                            }

                        } catch (SQLIntegrityConstraintViolationException e) {
                            System.out.println("\nNie mogę skasować tej grupy. Są tam przypisani użytkownicy");
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

    private static UserGroup groupReadConsole(UserGroup group) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nPodaj nazwę GRUPY");
        group.setName(scanner.nextLine());
        return group;
    }

    private static int giveMeRightId() {
        int id = -1;
        Scanner scanner = new Scanner(System.in);
        boolean rightId = false;
        while (!rightId) { // get right id
            while (!scanner.hasNextInt()) {  //get int number
                System.out.println("\nPodaj Numer GRUPY :");
                id = scanner.nextInt();
                scanner.nextLine(); // clear buffer
            } // we have int number
            id=scanner.nextInt();
            scanner.nextLine();
            try (Connection connection = DbUtil.getConnection()) {
                UserGroup group = UserGroup.loadUserGroupById(connection, id);
                rightId = true; // wczytano bez błędu czyli jest takie ćwiczenie
            } catch (SQLException e) {
                System.out.println("\nNie ma GRUPY o takim NUMERZE");
                id = -1;
            }
        } // while 1
        return id;
    }

}// last one Class



