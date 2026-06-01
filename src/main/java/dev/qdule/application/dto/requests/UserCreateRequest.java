package dev.qdule.application.dto.requests;

import java.util.List;

import dev.qdule.domain.model.UserStatus;

public class UserCreateRequest {
    public String name;
    public String password;
    public List<String> roles;
    public UserStatus status;

    public UserCreateRequest(String name, String password, List<String> roles, UserStatus status) {
        this.name = name;
        this.password = password;
        this.roles = roles;
        this.status = status;
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
