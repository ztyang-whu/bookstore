package servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Book;
import dao.BookDao;
import json.ModelToJson;

/**
 * Servlet implementation class Get_book_info
 */
@WebServlet("/getbookinfo")
public class Get_book_info extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Get_book_info() {
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
		String bookname=request.getParameter("bookname");
		String author=request.getParameter("author");
		String limitString=request.getParameter("limit");
		int limit=-1;
		if(!limitString.equals("")) {
			limit=Integer.parseInt(limitString);
		}
		ArrayList<Book> result=new ArrayList<Book>();
		BookDao bookDao=new BookDao();
		if(!bookname.equals("")) {
			try {
				result=bookDao.load_books_info_name(bookname);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(!author.equals("")) {
			try {
				result=bookDao.load_books_info_author(author);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(limit>0) {
			try {
				result=bookDao.load_books_info_limit(limit);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ModelToJson mtj=new ModelToJson();
		String jsonString=null;
		try {
			jsonString = mtj.bookToJson(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			DataOutputStream output=new DataOutputStream(response.getOutputStream());
			output.writeUTF(jsonString);
			output.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
