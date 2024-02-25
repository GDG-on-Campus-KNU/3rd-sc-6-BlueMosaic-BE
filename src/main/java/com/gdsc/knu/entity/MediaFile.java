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
    private String type;

    public MediaFile(String fileName, String fileType, String url, Long userId, String type) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.url = url;
        this.userId = userId;
        this.type = type;
    }
}
