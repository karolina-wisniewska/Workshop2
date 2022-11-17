package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static final String CREATE_DATABASE_QUERY =
            "CREATE DATABASE IF NOT EXISTS workshop2 " +
            "    CHARACTER SET utf8mb4 " +
            "    COLLATE utf8mb4_unicode_ci; ";

    public static final String CREATE_TABLE_USERS_QUERY =
            "CREATE TABLE IF NOT EXISTS workshop2.users (" +
            "    id INT(11) PRIMARY KEY AUTO_INCREMENT NOT NULL , " +
            "    email VARCHAR(255) UNIQUE NOT NULL , " +
            "    username VARCHAR(255) NOT NULL , " +
            "    password VARCHAR(60) NOT NULL " +
            ");";

    public static void main(String[] args) {

        User user1 = new User();
        user1.setUserName("User1");
        user1.setEmail("user1@email.com");
        user1.setPassword("pass1");



    }
}
