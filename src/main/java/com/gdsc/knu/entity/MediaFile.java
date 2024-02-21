package com.gdsc.knu.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

@Entity
@SQLDelete(sql = "UPDATE media_file SET deleted = true WHERE id = ?")
@NoArgsConstructor
@Getter
public class MediaFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String fileName;
    private String fileType;
    private String url; // 파일 저장 위치
    private Long score; // 파일의 점수
    public MediaFile(String fileName, String fileType, String url, Long userId, Long score) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.url = url;
        this.userId = userId;
        this.score = score;
    }
}
