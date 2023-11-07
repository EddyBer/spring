package entities;

import jakarta.persistence.*;

@Entity
public class Ranks {

    @Id
    @GeneratedValue
    private Integer Id;

    private String Label;

    private Integer Index;


    @ManyToOne @JoinColumn(name = "list_id")
    private List list;

    @OneToMany (mappedBy = "id", cascade = CascadeType.ALL)
    private java.util.List<Item> ListOfItem;
}
