package com.gdsc.knu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "marinelife")
public class MarineLife {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer marineLifeId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "name", length = 30)
    private String name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    /*
    @Column(name = "first_founder_id")
    private Integer firstFounderId;
    */
    // 기본 생성자
    public MarineLife() {
    }

    // getter
    public Integer getMarineLifeId() {
        return marineLifeId;
    }

    public Integer getUserId() {
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
    public Integer getFirstFounderId() {
        return firstFounderId;
    }
     */

    // setter
    /*
    public void setMarineLifeId(Integer marineLifeId) {
        this.marineLifeId = marineLifeId;
    }
     */

    public void setUserId(Integer userId) {
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
    public void setFirstFounderId(Integer firstFounderID) {
        this.firstFounderId = firstFounderID;
    }
     */

}

