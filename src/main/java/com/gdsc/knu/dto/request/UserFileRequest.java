package com.gdsc.knu.dto.request;

public class UserFileRequest {
    private Long userId;
    private Long fileId;

    // getter
    public Long getUserId() {
        return this.userId;
    }

    public Long getFileId() {
        return this.fileId;
    }

    // setter
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }
}
