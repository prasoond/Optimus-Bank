package com.bank.controller;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bank.mail.SendMail;
import com.bank.model.User;
import com.bank.service.AppService;

import java.net.IDN;
import java.net.IDN.*;

@Controller
public class AppController {
	
	@Autowired 
	AppService as;
	@RequestMapping(value="/")
	public String home()
	{
		return "index";
	}
	
	@RequestMapping(value="admin_user_login")
	public String admin_user()
	{
		return "admin_user_select";
	}

	@RequestMapping(value="admin_user")
	public String admin_user(HttpServletRequest req)
	{
		return "admin_login";
	}
	@RequestMapping(value="customer_user")
	public String customer_user(HttpServletRequest req)
	{
		return "customer_login";
	}
	
	@RequestMapping(value="signup")
	public String signup_form()
	{
		return "customer_signup";
	}
	
	
	   @RequestMapping(value="adduser",method = RequestMethod.POST)
	   public String customer_data(HttpServletRequest req,User u)
	 {  
	     	String msg = as.addUser(u);
	    	if(msg.equals("Username/account number already register"))
		    {
			  req.setAttribute("msg","Username/account number already register");
			  return "customer_signup";
	    	}
     		else
    	 	{
	            req.setAttribute("msg","succesfully signup");
		    	return "customer_login";
		    }
	 }

	
	@RequestMapping(value="customer_login",method = RequestMethod.POST)
	public String customer_login(HttpServletRequest req)
	{
		String username1 = req.getParameter("username");
		String password = req.getParameter("password");
		String dbPassword=as.getLogin(username1,password);
        System.out.println(password);
        System.out.println(dbPassword);
		boolean s = password.equals(dbPassword);   
		if(s) 
           {
			  ResultSet rs = as.getInfo(username1);
			try {
				rs.next();
				String first_name = rs.getString(3);
				String last_name = rs.getString(4);
				String account_no = rs.getString(5);
				String occupation = rs.getString(6);
				String email = rs.getString(7);
				String username = rs.getString(8);
				String birthday = rs.getString(10);
				String phone = rs.getString(12);
				String street = rs.getString(13);
				String district = rs.getString(14);
				String state = rs.getString(15);
				
				req.setAttribute("first_name",first_name);
				 req.setAttribute("last_name",last_name);
				 req.setAttribute("account_no",account_no);
				 req.setAttribute("occupation",occupation);
				 req.setAttribute("email",email);
				 req.setAttribute("username",username);
				 req.setAttribute("birthday",birthday);
				 req.setAttribute("phone",phone);
				 req.setAttribute("street",street );
				 req.setAttribute("district",district);
				 req.setAttribute("state",state);
				 req.setAttribute("username", username);

			
			}catch(Exception e)
			{
				System.out.println("ecxxx");
				e.printStackTrace();
			}
        	   return "customer_profile";
           }
		
           else
		{
			req.setAttribute("email_password_incorrect","email/password is incorrect.");
			return "customer_login";
		}
	}
	
	@RequestMapping(value="customer_forgot")
	public String customer_forgot()
	{
		return "customer_forgot";
		
	}
	
	
	@RequestMapping(value="infoCheck",method = RequestMethod.POST)
	public String customer_forgot(HttpServletRequest req)
	{   String account_no = req.getParameter("account_no");
	    String username = req.getParameter("username");
	    String x = as.infoCheck(account_no,username);
	if(x.equals("customer_forgot"))
	{
		req.setAttribute("no_such_account","No such account found.");
		return "customer_forgot";
	}
	else {
		SendMail s = new SendMail();
		s.sendMail("<p> To Resert password<br><a href='http://localhost:1000/Bank/resetPassword?email="+x+"> Click Here </a> </p>", x, "Optimus Bank Reset Password");
		
		req.setAttribute("email",x);
		return "wait";
	}
	}

@RequestMapping(value="resetPassword",method = RequestMethod.POST)
public String resetPassword(HttpServletRequest req)
{   String email = req.getParameter("email");
    String password = req.getParameter("passowrd"); 
	return as.updateCustomerPassword(email,password);
	
}





}


