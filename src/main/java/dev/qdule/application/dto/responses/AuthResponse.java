package dev.qdule.application.dto.responses;

public class AuthResponse {
    private Long id;
    private String email;
    private String password;

    public AuthResponse(String name) {
        this.id = id;
        this.email = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
