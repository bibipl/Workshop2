package pl.coderslab;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import pl.coderslab.model.*;
import pl.coderslab.util.DbUtil;


/*
Program po uruchomieniu wyświetli na konsoli listę wszystkich zadań.

Następnie wyświetli w konsoli napis

"Wybierz jedną z opcji:

    add – dodanie zadania,
    edit – edycja zadania,
    delete – edycja zadania,
    quit – zakończenie programu."
*/

    public class AdminExercise {
        public static void main(String[] args) {
            boolean programEnd = false;
            Scanner scan = new Scanner(System.in);
            while (!programEnd) {
                // wyświetla wszystkie ćwiczenia
                System.out.println("\n\nLista wszystkich ĆWICZEŃ :\n");
                try (Connection connection = DbUtil.getConnection()) {
                    Exercise[] allExercises = Exercise.loadAllExercises(connection);
                    for (Exercise i : allExercises) {
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
                    System.out.println("add – dodanie ćwiczenia");
                    System.out.println("edit – edycja ćwiczenia");
                    System.out.println("delete – usunięcie ćwiczenia");
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
                            System.out.println("Lista wszystkich ĆWICZEŃ :\n");
                            try (Connection connection = DbUtil.getConnection()) {
                                Exercise[] allExercises = Exercise.loadAllExercises(connection);
                                for (Exercise i : allExercises) {
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
                        Exercise exercise = new Exercise();
                        exercise = exerciseReadConsole(exercise);
                        try (Connection connection = DbUtil.getConnection()) {
                            exercise.saveToDB(connection);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case "edit": {
                        System.out.println("Wybrełeś edit. Podaj nr ĆWICZENIA do edycji :");
                        int rightId = giveMeRightId();
                        if (rightId != -1) {
                            try (Connection connection = DbUtil.getConnection()) {
                                Exercise exercise = Exercise.loadExerciseById(connection, rightId);
                                System.out.println("\nWybrałeś :" + exercise.showPrintExercise());
                                System.out.println("\nPodaj nowe dane :");
                                exercise = exerciseReadConsole(exercise);  // uwaga na błędy wczytywania stringa ??
                                exercise.saveToDB(connection);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    }
                    case "delete": {
                        System.out.println("Wybrełeś delete. Podaj nr ćwiczenia do skasowania :");
                        int rightId = giveMeRightId();
                        if (rightId != -1) {
                            try (Connection connection = DbUtil.getConnection()) {
                                Exercise exercise = Exercise.loadExerciseById(connection, rightId);
                                System.out.println("\nWybrałeś :" + exercise.showPrintExercise());
                                System.out.println("\n Czy na pewno chcesz skasować ? (y/n):");
                                Scanner sc = new Scanner(System.in);
                                if (sc.nextLine().equals("y")) {
                                    exercise.delete(connection);
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

        private static Exercise exerciseReadConsole(Exercise exercise) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Podaj Tytuł ćwiczenia");
            exercise.setTitle(scanner.nextLine());
            System.out.println("Podaj Opis ćwiczenia");
            exercise.setDescription (scanner.nextLine());

            System.out.println("Nowe dane to :");
            System.out.println(exercise);
            return exercise;
        }

        private static int giveMeRightId() {
            int id = -1;
            Scanner scanner = new Scanner(System.in);
            boolean rightId = false;
            while (!rightId) { // get right id
                while (!scanner.hasNextInt()) {  //get int number
                    System.out.println("Podaj Numer ćwiczenia :");
                    id = scanner.nextInt();
                    scanner.nextLine(); // clear buffer
                } // we have int number
                id=scanner.nextInt();
                scanner.nextLine();
                try (Connection connection = DbUtil.getConnection()) {
                    Exercise exercise = Exercise.loadExerciseById(connection, id);
                    rightId = true; // wczytano bez błędu czyli jest takie ćwiczenie
                } catch (SQLException e) {
                    System.out.println("Nie ma ĆWICZENIA o takim NUMERZE");
                    id = -1;
                }
            } // while 1
            return id;
        }

    }// last one Class
