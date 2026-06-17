package dev.qdule.infra.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    public String name;

    @Column(name = "password", nullable = false, length = 255)
    public String password;

    // @ElementCollection(fetch = FetchType.EAGER)
    // @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    // @Column(name = "role", length = 50)
    // public List<String> roles;

    // @Enumerated(EnumType.STRING)
    // public UserStatus status;

    @Column(name = "email", nullable = false, length = 255)
    public String email;

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
