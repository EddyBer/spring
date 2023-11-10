package com.example.tierlist.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String label;

    private String image;

    private String tag;

    @ManyToOne
    private User user;
    @ManyToMany
    private List<Ranks> ranksList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Ranks> getRanksList() {
        return ranksList;
    }

    public void setRanksList(List<Ranks> ranksList) {
        this.ranksList = ranksList;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", image='" + image + '\'' +
                ", tag='" + tag + '\'' +
                ", user=" + user +
                ", ranksList=" + ranksList +
                '}';
    }
}
