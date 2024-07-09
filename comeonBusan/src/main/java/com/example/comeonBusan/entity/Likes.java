package com.example.comeonBusan.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lno;

    @ManyToOne
    @JoinColumn(name = "tourlist_uc_seq", referencedColumnName = "uc_seq")
    @JsonBackReference
    private TourList tourlist;

    @ManyToOne
    @JoinColumn(name = "festival_uc_seq", referencedColumnName = "UC_SEQ")
    @JsonBackReference
    private Festival festival;

    @ManyToOne
    @JoinColumn(name = "lodgment_lid", referencedColumnName = "lid")
    @JsonBackReference
    private Lodgment lodgment;
    
    @ManyToOne
    @JoinColumn(name = "food_uc_seq", referencedColumnName = "UC_SEQ")
    @JsonBackReference
    private Food food;

    private Long likecount;
}
