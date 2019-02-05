package com.revature.models;

public class Account {
	private int id;
	private double balance;
	private String type;
	private String isJoint;

	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsJoint() {
		return isJoint;
	}

	public void setIsJoint(String isJoint) {
		this.isJoint = isJoint;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", type=" + type + ", isJoint=" + isJoint + "]";
	}

	public Account(int id, double balance, String type, String isJoint) {
		super();
		this.id = id;
		this.balance = balance;
		this.type = type;
		this.isJoint = isJoint;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((isJoint == null) ? 0 : isJoint.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (isJoint == null) {
			if (other.isJoint != null)
				return false;
		} else if (!isJoint.equals(other.isJoint))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

}
