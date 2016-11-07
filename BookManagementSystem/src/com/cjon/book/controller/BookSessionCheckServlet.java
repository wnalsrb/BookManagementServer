package com.cjon.book.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/sessionCheck")
public class BookSessionCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BookSessionCheckServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		  String callback = request.getParameter("callback");
		  String check = request.getParameter("quit");

		  HttpSession session = request.getSession(true);
		  String member_id = (String) session.getAttribute("member_id");
		  
		  if(check!=null){
			  if(check.equals("t")){
				  session.invalidate();
			  }
		  }
		  
		  boolean result = true;
		  
		  if(member_id !=null){
			  result = true;
		  }else{
			  result = false;
		  }
		  
		  response.setContentType("text/plain; charset=utf8");
		  PrintWriter out = response.getWriter();
		  out.println(callback+"("+result+")");
		  out.flush();
		  out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
