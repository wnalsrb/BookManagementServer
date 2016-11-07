package com.cjon.book.service;

import com.cjon.book.dao.BookDAO;

public class BookService {

	// 리스트를 가져오는 일을 하는 method
	public String getList(String keyword) {
		// 일반적인 로직처리 나와요!!
		
		// 추가적으로 DB처리가 나올 수 있어요!
		// DB처리는 Service에서 처리는하는게 아니라..다른 객체를 이용해서
		// Database처리를 하게 되죠!!
		BookDAO dao = new BookDAO();
		String result = dao.select(keyword);	
		
		return result;
	}

	public boolean updateBook(String isbn, String title, String author, String price) {
		// TODO Auto-generated method stub
		BookDAO dao = new BookDAO();
		boolean result = dao.update(isbn, title, author, price);	
		return result;
	}
	
	public String showDetail(String keyword){
		BookDAO dao = new BookDAO();
		String result = dao.showDetail(keyword);
		return result;
	}
	
	public boolean addbook(String isbn, String title, String date, String page, String price, String author, String translator, String supplement, String publisher, String imgbase64){
		BookDAO dao = new BookDAO();
		boolean result = dao.addbook(isbn, title, date, page, price, author, translator, supplement, publisher, imgbase64);	
		return result;
	}
		 
	 public boolean logincheck(String id, String password){
	  
	  BookDAO dao = new BookDAO();
	  boolean result = dao.logincheck(id, password);
	  return result;
	  
	 }

	public boolean insertMember(String id, String password, String email) {
		BookDAO dao = new BookDAO();
		boolean result = dao.insertMember(id, password, email);
		return result;
	} 
	
	public boolean addComment(String id, String isbn, String title, String author, String date, String comment, String member_id){
		BookDAO dao = new BookDAO();
		boolean result = dao.addComment(id, isbn, title, author, date, comment, member_id);
		return result;
	}

	public String showComment(String isbn) {
		BookDAO dao = new BookDAO();
		String result = dao.showComment(isbn);
		return result;
	}
	
	public String searchComment(String comment){
		BookDAO dao = new BookDAO();
		String result = dao.searchComment(comment);
		return result;
	}
	
	public boolean deleteComment(String number, String member_id){
		BookDAO dao = new BookDAO();
		boolean result = dao.deleteComment(number, member_id);
		return result;
	}
	
	public boolean updateComment(String number, String comment){
		BookDAO dao = new BookDAO();
		boolean result = dao.updateComment(number, comment);
		return result;
	}
	
	public boolean bookReserve(String isbn, String member_id){
		BookDAO dao = new BookDAO();
		boolean result = dao.bookReserve(isbn, member_id);
		return result;
	}
	
	public boolean bookReturn(String isbn, String member_id){
		BookDAO dao = new BookDAO();
		boolean result = dao.bookReturn(isbn, member_id);
		return result;
	}
}












