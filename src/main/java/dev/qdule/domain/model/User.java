package dev.qdule.domain.model;

import java.util.List;

public class User {
    private Long id;
    public String name;
    public String password;
    public List<String> roles;
    public UserStatus status;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
