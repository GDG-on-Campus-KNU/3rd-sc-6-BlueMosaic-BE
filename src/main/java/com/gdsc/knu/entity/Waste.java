package com.gdsc.knu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "wastes")
public class Waste {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer wasteId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer plastic;

    @Column(nullable = false)
    private Integer styrofoam;

    @Column(nullable = false)
    private Integer fiber; // 섬유형 (어업용 밧줄)

    @Column(nullable = false)
    private Integer vinyl;

    @Column(nullable = false)
    private Integer generalWaste; // 일반쓰레기(기타)

    //@Column
    //private Boolean upcyclingPossible; // 업사이클링 가능 여부

    // Getters and Setters

    public Integer getWasteId() {
        return wasteId;
    }

    public void setWasteId(Integer wasteId) {
        this.wasteId = wasteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPlastic() {
        return plastic;
    }

    public void setPlastic(Integer plastic) {
        this.plastic = plastic;
    }

    public Integer getStyrofoam() {
        return styrofoam;
    }

    public void setStyrofoam(Integer styrofoam) {
        this.styrofoam = styrofoam;
    }

    public Integer getFiber() {
        return fiber;
    }

    public void setFiber(Integer fiber) {
        this.fiber = fiber;
    }

    public Integer getVinyl() {
        return vinyl;
    }

    public void setVinyl(Integer vinyl) {
        this.vinyl = vinyl;
    }

    public Integer getGeneralWaste() {
        return generalWaste;
    }

    public void setGeneralWaste(Integer generalWaste) {
        this.generalWaste = generalWaste;
    }

    /*public Boolean getUpcyclingPossible() {
        return upcyclingPossible;
    }

    public void setUpcyclingPossible(Boolean upcyclingPossible) {
        this.upcyclingPossible = upcyclingPossible;
    }
    */

}
