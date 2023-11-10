package com.example.tierlist.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private Date birthdate;

    private String email;

    private Integer isAdmin;

    private String password;

    private String login;

    private String name;

    private String firstname;

    @OneToMany
    private List<com.example.tierlist.entities.List> listOfList;

    @OneToMany
    private  List<Item> listOfItem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public List<com.example.tierlist.entities.List> getListOfList() {
        return listOfList;
    }

    public void setListOfList(List<com.example.tierlist.entities.List> listOfList) {
        this.listOfList = listOfList;
    }

    public List<Item> getListOfItem() {
        return listOfItem;
    }

    public void setListOfItem(List<Item> listOfItem) {
        this.listOfItem = listOfItem;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthdate=" + birthdate +
                ", email='" + email + '\'' +
                ", isAdmin=" + isAdmin +
                ", password='" + password + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                ", listOfList=" + listOfList +
                ", listOfItem=" + listOfItem +
                '}';
    }
}
