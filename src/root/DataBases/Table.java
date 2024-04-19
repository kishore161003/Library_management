package root.DataBases;

import java.sql.*;

public class Table {
    static String url = "jdbc:mysql://localhost:3306/db";
    static String userName = "root";
    static String password = "admin";
    static Connection connection = null;
    static Statement statement = null;

    public static void createBookTable() throws Exception {
        connection = DriverManager.getConnection(url, userName, password);
        statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE Book(BookId Int Primary Key Auto_increment, BookName Varchar(50), Author Varchar(50), genre Varchar(50), Available Boolean , bookCount Int)");
        connection.close();

    }

    public static void createMemberTable() throws Exception {
        connection = DriverManager.getConnection(url, userName, password);
        statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE Member(MemberId Int Primary Key Auto_increment, MemberName Varchar(50), MemberPhone Varchar(50), MemberEmail Varchar(50), password Varchar(50))");
    }

    public static void createTransactionTable() throws Exception {
        connection = DriverManager.getConnection(url, userName, password);
        statement = connection.createStatement();
        statement.execute(
                "CREATE TABLE Transaction(TransactionId Int Primary Key Auto_increment, BookId Int, MemberId Int, BorrowDate VarChar(50), ReturnDate VarChar(50) DEFAULT NULL,status Enum('Borrowed','Returned'),Foreign Key(BookId) References Book(BookId),Foreign Key(MemberId) References Member(MemberId))");

    }

    public static void createTables() throws Exception {
        connection = DriverManager.getConnection(url, userName, password);
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "Book", null);
        if (!tables.next()) {
            createBookTable();
            createMemberTable();
            createTransactionTable();
        }
    }

}
