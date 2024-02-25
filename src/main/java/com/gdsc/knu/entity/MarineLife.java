package com.gdsc.knu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "marinelife")
@Getter
@Setter
@NoArgsConstructor
public class MarineLife extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long marineLifeId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "score")
    private int score;

    @Column(name = "class_name")
    private String className;

    @Column(name = "image_id")
    private Long imageId;
}

