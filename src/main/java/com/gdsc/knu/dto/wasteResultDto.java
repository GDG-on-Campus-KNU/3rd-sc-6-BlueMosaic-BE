package com.gdsc.knu.dto;

public class wasteResultDto {
    private String fileType;
    private Long score;
    private int totalWaste;

    // 기본 생성자
    public wasteResultDto() {
    }

    // 모든 필드를 파라미터로 받는 생성자
    public wasteResultDto(String fileType, Long score, int totalWaste) {
        this.fileType = fileType;
        this.score = score;
        this.totalWaste = totalWaste;
    }

    // Getter 메소드
    public String getFileType() {
        return fileType;
    }

    public Long getScore() {
        return score;
    }

    public int getTotalWaste() {
        return totalWaste;
    }

    // Setter 메소드
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public void setTotalWaste(int totalWaste) {
        this.totalWaste = totalWaste;
    }
}
