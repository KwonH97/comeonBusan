package com.example.comeonBusan.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @ToString.Exclude
    private Festival festival;
    
    @ManyToOne
    @JoinColumn(name = "food_uc_seq", referencedColumnName = "UC_SEQ")
    @JsonBackReference
    private Food food;

    private Long likecount;
}
