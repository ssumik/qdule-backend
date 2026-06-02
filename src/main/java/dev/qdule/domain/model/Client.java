package dev.qdule.domain.model;

public class Client {
    private Long id;
    private String name;
    private String email;
    private String cellPhone;
    private User user;

    public Client(Long id, String name, String email, String cellPhone, User user) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cellPhone = cellPhone;
        this.user = user;
    }

    public Client(String name, String email, String cellPhone, User user) {
        this.name = name;
        this.email = email;
        this.cellPhone = cellPhone;
        this.user = user;
    }

    public Client(String name, String email, String cellPhone) {
        this.name = name;
        this.email = email;
        this.cellPhone = cellPhone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
