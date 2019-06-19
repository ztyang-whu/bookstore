package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Book;

public class BookDao {
	Connection conn=null;

	PreparedStatement prepstmt=null;
	
	final String DB_DRIVER="com.mysql.jdbc.Driver";

	final String DB_URL=
			"jdbc:mysql://localhost:3306/bookstore?autoReconnect=true&characterEncoding=utf8&useSSL=true";
	final String DB_USER="root";
	final String DB_PASSWARD="123456";
	/**
	 * 根据limit选取相应数量的book
	 * @param limit
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Book> load_books_info_limit(int limit) throws SQLException{
		ArrayList<Book> books=new ArrayList<Book>();
		try {
			Class.forName(DB_DRIVER);
			conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWARD);
			String sql="select * from books limit ?";
			prepstmt=conn.prepareStatement(sql);
			prepstmt.setInt(1, limit);
			ResultSet result=prepstmt.executeQuery();
			
			while(result.next()) {
				Book book=new Book();
				book.setBook_id(result.getInt(1));
				book.setBook_name(result.getString(2));
				book.setBook_author(result.getString(3));
				book.setBook_photo_path(result.getString(4));
				book.setBook_stock(result.getInt(5));
				book.setBook_price(result.getString(6));
				books.add(book);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return books;
	}
	/**
	 * 根据书名选取相应的图书
	 * @param book_name
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Book> load_books_info_name(String book_name) throws SQLException{
		ArrayList<Book> books=new ArrayList<Book>();
		try {
			Class.forName(DB_DRIVER);
			conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWARD);
			String sql="select * from books where book_name=?";
			prepstmt=conn.prepareStatement(sql);
			prepstmt.setString(1, book_name);		
			ResultSet result=prepstmt.executeQuery();
			
			while(result.next()) {
				Book book=new Book();
				book.setBook_id(result.getInt(1));
				book.setBook_name(result.getString(2));
				book.setBook_author(result.getString(3));
				book.setBook_photo_path(result.getString(4));
				book.setBook_stock(result.getInt(5));
				book.setBook_price(result.getString(6));
				books.add(book);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return books;
	}
	/**
	 * 根据作者名选取相应的图书
	 * @param book_name
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Book> load_books_info_author(String book_author) throws SQLException{
		ArrayList<Book> books=new ArrayList<Book>();
		try {
			Class.forName(DB_DRIVER);
			conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWARD);
			String sql="select * from books where book_author=?";
			prepstmt=conn.prepareStatement(sql);
			prepstmt.setString(1, book_author);
			ResultSet result=prepstmt.executeQuery();
			
			while(result.next()) {
				Book book=new Book();
				book.setBook_id(result.getInt(1));
				book.setBook_name(result.getString(2));
				book.setBook_author(result.getString(3));
				book.setBook_photo_path(result.getString(4));
				book.setBook_stock(result.getInt(5));
				book.setBook_price(result.getString(6));
				books.add(book);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return books;
	}
	/**
	 * 添加书籍
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public int addbook(Book book) throws Exception{
		try {
			Class.forName(DB_DRIVER);
			conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWARD);
			String sql="INSERT INTO `bookstore`.`books` (`book_name`, `book_author`, "
					+ "`book_photo_path`, `book_stock`, `book_price`) VALUES (?, ?, ?, ?, ?);";
			prepstmt=conn.prepareStatement(sql);
			prepstmt.setString(1, book.getBook_name());
			prepstmt.setString(2, book.getBook_author());
			prepstmt.setString(3, book.getBook_photo_path());
			prepstmt.setInt(4, book.getBook_stock());
			prepstmt.setString(5, book.getBook_price());
			prepstmt.executeUpdate();
			return 1;							//插入成功
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		return -1;				//插入失败
	}
	/**
	 * 删除某本书
	 * @param book
	 * @return
	 * @throws Exception
	 */
	public int deletebook(Book book) throws Exception{
		try {
			Class.forName(DB_DRIVER);
			conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWARD);
			String sql="DELETE FROM `books` WHERE (`book_id` = ?)";
			prepstmt=conn.prepareStatement(sql);
			prepstmt.setInt(1, book.getBook_id());
			prepstmt.executeUpdate();
			return 1;						//删除成功
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(conn!=null) {
				try {
					conn.close();
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		return -1;							//删除失败
	}
}
