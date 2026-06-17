package dev.qdule.application.dto.requests;

public class UserCreateRequest {

    public String name;
    public String password;
    // public List<String> roles;
    // public UserStatus status;
    public String email;

    public UserCreateRequest(String name, String password,
    //  List<String> roles, UserStatus status, 
     String email) {
        this.name = name;
        this.password = password;
        // this.roles = roles;
        // this.status = status;
        this.email = email;
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

    // public List<String> getRoles() {
    //     return roles;
    // }

    // public void setRoles(List<String> roles) {
    //     this.roles = roles;
    // }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // public UserStatus getStatus() {
    //     return status;
    // }

    // public void setStatus(UserStatus status) {
    //     this.status = status;
    // }
}
