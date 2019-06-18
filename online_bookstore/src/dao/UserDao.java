package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import servlet.Users;

public class UserDao {
	Connection conn=null;

	PreparedStatement prepstmt=null;
	
	final String DB_DRIVER="com.mysql.jdbc.Driver";
	final String DB_URL=
			"jdbc:mysql://localhost:3306/bookstore?autoReconnect=true&useUnicode=true&useSSL=true";
	final String DB_USER="root";
	final String DB_PASSWARD="123456";
	
	/**
	 * 用于登陆验证
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public int login_check(Users user) throws SQLException{
   
		try {
			Class.forName(DB_DRIVER);
			conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWARD);
			String sql="select password from users where user_name=?";
			prepstmt=conn.prepareStatement(sql);
			prepstmt.setString(1, user.getUsername());
			ResultSet result=prepstmt.executeQuery();
			String password_in_database=null;
			while(result.next()) {
				password_in_database=result.getString(1);
			}
			if(password_in_database==null) {
				return -1;						//password为空说明没有这个id，用户不存在
			}else if(!password_in_database.equals(user.getPassword())) {
				return -2;						//密码不正确
			}else {
				return 1;						//用户存在且密码正确
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
		return -3;								//出现意外
	}
	
	/**
	 * 用于验证用户名和邮箱是否被使用过
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public int signup_check(Users user) throws SQLException{
		
		try {
			Class.forName(DB_DRIVER);
			conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWARD);
			String sql="select user_name, email from users where user_name=?";
			prepstmt=conn.prepareStatement(sql);
			prepstmt.setString(1, user.getUsername());
			ResultSet result=prepstmt.executeQuery();
			String username=null;
			String email=null;
			while(result.next()) {
				username=result.getString(1);
				email=result.getString(2);
			}
			if(username!=null) {
				return -1;						//用户名已存在
			}
			if(email!=null&&email.equals(user.getEmail())) {
				return -2;						//该邮箱已经被注册
			}else {
				if(insert(user)==1)
					return 1;					//注册成功
				else {
					return -3;					//注册失败
				}
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
		return -4;								//出现意外
	}
	/**
	 * 用户注册
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	 private int insert(Users user) throws SQLException{
	    	Connection conn=null;

			PreparedStatement prepstmt=null;
			
			final String DB_DRIVER="com.mysql.jdbc.Driver";
			final String DB_URL=
					"jdbc:mysql://localhost:3306/bookstore?autoReconnect=true&useUnicode=true&useSSL=true";
			final String DB_USER="root";
			final String DB_PASSWARD="123456";
			
			try {
				Class.forName(DB_DRIVER);
				conn=DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWARD);
				String sql="INSERT INTO `bookstore`.`users` (`user_name`, `password`, `phone_num`) VALUES (?, ?, ?); ";
				prepstmt=conn.prepareStatement(sql);
				prepstmt.setString(1, user.getUsername());
				prepstmt.setString(2, user.getPassword());
				prepstmt.setString(3, user.getEmail());
				prepstmt.executeUpdate();
			}catch(Exception e) {
				e.printStackTrace();
				return -1;							//插入失败
			}finally {
				if(conn!=null) {
					try {
						conn.close();
					}catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
			return 1;								//插入成功
	    }
}
