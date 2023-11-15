package com.example.tierlist.entities;

import jakarta.persistence.*;

import java.util.Date;
@Entity
public class List {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String label;

    private String tag;

    private Integer statut;

    private Date creationDate;

    @ManyToOne
    private User user;

    @OneToMany
    private java.util.List<Ranks> listOfRanks;

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

    public Integer getStatut() {
        return statut;
    }

    public void setStatut(Integer statut) {
        this.statut = statut;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public java.util.List<Ranks> getListOfRanks() {
        return listOfRanks;
    }

    public void setListOfRanks(java.util.List<Ranks> listOfRanks) {
        this.listOfRanks = listOfRanks;
    }

    @Override
    public String toString() {
        return "List{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", statut=" + statut +
                ", creationDate=" + creationDate +
                ", user=" + user +
                ", listOfRanks=" + listOfRanks +
                '}';
    }
}
