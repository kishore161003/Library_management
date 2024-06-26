package root;

import java.util.Scanner;

import root.DataBases.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Table.createTables();
        Scanner sc = new Scanner(System.in);
        System.out.println("\t\t\t--------------------Welcome to Library Management System------------------");
        System.out.println();
        System.out.println("1.Create an account\n2.Login");
        System.out.println();
        System.out.print("Enter Your choice: ");
        int ch = Integer.parseInt(sc.nextLine());
        int id = 0;
        if (ch == 1) {
            System.out.println("Enter your name: ");
            String name = sc.nextLine();
            System.out.println("Enter your phone number: ");
            String phone = sc.nextLine();
            System.out.println("Enter your email: ");
            String email = sc.nextLine();
            System.out.println("Enter your password: ");
            String password = sc.nextLine();
            id = TableActions.createMember(name, phone, email, password);
        } else if (ch == 2) {
            System.out.println("Enter your email: ");
            String email = sc.nextLine();
            System.out.println("Enter your password: ");
            String password = sc.nextLine();
            id = TableActions.login(email, password);
            if (id == -1) {
                return;
            }
        } else {
            System.out.println("Invalid choice");
            return;
        }

        int choice = 0;

        if (id == 1) {
            do {
                System.out.println();
                System.out.println("\t\t\t--------------------Welcome to Library Management System------------------");
                System.out.println();
                System.out.println("1. Add Book");
                System.out.println("2. Show Available Books");
                System.out.println("3. Show All Books");
                System.out.println("4. Show ALL Transactions");
                System.out.println("5. Delete the Book");
                System.out.println("6. show all members");
                System.out.println("7. Exit");
                System.out.println();
                System.out.print("Enter Your choice : ");
                choice = Integer.parseInt(sc.nextLine());
                System.out.println();
                switch (choice) {
                    case 1:
                        TableActions.addBook();
                        break;
                    case 2:
                        TableActions.showAvailableBooks();
                        break;
                    case 3:
                        TableActions.showAllBooks();
                        break;
                    case 4:
                        TableActions.showTransactions();
                        break;
                    case 5:
                        TableActions.deleteBook();
                        break;
                    case 6:
                        TableActions.showMembers();
                        break;
                    case 7:
                        System.out.println("Thank You for using Library Management System");
                        break;
                    default:
                        System.out.println("Invalid Choice");
                        System.out.println("Exiting...");
                        return;
                }
            } while (choice < 7 && choice > 0);
        } else {
            do {
                System.out.println();
                System.out.println("\t\t\t--------------------Welcome to Library Management System------------------");
                System.out.println();
                System.out.println("1. Show Available Books");
                System.out.println("2. Show All Books");
                System.out.println("3. Borrow Book");
                System.out.println("4. Return Book");
                System.out.println("5. Show my Transactions");
                System.out.println("6. Search the Book");
                System.out.println("7. Exit");
                System.out.println();
                System.out.print("Enter Your choice : ");
                choice = Integer.parseInt(sc.nextLine());
                System.out.println();
                switch (choice) {
                    case 1:
                        TableActions.showAvailableBooks();
                        break;
                    case 2:
                        TableActions.showAllBooks();
                        break;
                    case 3:
                        TableActions.borrowBook(id);
                        break;
                    case 4:
                        TableActions.returnBook(id);
                        break;
                    case 5:
                        TableActions.showTransactions(id);
                        break;
                    case 6:
                        TableActions.Search();
                        break;
                    case 7:
                        System.out.println("Thank You for using Library Management System");
                        break;
                    default:
                        System.out.println("Invalid Choice");
                        System.out.println("Exiting...");
                        return;
                }
            } while (choice < 7 && choice > 0);
        }
    }
}
