package com.gdsc.knu.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Exchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exchangeId;  // 변경된 부분

    @Column(nullable = false)
    private Long senderUserId;  // 변경된 부분

    @Column(nullable = false)
    private Long receiverUserId;  // 변경된 부분

    @Column(nullable = false)
    private Long marineLifeId;  // 변경된 부분

    @Column(name = "image_id")
    private Long imageId;

    @Column()
    private Long exchangeApproval;
    // 0 : 거래 신청, 1 : 거래 대기, 2: 거래 완료

    @Column(nullable = false)
    private LocalDateTime exchangeRequestDatetime;

    // 기본 생성자
    public Exchange() {}

    // 모든 필드를 파라미터로 받는 생성자
    public Exchange(Long senderUserId, Long receiverUserId, Long marineLifeId, Long exchangeApproval, LocalDateTime exchangeRequestDatetime) {
        this.senderUserId = senderUserId;  // 변경된 부분
        this.receiverUserId = receiverUserId;  // 변경된 부분
        this.marineLifeId = marineLifeId;  // 변경된 부분
        this.exchangeApproval = exchangeApproval;
        this.exchangeRequestDatetime = exchangeRequestDatetime;
    }

    // getter
    public Long getExchangeId() {  // 변경된 부분
        return exchangeId;
    }

    public Long getSenderUserId() {  // 변경된 부분
        return senderUserId;
    }

    public Long getReceiverUserId() {  // 변경된 부분
        return receiverUserId;
    }

    public Long getMarineLifeId() {  // 변경된 부분
        return marineLifeId;
    }

    public Long getExchangeApproval() {
        return exchangeApproval;
    }

    public LocalDateTime getExchangeRequestDatetime() {
        return exchangeRequestDatetime;
    }

    // setter 메소드들
    public void setExchangeId(Long exchangeId) {  // 변경된 부분
        this.exchangeId = exchangeId;
    }

    public void setSenderUserId(Long senderUserId) {  // 변경된 부분
        this.senderUserId = senderUserId;
    }

    public void setReceiverUserId(Long receiverUserId) {  // 변경된 부분
        this.receiverUserId = receiverUserId;
    }

    public void setMarineLifeId(Long marineLifeId) {  // 변경된 부분
        this.marineLifeId = marineLifeId;
    }

    public void setExchangeApproval(Long exchangeApproval) {
        this.exchangeApproval = exchangeApproval;
    }

    public void setExchangeRequestDatetime(LocalDateTime exchangeRequestDatetime) {
        this.exchangeRequestDatetime = exchangeRequestDatetime;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }
}

