package com.gdsc.knu.controller;

import com.gdsc.knu.dto.request.ExchangeRequestDto;
import com.gdsc.knu.entity.Exchange;
import com.gdsc.knu.entity.MarineLife;
import com.gdsc.knu.entity.MediaFile;
import com.gdsc.knu.repository.ExchangeRepository;
import com.gdsc.knu.repository.MarineLifeRepository;
import com.gdsc.knu.repository.MediaFileRepository;
import com.gdsc.knu.service.MediaFileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/exchanges")
public class ExchangeController {

    private final ExchangeRepository exchangeRepository;
    private final MarineLifeRepository marineLifeRepository;
    private final MediaFileService mediaFileService;
    private final MediaFileRepository mediaFileRepository;

    @Autowired
    public ExchangeController(ExchangeRepository exchangeRepository, MarineLifeRepository marineLifeRepository, MediaFileService mediaFileService, MediaFileRepository mediaFileRepository) {
        this.exchangeRepository = exchangeRepository;
        this.marineLifeRepository = marineLifeRepository;
        this.mediaFileService = mediaFileService;
        this.mediaFileRepository = mediaFileRepository;
    }

    // 교환 정보 생성
    @PostMapping
    @Operation(summary = "교환 신청", description = "교환 신청 기능")
    public ResponseEntity<Exchange> createExchange(@RequestBody ExchangeRequestDto exchangeRequestDto) {
        // imageId를 통해 MediaFile 객체를 찾습니다.
        Optional<MediaFile> mediaFileOptional = mediaFileRepository.findById(exchangeRequestDto.getImageId());
        if (!mediaFileOptional.isPresent()) {
            // 해당 imageId에 대한 MediaFile 객체가 없다면 적절한 예외 처리를 해야 합니다.
            throw new RuntimeException("해당 imageId에 대한 MediaFile 객체가 없습니다.");
        }

        // 찾아낸 MediaFile 객체의 정보를 바탕으로 새로운 MediaFile 객체를 생성합니다.
        MediaFile originalMediaFile = mediaFileOptional.get();
        MediaFile newMediaFile = new MediaFile(originalMediaFile.getFileName(), originalMediaFile.getFileType(), originalMediaFile.getUrl(), exchangeRequestDto.getSenderUserId(), originalMediaFile.getType());
        mediaFileRepository.save(newMediaFile);

        // imageId를 통해 MarineLife 객체를 찾습니다.
        Optional<MarineLife> marineLifeOptional = marineLifeRepository.findByImageId(exchangeRequestDto.getImageId());
        if (!marineLifeOptional.isPresent()) {
            // 해당 imageId에 대한 MarineLife 객체가 없다면 적절한 예외 처리를 해야 합니다.
            throw new RuntimeException("해당 imageId에 대한 MarineLife 객체가 없습니다.");
        }

    // 찾아낸 MarineLife 객체의 정보를 바탕으로 새로운 MarineLife 객체를 생성합니다.
        MarineLife originalMarineLife = marineLifeOptional.get();
        MarineLife newMarineLife = new MarineLife();

        newMarineLife.setUserId(exchangeRequestDto.getSenderUserId());
        newMarineLife.setName(originalMarineLife.getName());
        newMarineLife.setLatitude(originalMarineLife.getLatitude());
        newMarineLife.setLongitude(originalMarineLife.getLongitude());
        newMarineLife.setScore(originalMarineLife.getScore());
        newMarineLife.setClassName(originalMarineLife.getClassName());

    // 새롭게 생성한 MediaFile 객체의 id를 MarineLife 객체의 imageId로 설정합니다.
        newMarineLife.setImageId(0L);

    // MarineLife 객체를 저장합니다.
        marineLifeRepository.save(newMarineLife);


        Exchange exchange = new Exchange();
        exchange.setSenderUserId(exchangeRequestDto.getSenderUserId());
        exchange.setReceiverUserId(exchangeRequestDto.getReceiverUserId());
        // 새롭게 생성한 MediaFile 객체의 id를 exchange 객체에 설정합니다.
        exchange.setImageId(newMediaFile.getId());

        // exchangeApproval, exchangeRequestDatetime 설정
        // API를 호출한 시간을 설정
        exchange.setExchangeRequestDatetime(LocalDateTime.now());
        exchange.setExchangeId(0L);

        Exchange savedExchange = exchangeRepository.save(exchange);
        return ResponseEntity.ok(savedExchange);
    }

    // 특정 교환 정보 조회
    @GetMapping("/{exchangeID}")
    @Operation(summary = "교환 정보 조회", description = "교환 정보 확인 기능")
    public ResponseEntity<Exchange> getExchange(@PathVariable Integer exchangeID) {
        Optional<Exchange> exchange = exchangeRepository.findById(exchangeID);
        return exchange.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 교환 취소
    @DeleteMapping("/{exchangeID}")
    @Operation(summary = "교환 신청", description = "교환 신청 기능")
    public ResponseEntity<Void> deleteExchange(@PathVariable Integer exchangeID) {
        exchangeRepository.deleteById(exchangeID);
        return ResponseEntity.ok().build();
    }
}
