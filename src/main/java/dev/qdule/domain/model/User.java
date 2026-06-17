package dev.qdule.domain.model;

public class User {
    private Long id;
    public String name;
    public String email;
    public String password;
    // public List<String> roles; // Questionavel
    // public UserStatus status; // ?

    public User(String name, String password,
            // List<String> roles, UserStatus status,
            String email) {
        this.name = name;
        this.password = password;
        // this.roles = roles;
        // this.status = status;
        this.email = email;
    }

    public User(Long id, String name, String password,
            // List<String> roles, UserStatus status,
            String email) {
        this.id = id;
        this.name = name;
        this.password = password;
        // this.roles = roles;
        // this.status = status;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    // public List<String> getRoles() {
    // return roles;
    // }

    // public void setRoles(List<String> roles) {
    // this.roles = roles;
    // }

    // public UserStatus getStatus() {
    // return status;
    // }

    // public void setStatus(UserStatus status) {
    // this.status = status;
    // }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
