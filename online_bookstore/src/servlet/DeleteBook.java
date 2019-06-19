package servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import bean.Book;
import dao.BookDao;

/**
 * Servlet implementation class DeleteBook
 */
@WebServlet("/deletebook")
public class DeleteBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteBook() {
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
		request.setCharacterEncoding("UTF-8");
		int result=0;
		String book_id=request.getParameter("book_id");
		Book book=new Book();
		book.setBook_id(Integer.parseInt(book_id));
		BookDao bookDao=new BookDao();
		try{
			result=bookDao.deletebook(book);
		}catch (Exception e) {
			// TODO: handle exception
			result=-1;				//失败
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
