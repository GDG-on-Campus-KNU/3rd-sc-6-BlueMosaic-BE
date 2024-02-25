package com.gdsc.knu.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exchangeID;

    @Column(nullable = false)
    private Integer senderUserID;

    @Column(nullable = false)
    private Integer receiverUserID;

    @Column(nullable = false)
    private Integer marineLifeID;

    @Column()
    private Integer exchangeApproval;
    // 0 : 거래 신청, 1 : 거래 대기, 2: 거래 완료

    @Column(nullable = false)
    private LocalDateTime exchangeRequestDatetime;

    // 기본 생성자
    public Exchange() {}

    // 모든 필드를 파라미터로 받는 생성자
    public Exchange(Integer senderUserID, Integer receiverUserID, Integer marineLifeID, Integer exchangeApproval, LocalDateTime exchangeRequestDatetime) {
        this.senderUserID = senderUserID;
        this.receiverUserID = receiverUserID;
        this.marineLifeID = marineLifeID;
        this.exchangeApproval = exchangeApproval;
        this.exchangeRequestDatetime = exchangeRequestDatetime;
    }

    // getter
    public Integer getExchangeID() {
        return exchangeID;
    }

    public Integer getSenderUserID() {
        return senderUserID;
    }

    public Integer getReceiverUserID() {
        return receiverUserID;
    }

    public Integer getMarineLifeID() {
        return marineLifeID;
    }

    public Integer getExchangeApproval() {
        return exchangeApproval;
    }

    public LocalDateTime getExchangeRequestDatetime() {
        return exchangeRequestDatetime;
    }

    // setter 메소드들
    public void setExchangeID(Integer exchangeID) {
        this.exchangeID = exchangeID;
    }

    public void setSenderUserID(Integer senderUserID) {
        this.senderUserID = senderUserID;
    }

    public void setReceiverUserID(Integer receiverUserID) {
        this.receiverUserID = receiverUserID;
    }

    public void setMarineLifeID(Integer marineLifeID) {
        this.marineLifeID = marineLifeID;
    }

    public void setExchangeApproval(Integer exchangeApproval) {
        this.exchangeApproval = exchangeApproval;
    }

    public void setExchangeRequestDatetime(LocalDateTime exchangeRequestDatetime) {
        this.exchangeRequestDatetime = exchangeRequestDatetime;
    }

}
