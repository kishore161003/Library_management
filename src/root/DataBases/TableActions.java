package root.DataBases;

import java.sql.*;
import java.util.Scanner;

public class TableActions {
    static String url = "jdbc:mysql://localhost:3306/db";
    static String userName = "root";
    static String password = "admin";
    static Scanner scanner = new Scanner(System.in);

    public static void addBook() throws Exception {
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        System.out.print("Enter Book Name: ");
        String bookName = scanner.nextLine().trim();
        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine().trim();
        System.out.print("Enter Genre: ");
        String genre = scanner.nextLine().trim();
        System.out.print("Enter Book Count: ");
        int bookCount = scanner.nextInt();
        if (statement.executeUpdate(
                "INSERT INTO Book(BookName, Author, Genre, Available,bookCount) VALUES ('" + bookName + "','"
                        + author + "','" + genre + "',true," + bookCount + ")") > 0) {
            System.out.println("Book Added Successfully");
        } else {
            System.out.println("Book Not Added");
        }
        connection.close();
    }

    public static void showTransactions() throws Exception {
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Transaction");
        System.out.println(" Transactions Id \t Book Id \tMember Id \tBorrow Date \t Return Date \t Status");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        System.out.println();
        while (resultSet.next()) {
            System.out.println("\t" + resultSet.getInt("TransactionId") + "\t\t"
                    + resultSet.getInt("BookId") + "\t\t"
                    + resultSet.getInt("MemberId") + "\t\t"
                    + resultSet.getString("BorrowDate") + "\t " + resultSet.getString("ReturnDate")
                    + "\t"
                    + resultSet.getString("Status"));
        }
        System.out.println();
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        connection.close();
    }

    public static void showAvailableBooks() throws Exception {
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Book WHERE Available = true");
        System.out.println("    Book Id \t\t Book Name \t\t Author \t\t Genre \t\t Book Count");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------");
        System.out.println();
        while (resultSet.next()) {
            System.out.println("\t" +
                    resultSet.getInt("BookId") + "\t\t" + resultSet.getString("BookName")
                    + "\t \t"
                    + resultSet.getString("Author") + "\t\t " + resultSet.getString("Genre")
                    + "\t\t" + resultSet.getInt("bookCount"));
        }
        System.out.println();
        System.out.println(
                "------------------------------------------------------------------------------------------------------------");
        connection.close();
    }

    public static void showAllBooks() throws Exception {
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Book");
        System.out.println("    Book Id \t\t Book Name \t\t Author \t\t Genre \t\t Book Count");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        System.out.println();
        while (resultSet.next()) {
            System.out.println("\t" +
                    resultSet.getInt("BookId") + "\t\t" + resultSet.getString("BookName")
                    + "\t\t "
                    + resultSet.getString("Author") + "\t\t " + resultSet.getString("Genre")
                    + "\t\t" + resultSet.getInt("bookCount"));
        }
        System.out.println();
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        connection.close();
    }

    public static void deleteBook() throws Exception {
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        showAllBooks();

        System.out.println();
        System.out.print("Enter Book Id: ");
        int bookId = scanner.nextInt();
        statement.executeUpdate("DELETE FROM Transaction WHERE BookId = " + bookId);
        if (statement.executeUpdate("DELETE FROM Book WHERE BookId = " + bookId) > 0) {
            System.out.println("Book Deleted Successfully");
        } else {
            System.out.println("Book Not Deleted");
        }
        connection.close();
    }

    public static int createMember() throws Exception {
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Member Name: ");
        String memberName = sc.nextLine().trim();
        System.out.print("Enter Member Phone: ");
        String memberPhone = sc.nextLine().trim();
        System.out.print("Enter Member Email: ");
        String memberEmail = sc.nextLine().trim();
        if (statement.executeUpdate(
                "INSERT INTO Member(MemberName, MemberPhone, MemberEmail) VALUES ('" + memberName + "','"
                        + memberPhone + "','" + memberEmail + "')") > 0) {
            System.out.println("Member Added Successfully");
        } else {
            System.out.println("Member Not Added");
        }
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Member ORDER BY memberId DESC LIMIT 1 ;");
        resultSet.next();
        System.out.println("Your Member Id is: " + resultSet.getInt("MemberId"));
        connection.close();
        return resultSet.getInt("MemberId");
    }

    public static void borrowBook() throws Exception {
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        showAvailableBooks();
        System.out.println();
        System.out.println("1.Exising Member");
        System.out.println("2.New Member");
        System.out.print("Enter Choice: ");
        int choice = Integer.parseInt(scanner.nextLine());
        if (choice == 1) {
            System.out.println("Enter Member Id: ");
            int memberId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Book Id: ");
            int bookId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Borrow Date: ");
            String borrowDate = scanner.nextLine();
            String query = "Insert into Transaction(BookId, MemberId, BorrowDate, Status) Values(" + bookId + ","
                    + memberId + ",'" + borrowDate + "','Borrowed')";
            if (statement.executeUpdate(query) > 0) {
                statement.execute(
                        "update book set Available = case when bookCount = 1 then false else true end, bookCount = bookCount - 1 where bookId = "
                                + bookId);
                System.out.println("Book Borrowed Successfully");
            } else {
                System.out.println("Book Not Borrowed");
            }
        } else if (choice == 2) {
            int memberId = createMember();
            System.out.print("Enter Book Id: ");
            String bookId = scanner.nextLine();
            System.out.println("Enter Borrow Date: ");
            String borrowDate = scanner.nextLine();
            String query = "Insert into Transaction(BookId, MemberId, BorrowDate, Status) Values(" + bookId + ","
                    + memberId + ",'" + borrowDate + "','Borrowed')";
            if (statement.executeUpdate(query) > 0) {
                statement.execute(
                        "update book set Available = case when bookCount = 1 then false else true end, bookCount = bookCount - 1 where bookId = "
                                + bookId);
                System.out.println("Book Borrowed Successfully");
            } else {
                System.out.println("Book Not Borrowed");
            }
        }
        connection.close();
    }

