package com.gdsc.knu.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@SQLDelete(sql = "UPDATE ranking SET deleted = true WHERE id = ?")
@NoArgsConstructor
public class Ranking extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private int score;

    private String region;

    private boolean deleted = Boolean.FALSE;

    public Ranking(User user, int score, String region){
        this.user = user;
        this.score = score;
        this.region = region;
    }
}
