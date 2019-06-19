package bean;

public class Book {
	private int book_id;
	private String book_name;
	private String book_author;
	private String book_photo_path;
	private int book_stock;
	private String book_price;
	
	public Book() {};
	
	public int getBook_id() {
		return this.book_id;
	}
	public void setBook_id(int book_name) {
		this.book_id=book_name;
	}
	
	public String getBook_name() {
		return this.book_name;
	}
	public void setBook_name(String book_name) {
		this.book_name=book_name;
	}
	public String getBook_author() {
		return this.book_author;
	}
	public void setBook_author(String book_author) {
		this.book_author=book_author;
	}
	public String book_photo_path() {
		return this.book_photo_path;
	}
	public void setBook_photo_path(String book_photo_path) {
		this.book_photo_path=book_photo_path;
	}
	public int getBook_stock() {
		return this.book_stock;
	}
	public void setBook_stock(int book_stock) {
		this.book_stock=book_stock;
	}
	public String getBook_price() {
		return this.book_price;
	}
	public void setBook_price(String book_price) {
		this.book_price=book_price;
	}
}
