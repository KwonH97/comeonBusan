package com.example.comeonBusan.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Views {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vno;

    @ManyToOne
    @JoinColumn(name = "tourlist_uc_seq", referencedColumnName = "uc_seq")
    private TourList tourlist;

    @ManyToOne
    @JoinColumn(name = "festival_uc_seq", referencedColumnName = "UC_SEQ")
    private Festival festival;

    @ManyToOne
    @JoinColumn(name = "lodgment_lid", referencedColumnName = "lid")
    private Lodgment lodgment;

    @ManyToOne
    @JoinColumn(name = "food_uc_seq", referencedColumnName = "UC_SEQ")
    private Food food;
    
    private Long viewcount;
}
