package com.bigdev.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String username = request.getParameter("name");
	      String email = request.getParameter("email");
	      String password = request.getParameter("pass");
	      String Repassword = request.getParameter("re_pass");
	      String contact = request.getParameter("contact");
	      RequestDispatcher dispatcher = null;
	      Connection con = null;
	      
	      
	      if(username == null || username.equals("")) {
				request.setAttribute("status", "invalidUsername");
				dispatcher = request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response);
			}
	      

	      if(email  == null || email.equals("")) {
				request.setAttribute("status", "invalidEmail");
				dispatcher = request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response);
			}
	      

	      if(password  == null || password.equals("")) {
				request.setAttribute("status", "invalidPassword");
				dispatcher = request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response); 
			}else if(!password.equals(Repassword)) {
				request.setAttribute("status", "invalidConfirmPassword");
				dispatcher = request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response); 
				
			}
	      
	      if(contact  == null || contact.equals("")) {
				request.setAttribute("status", "invalidContact");
				dispatcher = request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response);
			}else if(contact.length() > 11 && contact.length() < 11 ) {
				request.setAttribute("status", "invalidContactLength ");
				dispatcher = request.getRequestDispatcher("registration.jsp");
				dispatcher.forward(request, response);
				
			}
	      
	      
	     
	      
	      try {
	    	  Class.forName("com.mysql.cj.jdbc.Driver");
	    	  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/signupaccount?useSSL=false", "root","1234567");
	    	  PreparedStatement pst = con.prepareStatement("insert into users (username, password, email, contact) values(?,?,?,?)"); 
	    	  pst.setString(1, username);
	    	  pst.setString(2, password);
	    	  pst.setString(3, email);
	    	  pst.setString(4, contact);
	    	  
	    	  int rowCount = pst.executeUpdate();
	    	  dispatcher = request.getRequestDispatcher("registration.jsp");
	    	  if (rowCount > 0 ) {
	    		  request.setAttribute("status", "success");
	    		  
	    	  }else {
	    		  request.setAttribute("status", "failed");
	    	  }
	    	  
	    	  dispatcher.forward(request, response);
	      }catch(Exception e){
	    	  
	    	  e.printStackTrace();
	    	  
	      }
	      
	      finally {
	    	  
	    	  try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    	  
      }
	}

}

