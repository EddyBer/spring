package entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Integer Id;

    private String Label;

    private String Image;

    private String Tag;

    @OneToOne(mappedBy = "id")
    private User userId;

    @OneToMany(mappedBy = "id")
    private List<Ranks> ranksList;
}
