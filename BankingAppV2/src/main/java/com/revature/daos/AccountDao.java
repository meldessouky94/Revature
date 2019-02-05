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
				account.setid(id);
				account.setBalance(balance);
				account.setType(type);
				account.setIsJoint(isJoint);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountList;
	}

	public int getNextAccountID() {
		int accountID = -1;

		if (getAccounts().size() == 0) {
			return 0;
		}

		String query = "SELECT MAX(id) AS MAX_AID FROM accounts";
		try (Connection conn = ConnectionUtil.newConnection();
				PreparedStatement statement = conn.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery();) {
			if (resultSet.next()) {
				accountID = resultSet.getInt("MAX_AID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accountID + 1;
	}

	public Account createAccount(Account account, Customer customer) {
		int accountsCreated = 0;
		String query = "INSERT INTO accounts (id, balance, a_type, isJoint) VALUES (?, ?, ?, ?)";
		try (Connection conn = ConnectionUtil.newConnection();
				PreparedStatement statement = conn.prepareStatement(query)) {
			statement.setInt(1, getNextAccountID());
			statement.setDouble(2, account.getBalance());
			statement.setString(3, account.getType());
//			String isJoint = account.isJoint() ? "joint" : "single";
			statement.setString(4, account.getIsJoint());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String query2 = "INSERT INTO customersAccounts (aid, cid) VALUES (?, ?)";
		try (Connection conn = ConnectionUtil.newConnection();
				PreparedStatement statement = conn.prepareStatement(query2);) {
			statement.setString(1, customer.getEmail());
			statement.setInt(2, account.getid());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	public Account insertAccount(Account account) {
		try (Connection conn = ConnectionUtil.newConnection()) {
			String query = "INSERT INTO accounts (balance, a_type, isJoint) " + "VALUES (?, ?, ?) RETURNING id";

			PreparedStatement statement = conn.prepareStatement(query);

			statement.setDouble(1, account.getBalance());
			statement.setString(2, account.getType());
			statement.setString(3, account.getIsJoint());

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				account.setid(resultSet.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}

	public Account getAccountById(int id2) {
		Account a = null;
		String sql = "SELECT * FROM accounts WHERE ID = ?";

		ResultSet rs = null;
		try (Connection con = ConnectionUtil.newConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
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
	
	public Account update(Account account) 
	{
		try(Connection conn = ConnectionUtil.newConnection())
		{
			conn.setAutoCommit(false);
			String sql = "update accounts set balance = ? where id = ?";
			
			String[] keys = {"account_id"};
			
			PreparedStatement ps = conn.prepareStatement(sql, keys);
			ps.setDouble(1,account.getBalance());
			ps.setInt(2, account.getid());
			ps.executeQuery();
			conn.commit();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
}
}
