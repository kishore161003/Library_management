package root;

import java.util.Scanner;

import root.DataBases.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Table.createTables();
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        System.out.println("Are you an admin or a member?  ");
        System.out.println("1. Admin \n2. Member");
        int userType = Integer.parseInt(sc.nextLine());

        if (userType == 1) {
            do {
                System.out.println();
                System.out.println("\t\t\t--------------------Welcome to Library Management System------------------");
                System.out.println();
                System.out.println("1. Add Book");
                System.out.println("2. Show Available Books");
                System.out.println("3. Show All Books");
                System.out.println("4. Show ALL Transactions");
                System.out.println("5. Delete the Book");
                System.out.println("6.show all members");
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
        } else if (userType == 2) {
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
                        TableActions.borrowBook();
                        break;
                    case 4:
                        TableActions.returnBook();
                        break;
                    case 5:
                        System.out.print("Enter Member Id: ");
                        int memberId = Integer.parseInt(sc.nextLine());
                        TableActions.showTransactions(memberId);
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
        } else {
            System.out.println("Invalid user type");
        }
    }
}
