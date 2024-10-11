package sg.nus.iss.shoppingCart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Integer id;
@Column(name="adminname")
private String name;
@Column(name="password")
private String password;
@Column(name="email")
private String email;
@Column(name="contact_number")
private String contactNumber;
public Admin() {}
public Admin(String name,String password,String email,String contactNumber) {
	this.name = name;
	this.password = password;
	this.email = email;
	this.contactNumber = contactNumber;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getContactNumber() {
	return contactNumber;
}
public void setContactNumber(String contactNumber) {
	this.contactNumber = contactNumber;
}
}
