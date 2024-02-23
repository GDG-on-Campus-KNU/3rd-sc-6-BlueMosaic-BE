package com.gdsc.knu.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exchangeId;

    @Column(nullable = false)
    private Integer sender_userId;

    @Column(nullable = false)
    private Integer receiver_userId;

    @Column(nullable = false)
    private Integer marineLifeId;

    @Column(nullable = false)
    private Integer exchangeApproval;

    @Column(nullable = false)
    private LocalDateTime exchangeRequestDateTime;

    // Getter & Setter
    public Integer getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Integer exchangeId) {
        this.exchangeId = exchangeId;
    }

    public Integer getSender_userId() {
        return sender_userId;
    }

    public void setSender_userId(Integer sender_userId) {
        this.sender_userId = sender_userId;
    }

    public Integer getReceiver_userId() {
        return receiver_userId;
    }

    public void setReceiver_userId(Integer receiver_userId) {
        this.receiver_userId = receiver_userId;
    }

    public Integer getMarineLifeId() {
        return marineLifeId;
    }

    public void setMarineLifeId(Integer marineLifeId) {
        this.marineLifeId = marineLifeId;
    }

    public Integer getExchangeApproval() {
        return exchangeApproval;
    }

    public void setExchangeApproval(Integer exchangeApproval) {
        this.exchangeApproval = exchangeApproval;
    }

    public LocalDateTime getExchangeRequestDateTime() {
        return exchangeRequestDateTime;
    }

    public void setExchangeRequestDateTime(LocalDateTime exchangeRequestDateTime) {
        this.exchangeRequestDateTime = exchangeRequestDateTime;
    }
}