    public static int showTransactions(int memberId) throws Exception {
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Transaction WHERE MemberId = " + memberId);
        System.out.println(" Transactions Id \t Book Id \tBorrow Date \t Return Date \t Status");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        System.out.println();
        int counter = 0;
        while (resultSet.next()) {
            System.out.println("\t" + resultSet.getInt("TransactionId") + "\t\t"
                    + resultSet.getInt("BookId") + "\t\t"
                    + resultSet.getString("BorrowDate") + "\t " + resultSet.getString("ReturnDate")
                    + "\t"
                    + resultSet.getString("Status"));
            counter++;
        }
        System.out.println();
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        connection.close();
        return counter;
    }

    public static void returnBook() throws Exception {
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        System.out.println("Enter Member Id: ");
        int memberId = Integer.parseInt(scanner.nextLine());
        int n = showTransactions(memberId);
        if (n == 0) {
            System.out.println("\t\t\tThere is No Transactions");
            return;
        }
        System.out.println();
        System.out.print("Enter Transaction Id: ");
        int transactionId = Integer.parseInt(scanner.nextLine());
        ResultSet resultSet = statement
                .executeQuery("SELECT * FROM Transaction WHERE TransactionId = " + transactionId);
        if (!resultSet.next()) {
            System.out.println("Invalid Transaction ID");
            return;
        }
        System.out.print("Enter Return Date: ");
        String returnDate = scanner.nextLine();

        int bookId = resultSet.getInt("BookId");
        if (statement.executeUpdate(
                "UPDATE Transaction SET ReturnDate = '" + returnDate + "', Status = 'Returned' WHERE TransactionId = "
                        + transactionId) > 0) {
            statement.execute(
                    "update book set Available = true, bookCount = bookCount + 1 where bookId = "
                            + bookId);
            System.out.println("Book Returned Successfully");
        } else {
            System.out.println("Book Not Returned");
        }
        connection.close();
    }

    public static void showMembers() throws Exception {
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Member");
        System.out.println("    Member Id \t\t Member Name \t\t Member Phone \t\t Member Email");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        System.out.println();
        while (resultSet.next()) {
            System.out.println("\t" +
                    resultSet.getInt("MemberId") + "\t\t" + resultSet.getString("MemberName")
                    + "\t\t "
                    + resultSet.getString("MemberPhone") + "\t\t " + resultSet.getString("MemberEmail"));
        }
        System.out.println();
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        connection.close();
    }

    public static void searchBookByName() throws Exception {
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        System.out.print("Enter Book Name: ");
        String bookName = scanner.nextLine().trim();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Book WHERE BookName Like '%" + bookName + "%'");
        System.out.println();

        System.out.println("    Book Id \t\t Book Name \t\t Author \t\t Genre \t\t Book Count");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        System.out.println();
        while (resultSet.next()) {
            System.out.println("\t" +
                    resultSet.getInt("BookId") + "\t\t" + resultSet.getString("BookName")
                    + "\t\t "
                    + resultSet.getString("Author") + "\t\t " + resultSet.getString("Genre")
                    + "\t\t" + resultSet.getInt("bookCount"));
        }
        System.out.println();
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        connection.close();
    }

    public static void searchBookByAuthor() throws Exception {
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        System.out.print("Enter Author Name: ");
        String author = scanner.nextLine().trim();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Book WHERE Author LIKE '%" + author + "%'");
        System.out.println();
        System.out.println("    Book Id \t\t Book Name \t\t Author \t\t Genre \t\t Book Count");

        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        System.out.println();
        while (resultSet.next()) {
            System.out.println("\t" +
                    resultSet.getInt("BookId") + "\t\t" + resultSet.getString("BookName")
                    + "\t\t "
                    + resultSet.getString("Author") + "\t\t " + resultSet.getString("Genre")
                    + "\t\t" + resultSet.getInt("bookCount"));
        }
        System.out.println();
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        connection.close();
    }

    public static void searchBookByGenre() throws Exception {
        Connection connection = DriverManager.getConnection(url, userName, password);
        Statement statement = connection.createStatement();
        System.out.print("Enter Genre: ");
        String genre = scanner.nextLine().trim();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Book WHERE Genre = '" + genre + "'");
        System.out.println();
        System.out.println("    Book Id \t\t Book Name \t\t Author \t\t Genre \t\t Book Count");

        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        System.out.println();
        while (resultSet.next()) {
            System.out.println("\t" +
                    resultSet.getInt("BookId") + "\t\t" + resultSet.getString("BookName")
                    + "\t\t "
                    + resultSet.getString("Author") + "\t\t " + resultSet.getString("Genre")
                    + "\t\t" + resultSet.getInt("bookCount"));
        }
        System.out.println();
        System.out.println(
                "-------------------------------------------------------------------------------------------------------------");
        connection.close();
    }

    public static void Search() throws Exception {
        System.out.println("1.Search by Book Name");
        System.out.println("2.Search by Author Name");
        System.out.println("3.Search by Genre");
        System.out.println("Enter Your Choice: ");
        int searchChoice = Integer.parseInt(scanner.nextLine());
        if (searchChoice == 1) {
            searchBookByName();
        } else if (searchChoice == 2) {
            searchBookByAuthor();
        } else if (searchChoice == 3) {
            searchBookByGenre();
        } else {
            System.out.println("Invalid Choice");
        }
    }
}
