package com.alten.kata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class ProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
		String jdbcUrl = "jdbc:mysql://localhost:3306/product_db";
		String username = "root";
		String password = "779999";


		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
			System.out.println("Connection successful!");
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
