package servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bean.Users;
import dao.UserDao;
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
		response.setCharacterEncoding("UTF-8");
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
			UserDao userDao=new UserDao();
			result=userDao.signup_check(user);
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
