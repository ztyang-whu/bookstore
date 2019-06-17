package servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
/**
 * Servlet implementation class JsonTest
 */
@WebServlet("/signup")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp() {
        super();
        // TODO Auto-generated constructor stub
    }
    
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
    
    private int check(Users user) throws SQLException{
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		String username=request.getParameter("name");
		String password=request.getParameter("password");
		String email=request.getParameter("email");
		Users user=new Users();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		int result=0;
		try {
			result=check(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DataOutputStream output=new DataOutputStream(response.getOutputStream());
			String jsonresult="";
			JSONObject jsonObj=new JSONObject();
			jsonObj.put("result", result);
			jsonresult=jsonObj.toString();
			output.writeUTF(jsonresult);
			output.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
