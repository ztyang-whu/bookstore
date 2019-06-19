package load;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

import bean.Book;
import bean.Users;

/**
 * 更新用户信息（上传图片）
 * 
 * @author yzt
 *
 */
public class Upload {
	public int upload(List<FileItem> items, Users user) {
		try {

			FileItem profile = null;
			for (FileItem item : items) {
				String fileName = item.getFieldName();
				if (item.isFormField()) {
					String value = item.getString("UTF8");
					switch (fileName) {
					case "username":
						user.setUsername(value);
						break;
					case "phonenumber":
						user.setPhonenumber(value);
						break;
					case "address":
						user.setAddress(value);
						break;
					}
				} else {
					profile = item;
				}
			}
			if (user.getUsername() == null || profile == null) {
				return -1; // 上传失败
			} else {
				String filePath = "/home/yzt";
				profile.write(new File(filePath, user.getUsername() + ".png"));
				user.setProfilePath(filePath + "/" + user.getUsername() + ".png");
				return 1; // 上传成功
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1; // 上传失败
	}

	/**
	 * 更新图书信息，上传图片
	 * 
	 * @param items
	 * @param book
	 * @return
	 */
	public int upload_book(List<FileItem> items, Book book) {
		try {
			FileItem book_photo = null;
			for (FileItem item : items) {
				String fileName = item.getFieldName();
				if (item.isFormField()) {
					String value = item.getString("UTF-8");
					switch (fileName) {
					case "bookname":
						book.setBook_name(value);
						break;
					case "author":
						book.setBook_author(value);
						break;
					case "bookstock":
						book.setBook_stock(Integer.parseInt(value));
						break;
					case "bookprice":
						book.setBook_price(value);
						break;
					}
				} else {
					book_photo = item;
				}
			}
			String filePath = "/home/yzt/Pictures";
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			String date = df.format(new Date());
			book_photo.write(new File(filePath, date + ".png"));
			book.setBook_photo_path(filePath + "/" + date + ".png");
			return 1; // 上传成功
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return -1; // 上传失败
	}
}
