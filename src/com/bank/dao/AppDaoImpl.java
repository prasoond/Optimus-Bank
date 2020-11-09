 package com.bank.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bank.model.User;

@Repository
public class AppDaoImpl implements AppDao {
	
	@Autowired 
	JdbcTemplate jdbcTemplate;
	

	
	
	public String addUser(User u) //this method is with usin User class of Model
	{final String procedurecall = "{call Proc_App(?,?,?)}";
	Connection con=null;
	try {
	  


	 con =	jdbcTemplate.getDataSource().getConnection();  //connection established
	  CallableStatement cs = con.prepareCall(procedurecall);
	  cs.setString(1,"infoCheck");
	  cs.setString(2,u.getAccount_no());
	  cs.setString(3,u.getUsername());
	  ResultSet rs = cs.executeQuery();
	  
	  
	  
	 if(rs.next())
	   {
		return "Username/account number already register";
	   }
	else
    	{
			final String procedurecall1 = "{call Proc_App(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
			CallableStatement cs1 = con.prepareCall(procedurecall1);
			  cs1.setString(1,"insert");
			  cs1.setString(2,u.getAdmin_refid());
			  cs1.setString(3,u.getFirst_name());
			  cs1.setString(4,u.getLast_name());
			  cs1.setString(5,u.getAccount_no());
			  cs1.setString(6,u.getOccupation());
			  cs1.setString(7,u.getEmail());
			  cs1.setString(8,u.getUsername());
			  cs1.setString(9,u.getPassword());
			  cs1.setString(10,u.getBirthday());
			  cs1.setString(11,u.getGender());
			  cs1.setString(12,u.getPhone());
			  cs1.setString(13,u.getStreet());
			  cs1.setString(14,u.getDistrict());
			  cs1.setString(15,u.getState());
			  cs1.setString(16,u.getDebit_card());
			  cs1.setString(17,u.getCredit_card());
			  cs1.setString(18,u.getNet_banking());
			  cs1.execute();
        return "email not registered";
    	}
	} catch(Exception e)
	{
		e.printStackTrace();
	return "";
	}
     finally
     {
		try{
			con.close();
		   } catch(Exception e){
	   }
	 }
	}
//method add user ends here

	public String getLogin(String username, String password)
	{
		String dbPassword = null;
		final String procedurecall = "{call Proc_App(?,?)}";
		Connection con=null;
		try {
		  con =	jdbcTemplate.getDataSource().getConnection();  //connection established
		  CallableStatement cs = con.prepareCall(procedurecall);
		  cs.setString(1,"getPassword");
		  cs.setString(2,username);
		  ResultSet rs = cs.executeQuery();
		rs.next();
		dbPassword =rs.getString(9);
		System.out.println("jgg " + dbPassword);
		  return dbPassword;
	 } catch(Exception e) {
	    	e.printStackTrace();
			System.out.println("exception in login in AppDaoimpl");
         	return "dbPassword";	  
		}	
 finally {
      	 try{
			con.close();			
			} catch(Exception e){
			   }
		}
}
	
	public String infoCheck(String account_no, String username)
	{
		final String procedurecall = "{call Proc_App(?,?,?)}";
		Connection con=null;
		try {
		  


		 con =	jdbcTemplate.getDataSource().getConnection();  //connection established
		  CallableStatement cs = con.prepareCall(procedurecall);
		  cs.setString(1,"infoCheck1");
		  cs.setString(2,account_no);
		  cs.setString(3,username);
		  ResultSet rs = cs.executeQuery();
		  
		  
		  
		 if(rs.next())
		   {
			 System.out.println("email");
			return rs.getString("email");
		   }
		 else
		 {   System.out.println("noemail");
			 return "customer_forgot";
		 }
	}catch(Exception e)
		{
		  return "";
		}

	}

	public String updateCustomerPassword(String email, String password)
	{
		final String procedurecall = "{call Proc_App(?,?,?)}";
		Connection con=null;
		try {
		  con =	jdbcTemplate.getDataSource().getConnection();  //connection established
		  CallableStatement cs = con.prepareCall(procedurecall);
		  cs.setString(1,"updatePassword");
		  cs.setString(2,email);
		  cs.setString(3,password);
		 
		  cs.executeUpdate();

	      return "customer_login";

	  } catch(Exception e) {
	  

			e.printStackTrace();
			System.out.println("exception in updatepassword in AppDaoimpl");
			return null;

			
		}
		 finally {
	     	 
				try{
					
					con.close();			
				} catch(Exception e){
				   }

	}


}
	public ResultSet getInfo(String username)
	{
		Connection con = null;
		final String procedurecall = "{call Proc_App(?,?)}";
		try {
			con = jdbcTemplate.getDataSource().getConnection();
			CallableStatement cs = con.prepareCall(procedurecall);
			cs.setString(1,"getInfo");
			cs.setString(2,username);
			ResultSet rs = cs.executeQuery();
		   return rs;	
		}catch(Exception e)
		{
			return null;

		}
		
	}

}

