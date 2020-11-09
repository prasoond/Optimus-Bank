package com.bank.service;

import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.dao.AppDao;
import com.bank.model.User;
@Service
public class AppServiceImpl implements AppService {
    @Autowired 
    AppDao ad;

    public String addUser(User u)
	{
	return ad.addUser(u);
	}

    public String getLogin(String username, String password)
    {
		return ad.getLogin(username,password);
    	
    }
    
    public String infoCheck(String account_no, String username)
    {
    	return ad.infoCheck(account_no,username);
    }
	public String updateCustomerPassword(String email,String password)
	{
		return ad.updateCustomerPassword(email,password);
	}
	
	public ResultSet getInfo(String username)
	{
		return ad.getInfo(username);
	}

}

