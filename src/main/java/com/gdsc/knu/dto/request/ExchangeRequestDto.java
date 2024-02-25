package com.gdsc.knu.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRequestDto {
    private Long senderUserId;
    private Long receiverUserId;
    private Long imageId;

    public Long getSenderUserId() {
        return senderUserId;
    }

    public Long getReceiverUserId() {
        return receiverUserId;
    }

    public Long getImageId() {
        return imageId;
    }

}
