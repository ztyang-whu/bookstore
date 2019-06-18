package servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * Servlet implementation class JsonTest
 */
@WebServlet("/updateinfo")
public class UpdateInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateInfo() {
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
		
		Boolean isMultipart=ServletFileUpload.isMultipartContent(request);
		if(!isMultipart) {
			return;
		}
		
		try {
			FileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(factory);
			List<FileItem> items=upload.parseRequest(request);
			String username=null;
			FileItem profile=null;
			for(FileItem item:items) {
				String fileName=item.getFieldName();
				if(item.isFormField()) {
					String value=item.getString("GB2312");
					if(fileName.equals("username")) {
						username=value;
					}
				}else {
					profile=item;
				}
			}
			System.out.println(username);
			if(username==null||profile==null) {
				return;
			}else {
				profile.write(new File("/home/yzt", username+".png"));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
