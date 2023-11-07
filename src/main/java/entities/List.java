package entities;

import jakarta.persistence.*;

import java.util.Date;
@Entity
public class List {

    @Id
    @GeneratedValue
    private Integer Id;

    private String Label;

    private Integer Statut;

    private Date CreationDate;

    @ManyToOne @JoinColumn(name = "user_id")
    private User user;

    @OneToMany (mappedBy = "id", cascade = CascadeType.ALL)
    private java.util.List<Ranks> ListOfRanks;


}
