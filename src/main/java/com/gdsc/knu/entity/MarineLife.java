package com.gdsc.knu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "marinelife")
public class MarineLife {

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
/*
    @Column(name = "first_founder_id")
    private Long firstFounderId;
*/

    //score
    @Column(name = "score")
    private int score;

    // 기본 생성자
    public MarineLife() {
    }

    // getter
    public Long getMarineLifeId() {
        return marineLifeId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
    /*
    public Long getFirstFounderId() {
        return firstFounderId;
    }
    */
    public int getScore() {
        return score;
    }
    // setter
    public void setMarineLifeId(Long marineLifeId) {
        this.marineLifeId = marineLifeId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    /*
    public void setFirstFounderId(Long firstFounderID) {
        this.firstFounderId = firstFounderID;
    }
     */
    public void setScore(int score) {
        this.score = score;
    }
}

