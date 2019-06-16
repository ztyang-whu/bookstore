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
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public int check(Users user) throws SQLException{
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("GB2312");
		response.setContentType("text/html");
		String username=request.getParameter("name");
		String password=request.getParameter("password");
		Users user=new Users();
		System.out.println(password);
		user.setUsername(username);
		user.setPassword(password);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
