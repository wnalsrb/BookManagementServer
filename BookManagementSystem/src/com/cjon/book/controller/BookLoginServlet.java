package com.cjon.book.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cjon.book.service.BookService;


@WebServlet("/booklogin")
public class BookLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BookLoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  //1. 입력받고
		  String id = request.getParameter("id");
		  String password = request.getParameter("password");
		  String callback = request.getParameter("callback");
		  

		  //2.로직처리
		  
		  BookService service = new BookService();
		  boolean result = service.logincheck(id, password);
		  
		  if(result) {
			   HttpSession session = request.getSession(true);
			   session.setAttribute("member_id", id);
		  } 
		  
		  //3.출력처리
		  response.setContentType("text/plain; charset=utf8");
		  PrintWriter out = response.getWriter();
		  out.println(callback + '(' + result + ')');
		  out.flush();
		  out.close();		  
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
