package com.example.comeonBusan.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(callSuper = true)
public class Views extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vno;

    @ManyToOne
    @JoinColumn(name = "tourlist_uc_seq", referencedColumnName = "uc_seq")
    @JsonBackReference
    @ToString.Exclude
    private TourList tourlist;

    @ManyToOne
    @JoinColumn(name = "festival_uc_seq", referencedColumnName = "uc_seq")
    @JsonBackReference
    @ToString.Exclude
    private Festival festival;

    @ManyToOne
    @JoinColumn(name = "food_uc_seq", referencedColumnName = "uc_seq")
    @JsonBackReference
    @ToString.Exclude
    private Food food;
    
    private Long viewcount;
}
