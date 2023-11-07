package entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String Username;

    private Date Birthdate;

    private String Email;

    private Integer IsAdmin;

    private String Password;

    private String Login;

    private String Name;

    private String Firstname;

    @OneToMany(mappedBy = "Id", cascade = CascadeType.ALL)
    private List<entities.List> ListOfList;

    @OneToMany(mappedBy = "Id")
    private  List<Item> ListOfItem;



}
