package com.gdsc.knu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "achievements")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long achievementId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Float marineLifeCollectionRate = 0.0F;

    @Column(nullable = false)
    private Float garbageCollectionDegree;

    // New fields for achievements
    @Column
    private Boolean sproutCleaner  = false;

    @Column
    private Boolean experiencedCleaner = false;

    @Column
    private Boolean skilledCleaner = false;

    @Column
    private Boolean noviceDiver = false;

    @Column
    private Boolean promisingDiver = false;

    @Column
    private Boolean experiencedDiver = false;

    @Column
    private Boolean skilledDiver = false;

    @Column
    private Boolean dolphin = false;

    @Column
    private Boolean novicePhotographer = false;

    @Column
    private Boolean experiencedPhotographer = false;

    @Column
    private Boolean popularPhotographer = false;

    @Column
    private Boolean paparazzi = false;

    @Column
    private Boolean pioneer = false;

    // Getters and Setters
    public Long getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Long achievementId) {
        this.achievementId = achievementId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Float getMarineLifeCollectionRate() {
        return marineLifeCollectionRate;
    }

    public void setMarineLifeCollectionRate(Float marineLifeCollectionRate) {
        this.marineLifeCollectionRate = marineLifeCollectionRate;
    }

    public Float getGarbageCollectionDegree() {
        return garbageCollectionDegree;
    }

    public void setGarbageCollectionDegree(Float garbageCollectionDegree) {
        this.garbageCollectionDegree = garbageCollectionDegree;
    }

    public Boolean getSproutCleaner() {
        return sproutCleaner;
    }

    public void setSproutCleaner(Boolean sproutCleaner) {
        this.sproutCleaner = sproutCleaner;
    }

    public Boolean getExperiencedCleaner() {
        return experiencedCleaner;
    }

    public void setExperiencedCleaner(Boolean experiencedCleaner) {
        this.experiencedCleaner = experiencedCleaner;
    }

    public Boolean getSkilledCleaner() {
        return skilledCleaner;
    }

    public void setSkilledCleaner(Boolean skilledCleaner) {
        this.skilledCleaner = skilledCleaner;
    }

    public Boolean getNoviceDiver() {
        return noviceDiver;
    }

    public void setNoviceDiver(Boolean noviceDiver) {
        this.noviceDiver = noviceDiver;
    }

    public Boolean getPromisingDiver() {
        return promisingDiver;
    }

    public void setPromisingDiver(Boolean promisingDiver) {
        this.promisingDiver = promisingDiver;
    }

    public Boolean getExperiencedDiver() {
        return experiencedDiver;
    }

    public void setExperiencedDiver(Boolean experiencedDiver) {
        this.experiencedDiver = experiencedDiver;
    }

    public Boolean getSkilledDiver() {
        return skilledDiver;
    }

    public void setSkilledDiver(Boolean skilledDiver) {
        this.skilledDiver = skilledDiver;
    }

    public Boolean getDolphin() {
        return dolphin;
    }

    public void setDolphin(Boolean dolphin) {
        this.dolphin = dolphin;
    }

    public Boolean getNovicePhotographer() {
        return novicePhotographer;
    }

    public void setNovicePhotographer(Boolean novicePhotographer) {
        this.novicePhotographer = novicePhotographer;
    }

    public Boolean getExperiencedPhotographer() {
        return experiencedPhotographer;
    }

    public void setExperiencedPhotographer(Boolean experiencedPhotographer) {
        this.experiencedPhotographer = experiencedPhotographer;
    }

    public Boolean getPopularPhotographer() {
        return popularPhotographer;
    }

    public void setPopularPhotographer(Boolean popularPhotographer) {
        this.popularPhotographer = popularPhotographer;
    }

    public Boolean getPaparazzi() {
        return paparazzi;
    }

    public void setPaparazzi(Boolean paparazzi) {
        this.paparazzi = paparazzi;
    }

    public Boolean getPioneer() {
        return pioneer;
    }

    public void setPioneer(Boolean pioneer) {
        this.pioneer = pioneer;
    }

}
