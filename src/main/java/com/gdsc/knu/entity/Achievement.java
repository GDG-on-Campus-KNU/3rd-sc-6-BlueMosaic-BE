package com.gdsc.knu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "achievements")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer achievementId;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Float marineLifeCollectionRate = 0.0F;

    @Column(nullable = false)
    private Float garbageCollectionDegree;

    @Column
    private Boolean sproutCleaner011  = false;

    @Column
    private Boolean experiencedCleaner012 = false;

    @Column
    private Boolean skilledCleaner013 = false;

    @Column
    private Boolean noviceDiver021 = false;

    @Column
    private Boolean promisingDiver022 = false;

    @Column
    private Boolean experiencedDiver023 = false;

    @Column
    private Boolean skilledDiver024 = false;

    @Column
    private Boolean dolphin025 = false;

    @Column
    private Boolean novicePhotographer031 = false;

    @Column
    private Boolean experiencedPhotographer032 = false;

    @Column
    private Boolean popularPhotographer033 = false;

    @Column
    private Boolean paparazzi034 = false;

    @Column
    private Boolean pioneer04 = false;

    // Getters and Setters
    public Integer getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Integer achievementId) {
        this.achievementId = achievementId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    public Boolean getSproutCleaner011() {
        return sproutCleaner011;
    }

    public void setSproutCleaner011(Boolean sproutCleaner011) {
        this.sproutCleaner011 = sproutCleaner011;
    }

    public Boolean getExperiencedCleaner012() {
        return experiencedCleaner012;
    }

    public void setExperiencedCleaner012(Boolean experiencedCleaner012) {
        this.experiencedCleaner012 = experiencedCleaner012;
    }

    public Boolean getSkilledCleaner013() {
        return skilledCleaner013;
    }

    public void setSkilledCleaner013(Boolean skilledCleaner013) {
        this.skilledCleaner013 = skilledCleaner013;
    }

    public Boolean getNoviceDiver021() {
        return noviceDiver021;
    }

    public void setNoviceDiver021(Boolean noviceDiver021) {
        this.noviceDiver021 = noviceDiver021;
    }

    public Boolean getPromisingDiver022() {
        return promisingDiver022;
    }

    public void setPromisingDiver022(Boolean promisingDiver022) {
        this.promisingDiver022 = promisingDiver022;
    }

    public Boolean getExperiencedDiver023() {
        return experiencedDiver023;
    }

    public void setExperiencedDiver023(Boolean experiencedDiver023) {
        this.experiencedDiver023 = experiencedDiver023;
    }

    public Boolean getSkilledDiver024() {
        return skilledDiver024;
    }

    public void setSkilledDiver024(Boolean skilledDiver024) {
        this.skilledDiver024 = skilledDiver024;
    }

    public Boolean getDolphin025() {
        return dolphin025;
    }

    public void setDolphin025(Boolean dolphin025) {
        this.dolphin025 = dolphin025;
    }

    public Boolean getNovicePhotographer031() {
        return novicePhotographer031;
    }

    public void setNovicePhotographer031(Boolean novicePhotographer031) {
        this.novicePhotographer031 = novicePhotographer031;
    }

    public Boolean getExperiencedPhotographer032() {
        return experiencedPhotographer032;
    }

    public void setExperiencedPhotographer032(Boolean experiencedPhotographer032) {
        this.experiencedPhotographer032 = experiencedPhotographer032;
    }

    public Boolean getPopularPhotographer033() {
        return popularPhotographer033;
    }

    public void setPopularPhotographer033(Boolean popularPhotographer033) {
        this.popularPhotographer033 = popularPhotographer033;
    }

    public Boolean getPaparazzi034() {
        return paparazzi034;
    }

    public void setPaparazzi034(Boolean paparazzi034) {
        this.paparazzi034 = paparazzi034;
    }

    public Boolean getPioneer04() {
        return pioneer04;
    }

    public void setPioneer04(Boolean pioneer04) {
        this.pioneer04 = pioneer04;
    }
}
