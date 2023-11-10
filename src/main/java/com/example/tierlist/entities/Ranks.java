package com.example.tierlist.entities;

import jakarta.persistence.*;

@Entity
public class Ranks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String label;

    private Integer rankIndex;

    @ManyToOne
    private List list;

    @ManyToMany
    private java.util.List<Item> listOfItem;

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

    public Integer getRankIndex() {
        return rankIndex;
    }

    public void setRankIndex(Integer rankIndex) {
        this.rankIndex = rankIndex;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public java.util.List<Item> getListOfItem() {
        return listOfItem;
    }

    public void setListOfItem(java.util.List<Item> listOfItem) {
        this.listOfItem = listOfItem;
    }

    @Override
    public String toString() {
        return "Ranks{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", rankIndex=" + rankIndex +
                ", list=" + list +
                ", listOfItem=" + listOfItem +
                '}';
    }
}
