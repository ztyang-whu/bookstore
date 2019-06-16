package servlet;

public class Users {
	private int id;
	private String username;
	private String password;
	private String phonenumber;
	private String address;
	private String profilePath;
	
	public Users() {}
	
	
	public Users(int _id, String _username, String _password, String _phonenumber) {
		this.id=_id;
		this.username=_username;
		this.password=_password;
		this.phonenumber=_phonenumber;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int _id) {
		this.id=_id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String _username) {
		this.username=_username;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String _password) {
		this.password=_password;
	}
	
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String _phonenumber) {
		this.phonenumber=_phonenumber;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String _address) {
		this.address=_address;
	}
	
	public String getProfilePath() {
		return profilePath;
	}
	public void setProfilePath(String _profilePath) {
		this.profilePath=_profilePath;
	}
}
