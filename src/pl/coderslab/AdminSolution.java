package pl.coderslab;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;
import pl.coderslab.util.DbUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.Date;

public class AdminSolution {
    public static void main(String[] args) {
        boolean programEnd = false;
        Scanner scan = new Scanner(System.in);
        while (!programEnd) {
            // wyświetla wszystkie rozwiązania
            System.out.println("\n\nLista wszystkich ROZWIĄZAŃ :\n");
            try (Connection connection = DbUtil.getConnection()) {
                Solution[] allSolutions = Solution.loadAllSolutions(connection);
                for (Solution i : allSolutions) {
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
                System.out.println("add – dodanie rozwiązania");
                System.out.println("edit – edycja rozwiązania");
                System.out.println("delete – usunięcie rozwiązania");
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
                        System.out.println("\nWybierz prawidłową opcję!\n");
                        System.out.println("Lista wszystkich ROZWIĄZAŃ :\n");
                        try (Connection connection = DbUtil.getConnection()) {
                            Solution[] allSolutions = Solution.loadAllSolutions(connection);
                            for (Solution i : allSolutions) {
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
                    Solution solution = new Solution();
                    solution = SolutionReadConsole(solution);
                    try (Connection connection = DbUtil.getConnection()) {
                        solution.saveToDB(connection);
                        System.out.println("\nNowe dodane ROZWIĄZANIE :");
                        System.out.println(solution);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "edit": {
                    System.out.println("Wybrełeś edit. Podaj nr ROZWIĄZANIA do poprawy :");
                    int rightId = giveMeRightId();
                    if (rightId != -1) {
                        try (Connection connection = DbUtil.getConnection()) {
                            Solution solution = Solution.loadSolutionById(connection, rightId);
                            System.out.println("\nWybrałeś :" + solution.showPrintSolution());
                            System.out.println("\nPodaj nowe dane :");
                            solution = SolutionReadConsole(solution);  // uwaga na błędy wczytywania stringa ??
                            solution.saveToDB(connection);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case "delete": {
                    System.out.println("\nWybrałeś delete. Podaj nr ROZWIĄZANIA do skasowania :");
                    int rightId = giveMeRightId();
                    if (rightId != -1) {
                        try (Connection connection = DbUtil.getConnection()) {
                            Solution solution = Solution.loadSolutionById(connection, rightId);
                            System.out.println("\nWybrałeś :" + solution.showPrintSolution());
                            System.out.println("\nCzy na pewno chcesz skasować ? (y/n):");
                            Scanner sc = new Scanner(System.in);
                            if (sc.nextLine().equals("y")) {
                                solution.delete(connection);
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

    private static Solution SolutionReadConsole(Solution solution) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj Datę UTWORZENIA ROZWIĄZANIA RRRR-MM-DD");
        String data =scanner.nextLine();
        solution.setCreated (Date.valueOf(data));
        System.out.println("Podaj Datę KOREKTY ROZWIĄZANIA RRR-MM-DD (Enter=brak)");
        scanner = new Scanner(System.in);
        data =scanner.nextLine();
        if (data.equals("")) solution.setUpdated(null);
        else solution.setUpdated(Date.valueOf(data));
        System.out.println("Podaj OPIS ROZWIĄZANIA");
        solution.setDescription(scanner.nextLine());
        // ############ EXERCISE

        System.out.println("\n\nLista wszystkich ĆWICZEŃ :\n");
        boolean rightExercise = false;
        while (!rightExercise) {
            try (Connection connection = DbUtil.getConnection()) {   // pokazujemy ćwiczenia :
                System.out.println("\nWybierz ĆWICZENIE podając NUMER : ");
                Exercise[] allExercises = Exercise.loadAllExercises(connection);
                for (Exercise i : allExercises) {
                    System.out.println(i);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int id=0;
            System.out.println("\nPodaj numer ĆWICZENIA, którego dotyczy to ROZWIĄZANIE :");
            while (!scanner.hasNextInt()) {
                id = scanner.nextInt();
            }
            id=scanner.nextInt();
            scanner.nextLine(); // clear buffer
            try (Connection connection = DbUtil.getConnection()) {   // pokazujemy ćwiczenie :
                Exercise exercise = Exercise.loadExerciseById(connection, id);
                System.out.println("\nWybrałeś ROZWIĄZANIE :");
                System.out.println(exercise.toString());
                rightExercise = true;
                solution.setExcercise_id(exercise);
            } catch (SQLException e) {
                System.out.println("\nNie ma takiej GRUPY. Spróbuj ponownie");
            }
        }
       // ##################### UŻYTKOWNIK
        boolean rightUser = false;
        while (!rightUser) {
            try (Connection connection = DbUtil.getConnection()) {   // pokazujemy userów :
                System.out.println("\nWybierz UŻYTKOWNIKA podając NUMER : ");
                User[] allUsers = User.loadAllUsers(connection);
                for (User i : allUsers) {
                    System.out.println(i);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int id=0;
            System.out.println("\nPodaj UŻYTKOWNIKA, którego przygotował to ROZWIĄZANIE :");
            while (!scanner.hasNextInt()) {
                id = scanner.nextInt();
            }
            id=scanner.nextInt();
            scanner.nextLine(); // clear buffer
            try (Connection connection = DbUtil.getConnection()) {   // pokazujemy ćwiczenie :
                User user = User.loadUserById(connection, id);
                System.out.println("\nWybrałeś UŻYTKOWNIKA :");
                System.out.println(user.toString());
                rightUser = true;
                solution.setUser(user);
            } catch (SQLException e) {
                System.out.println("Nie ma takiego UŻYTKOWNIKA. Spróbuj ponownie");
            }
        }
        return solution;
    }

    private static int giveMeRightId() {
        int id = -1;
        Scanner scanner = new Scanner(System.in);
        boolean rightId = false;
        while (!rightId) { // get right id
            while (!scanner.hasNextInt()) {  //get int number
                System.out.println("Podaj id ROZWIĄZANIA :");
                id = scanner.nextInt();
                scanner.nextLine(); // clear buffer
            } // we have int number
            id=scanner.nextInt();
            scanner.nextLine();
            try (Connection connection = DbUtil.getConnection()) {
                Solution user = Solution.loadSolutionById(connection, id);
                rightId = true; // wczytano bez błędu czyli jest taki użytkownik
            } catch (SQLException e) {
                System.out.println("Nie ma ROZWIĄZANIA o takim id");
                id = -1;
            }
        } // while NR1
        return id;
    }
}
