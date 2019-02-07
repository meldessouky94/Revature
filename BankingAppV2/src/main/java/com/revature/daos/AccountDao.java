package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.util.ConnectionUtil;

public class AccountDao {

	//Get all accounts from accounts table, not really utilized for customers
	public List<Account> getAccounts() {
		List<Account> accountList = new ArrayList<>();
		String query = "SELECT * FROM accounts";

		try (Connection conn = ConnectionUtil.newConnection();
				PreparedStatement statement = conn.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery();) {

			Account account = new Account();

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				double balance = resultSet.getDouble("balance");
				String type = resultSet.getString("a_type");
				String isJoint = resultSet.getString("isJoint");
				account.setId(id);
				account.setBalance(balance);
				account.setType(type);
				account.setIsJoint(isJoint);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountList;
	}

	//Create
	public Account createAccount(Account account, Customer customer) {
		String query = "INSERT INTO accounts (id, balance, a_type, isJoint) VALUES (?, ?, ?, ?)";
		try (Connection conn = ConnectionUtil.newConnection();
				PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, account.getId());
			statement.setDouble(2, account.getBalance());
			statement.setString(3, account.getType());
//			String isJoint = account.isJoint() ? "joint" : "single";
			statement.setString(4, account.getIsJoint());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query2 = "INSERT INTO customersaccounts (id, email) VALUES (?, ?)";
		try (Connection conn = ConnectionUtil.newConnection();
				PreparedStatement statement = conn.prepareStatement(query2);) {
			statement.setInt(1, account.getId());
			statement.setString(2, customer.getEmail());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	public Account createJointAccount(Account account, List<Customer> customers) {
		String query = "INSERT INTO accounts (id, balance, a_type, isJoint) VALUES (?, ?, ?, ?)";
		try (Connection conn = ConnectionUtil.newConnection();
				PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, account.getId());
			statement.setDouble(2, account.getBalance());
			statement.setString(3, account.getType());
//			String isJoint = account.isJoint() ? "joint" : "single";
			statement.setString(4, account.getIsJoint());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < customers.size(); i++) {
			String query2 = "INSERT INTO customersaccounts (id, email) VALUES (?, ?)";
			try (Connection conn = ConnectionUtil.newConnection();
					PreparedStatement statement = conn.prepareStatement(query2);) {
				statement.setInt(1, account.getId());
				statement.setString(2, customers.get(i).getEmail());
				statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return account;
	}

	public Account insertAccount(Account account) {
		try (Connection conn = ConnectionUtil.newConnection()) {
			String query = "INSERT INTO accounts (id, balance, a_type, isJoint) " + "VALUES (?, ?, ?, ?) RETURNING id";

			PreparedStatement statement = conn.prepareStatement(query);

			statement.setInt(1, account.getId());
			statement.setDouble(2, account.getBalance());
			statement.setString(3, account.getType());
			statement.setString(4, account.getIsJoint());

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				account.setId(resultSet.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	public Account getAccountById(String id2) {
		Account a = null;
		String sql = "SELECT * FROM accounts WHERE id = 891";

		ResultSet rs = null;
		try (Connection con = ConnectionUtil.newConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
//			ps.setString(1, id2);
			rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				String type = rs.getString("a_type");
				String isJoint = rs.getString("isJoint");

				a = new Account(id, balance, type, isJoint);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return a;
	}

	public Account getAccountById(Connection conn, int id2) {
		Account a = null;
		String sql = "SELECT * FROM accounts WHERE ID = ?";

		ResultSet rs = null;
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, id2);
			rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				String type = rs.getString("a_type");
				String isJoint = rs.getString("isJoint");

				a = new Account(id, balance, type, isJoint);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return a;
	}

	public int update(Account account) {
		int usersUpdated = 0;
		String query = "UPDATE accounts SET balance = ?, a_type = ?, isJoint = ? WHERE id = ?";
		try (Connection conn = ConnectionUtil.newConnection(); PreparedStatement ps = conn.prepareStatement(query);) {
			conn.setAutoCommit(false);
			ps.setDouble(1, account.getBalance());
			ps.setInt(2, account.getId());
			ps.setString(3, account.getIsJoint());
			ps.setInt(4, account.getId());

			usersUpdated = ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usersUpdated;
	}
}
