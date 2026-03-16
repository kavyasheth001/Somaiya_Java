package com.classconnect.dto;

import com.classconnect.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    
    @NotBlank
    @Size(max = 100)
    private String name;
    
    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    @Size(min = 6)
    private String password;
    
    private User.Role role;
    
    @Size(max = 100)
    private String department;
    
    public RegisterRequest() {}
    
    public RegisterRequest(String name, String email, String password, User.Role role, String department) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.department = department;
    }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public User.Role getRole() { return role; }
    public void setRole(User.Role role) { this.role = role; }
    
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}

