package sg.nus.iss.shoppingCart.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// SignUp is the model used to store SignUp data
public class SignUp {

	@NotBlank(message="Email is required")
	@Email(message="Must have a valid email")
	public String email;
	
	@NotBlank(message="Name is required")
	@Size(min=2,max=30,message="Name must be between 2-30 characters")
	public String username;
	
	@Pattern(regexp="(|[0-9]{8,})",
			message="Either leave Contact Number blank or input a proper contact number (at least 8 digits).")
	public String contactNumber;
	
	@NotBlank(message = "Password is required")
	@Size(min = 8, message = "Password must be at least 8 characters long")
	public String password1;
	
	@NotBlank(message = "Confirm password is required")
	public String password2;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
	
}
