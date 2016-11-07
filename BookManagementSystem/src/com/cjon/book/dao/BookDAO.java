package com.cjon.book.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.cjon.book.common.DBTemplate;

public class BookDAO {

	public String select(String keyword) {
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			String sql = "select bisbn, bimgbase64, btitle, bauthor, bprice, breserve, member_id from book where btitle like ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("imgbase64", rs.getString("bimgbase64"));
				obj.put("title", rs.getString("btitle"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("price", rs.getString("bprice"));
				obj.put("reserve", rs.getString("breserve"));
				obj.put("member_id", rs.getString("member_id"));
				arr.add(obj);
			}
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}

	public boolean update(String isbn, String title, String author, String price) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
			String sql = "update book set btitle=?, bauthor=?, bprice=? where bisbn=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, author);
			pstmt.setInt(3, Integer.parseInt(price));
			pstmt.setString(4, isbn);
			
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}
	
	public String showDetail(String keyword) {
		
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			String sql = "select bisbn, btitle, bdate, bpage, bprice, bauthor, btranslator, bsupplement, bpublisher, bimgbase64 from book where bisbn=?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, keyword);
			rs = pstmt.executeQuery();
			
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("title", rs.getString("btitle"));
				obj.put("date", rs.getString("bdate"));
				obj.put("page", rs.getString("bpage"));
				obj.put("price", rs.getString("bprice"));
				obj.put("author", rs.getString("bauthor"));
				obj.put("translator", rs.getString("btranslator"));
				obj.put("supplement", rs.getString("bsupplement"));
				obj.put("publisher", rs.getString("bpublisher"));
				obj.put("imgbase64", rs.getString("bimgbase64"));
				arr.add(obj);
			}
			result = arr.toJSONString();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}
	
	public boolean addbook(String isbn, String title, String date, String page, String price, String author, String translator, String supplement, String publisher, String imgbase64){
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
			String sql = "insert into book(bisbn, btitle, bdate, bpage, bprice, bauthor, btranslator, bsupplement, bpublisher, bimgbase64) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			pstmt.setString(2, title);
			pstmt.setString(3, date);
			pstmt.setString(4, page);
			pstmt.setInt(5, Integer.parseInt(price));
			pstmt.setString(6, author);
			pstmt.setString(7, translator);
			pstmt.setString(8, supplement);
			pstmt.setString(9, publisher);
			pstmt.setString(10, imgbase64);
			
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		
		return result;
	}
	
	
	 public boolean logincheck(String id, String password){
		 Connection con = DBTemplate.getConnection();
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 boolean result = false;
		 
		 String input_password = null;
		 
		 try{
			 String sql = "select member_id, member_password from member where member_id = ?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setString(1, id);
			 rs = pstmt.executeQuery();
			 
			 while(rs.next()){
				 input_password = rs.getString("member_password");
				 
				 if(rs!=null){
					 if(password.equals(input_password)){
						 result = true;
					 }else{
						 result = false;
					 }
				 }
			 }
		 }catch(Exception e){
			 System.out.println(e);
		 }finally{
			   DBTemplate.close(rs);
			   DBTemplate.close(pstmt);
			   DBTemplate.close(con);
		 }
		 return result;
	 }

	public boolean insertMember(String id, String password, String email) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			String sql = "insert into member (member_id, member_password, member_email) values (?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			pstmt.setString(3, email);
			
			int count = pstmt.executeUpdate();
			
			if(count==1){
				result = true;
				DBTemplate.commit(con);
			}else{
				DBTemplate.rollback(con);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
		    DBTemplate.close(con);
		}
		 return result;
	}
	
	public boolean addComment(String id, String isbn, String title, String author, String date, String comment, String member_id){
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		boolean result = false;
	
		System.out.println("dao-1");
		
		try {
			String sql = "insert into book_comment (cid, bisbn, ctitle, cauthor, cdate, ctext, member_id) values (?, ?, ?, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.setString(2, isbn);
			pstmt.setString(3, title);
			pstmt.setString(4, author);
			pstmt.setString(5, date);
			pstmt.setString(6, comment);
			pstmt.setString(7, member_id);
			
			int count = pstmt.executeUpdate();
			
			System.out.println("dao-2");
			
			if(count==1){
				result = true;
				DBTemplate.commit(con);
			}else{
				DBTemplate.rollback(con);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
		    DBTemplate.close(con);
		}
		
		return result;
	}

	public String showComment(String isbn) {
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			String sql = "select cid, bisbn, ctitle, cauthor, cdate, ctext, member_id from book_comment where bisbn = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, isbn);
			System.out.println("isbn : " + isbn);
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("id", rs.getString("cid"));
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("title", rs.getString("ctitle"));
				obj.put("author", rs.getString("cauthor"));
				obj.put("date", rs.getString("cdate"));
				obj.put("comment", rs.getString("ctext"));
				obj.put("member_id", rs.getString("member_id"));
				arr.add(obj);
			}
			result = arr.toJSONString();
			
			System.out.println("result 0 : " + result);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}
	
	public String searchComment(String comment){
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			String sql = "select cid, bisbn, ctitle, cauthor, cdate, ctext, member_id from book_comment where ctext like ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, "%" + comment + "%");
			System.out.println("comment : " + comment);
			rs = pstmt.executeQuery();
			JSONArray arr = new JSONArray();
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				obj.put("id", rs.getString("cid"));
				obj.put("isbn", rs.getString("bisbn"));
				obj.put("title", rs.getString("ctitle"));
				obj.put("author", rs.getString("cauthor"));
				obj.put("date", rs.getString("cdate"));
				obj.put("comment", rs.getString("ctext"));
				obj.put("member_id", rs.getString("member_id"));
				arr.add(obj);
			}
			result = arr.toJSONString();
			
			System.out.println("result 0 : " + result);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(rs);
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}
	
	
	public boolean deleteComment(String number, String member_id){
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			String sql = "delete from book_comment where cid = ? and member_id = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, number);
			pstmt.setString(2, member_id);
			
			int count = pstmt.executeUpdate();
			
			if(count==1){
				result = true;
				DBTemplate.commit(con);
			}else{
				DBTemplate.rollback(con);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
		    DBTemplate.close(con);
		}
		return result;
	}
	
	public boolean updateComment(String number, String comment){
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		boolean result = false;
		try {
			String sql = "update book_comment set ctext = ? where cid = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, comment);
			pstmt.setString(2, number);
			
			int count = pstmt.executeUpdate();
			// 결과값은 영향을 받은 레코드의 수
			if( count == 1 ) {
				result = true;
				// 정상처리이기 때문에 commit
				DBTemplate.commit(con);
			} else {
				DBTemplate.rollback(con);
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
			DBTemplate.close(con);
		} 
		return result;
	}
	
	public boolean bookReserve(String isbn, String member_id){
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		boolean result = false;
	
		System.out.println("dao-1");
		
		try {
			String sql = "update book set member_id = ? where bisbn = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			pstmt.setString(2, isbn);
			
			int count = pstmt.executeUpdate();
			
			System.out.println("dao-2");
			
			if(count==1){
				result = true;
				DBTemplate.commit(con);
			}else{
				DBTemplate.rollback(con);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
		    DBTemplate.close(con);
		}
		
		return result;
	}
	
	public boolean bookReturn(String isbn, String member_id){
		Connection con = DBTemplate.getConnection();
		PreparedStatement pstmt = null;
		boolean result = false;
	
		System.out.println("dao-1");
		
		try {
			String sql = "update book set member_id = ? where bisbn = ?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member_id);
			pstmt.setString(2, isbn);
			
			int count = pstmt.executeUpdate();
			
			System.out.println(isbn);
			System.out.println(count);
			if(count==1){
				result = true;
				DBTemplate.commit(con);
			}else{
				DBTemplate.rollback(con);
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			DBTemplate.close(pstmt);
		    DBTemplate.close(con);
		}
		
		return result;
	}
}
















