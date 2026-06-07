package dev.qdule.domain.model;

import java.util.List;

public class User {
    private Long id;
    private String name;
    private String password;
    private List<String> roles;
    private UserStatus status;

    public User(String name, String password, List<String> roles, UserStatus status) {
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.status = status;
    }

    public User(Long id, String name, String password, List<String> roles, UserStatus status) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.status = status;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
