package com.bank.dao;

import java.sql.ResultSet;

import com.bank.model.User;

public interface AppDao {

	public String addUser(User u);

	public String getLogin(String username, String password);

	public String infoCheck(String account_no, String username);

	public String updateCustomerPassword(String email, String password);

	public ResultSet getInfo(String username);


}
