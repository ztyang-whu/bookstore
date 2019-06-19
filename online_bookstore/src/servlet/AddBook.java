package servlet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import bean.Book;
import dao.BookDao;
import load.Upload;

/**
 * Servlet implementation class AddBook
 */
@WebServlet("/addbook")
public class AddBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBook() {
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
		int result = 0;
		Boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			result=-1;								//非二进制流
		} else {
			try {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items = upload.parseRequest(request);
				Book book=new Book();
				Upload upload2 = new Upload();
				result = upload2.upload_book(items, book);
				
				if (result == 1) {
					BookDao bookDao=new BookDao();
					result=bookDao.addbook(book);
				} else {
					result=-2;						//上传图片失败
				}
			} catch (Exception e) {
				e.printStackTrace();
				result=-3;							//更新失败
			}
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
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
