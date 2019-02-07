package com.revature.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.util.ConnectionUtil;

public class CustomerDao {

	public int insertCustomer(Customer customer) {
		int usersCreated = 0;
		String sql = "INSERT INTO customers (first_name, last_name, email, c_password) VALUES (?, ?, ?, ?)";
		try (Connection con = ConnectionUtil.newConnection(); PreparedStatement statement = con.prepareStatement(sql)) {
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getEmail());
			statement.setString(4, customer.getPassword());
			usersCreated = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usersCreated;
	}

	public List<Customer> getCustomers() {
		List<Customer> customerList = new ArrayList<>();
		String query = "SELECT * FROM Customer";

		try (Connection conn = ConnectionUtil.newConnection();
				PreparedStatement statement = conn.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				Customer customer = new Customer();

				statement.setString(1, customer.getFirstName());
				statement.setString(2, customer.getLastName());
				statement.setString(3, customer.getEmail());
				statement.setString(4, customer.getPassword());

				customerList.add(customer);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerList;
	}

	public static Customer getCustomerById(String id) {
		Customer customer = null;
		String query = "SELECT * FROM customers WHERE email = ?";

		ResultSet resultSet = null;
		try (Connection conn = ConnectionUtil.newConnection();
				PreparedStatement statement = conn.prepareStatement(query);) {
			statement.setString(1, id);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				String password = resultSet.getString("c_password");

				customer = new Customer(firstName, lastName, email, password, false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	public Customer getCustomerById(Connection conn, String id) {
		Customer customer = null;
		String query = "SELECT * FROM customers WHERE email = ?";

		ResultSet resultSet = null;
		try (PreparedStatement statement = conn.prepareStatement(query);) {
			statement.setString(1, id);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				String password = resultSet.getString("c_password");

				customer = new Customer(firstName, lastName, email, password, false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

//	public static List<Map<String, String>> getAccounts(String email) {
//        try(Connection conn = ConnectionUtil.newConnection()){
//            String query = "SELECT aid FROM customersaccounts WHERE cid= ?;";
//
//            PreparedStatement statement = conn.prepareStatement(query);
//            
//            statement.setString(1, email);
//            
//            ResultSet rs = statement.executeQuery();
//            List<Map<String, String>> account = new ArrayList<>();
//            while(rs.next()) {
//                query = "SELECT * FROM accounts WHERE id = ?";
//                statement = conn.prepareStatement(query);
//                statement.setInt(1, rs.getInt("aid"));
//                ResultSet result = statement.executeQuery();
//                if(result.next()) {
//                    Map<String, String> a = new HashMap<>();
//                    a.put("id", String.valueOf(result.getInt("id")));
//                    a.put("balance", String.valueOf(result.getDouble("balance")));
//                    a.put("type", result.getString("account_type"));
//                    account.add(a);
//                }
//            }
//            return account;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<Map<String, String>>();
//	}
//}
	public static List<Account> getAcounts(String email) {
		String query = "SELECT * FROM accounts WHERE id = ANY( SELECT id FROM customersaccounts WHERE email = ANY(SELECT email FROM customers WHERE email = ?))";
		List<Account> customerAccounts = new ArrayList<>();
		
		try (Connection conn = ConnectionUtil.newConnection();
				PreparedStatement statement = conn.prepareStatement(query); ) {
			
			statement.setString(1, email);
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				Account a = new Account();
				
				int id = rs.getInt("id");
				int balance = rs.getInt("balance");
				String type = rs.getString("a_type");
				String isJoint = rs.getString("isJoint");
				
				a.setId(id);
				a.setBalance(balance);
				a.setType(type);
				a.setIsJoint(isJoint);
				
				customerAccounts.add(a);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return customerAccounts;
	}
}
	
