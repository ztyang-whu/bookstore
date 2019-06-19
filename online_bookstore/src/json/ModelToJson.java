package json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import bean.Book;

public class ModelToJson {
	public String bookToJson(ArrayList<Book> books) throws Exception {
		String result=null;
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		for(Book book:books) {
			JSONObject jsonobj = new JSONObject(book);
			jsonArray.put(jsonobj);
		}
		jsonObject.put("books", jsonArray);
		result=jsonObject.toString();
		return result;
	}
}
