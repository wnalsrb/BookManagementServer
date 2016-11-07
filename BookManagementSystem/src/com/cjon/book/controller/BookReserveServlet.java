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

@WebServlet("/bookReserve")
public class BookReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BookReserveServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String isbn = request.getParameter("isbn");
		
		String callback = request.getParameter("callback");
		
		System.out.println("servlet-1");
		// 2. 로직처리
		
		HttpSession session = request.getSession(true);
		String member_id = (String) session.getAttribute("member_id");
		
		
		BookService service = new BookService();
		boolean result = service.bookReserve(isbn, member_id);	
		// 3. 출력처리
		response.setContentType("text/plain; charset=utf8");
		PrintWriter out = response.getWriter();
		out.println(callback + "(" + result + ")");
		out.flush();
		out.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
