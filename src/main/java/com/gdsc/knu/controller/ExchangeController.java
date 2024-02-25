package com.gdsc.knu.controller;

import com.gdsc.knu.entity.Exchange;
import com.gdsc.knu.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/exchanges")
public class ExchangeController {

    private final ExchangeRepository exchangeRepository;

    @Autowired
    public ExchangeController(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    // 교환 정보 생성
    @PostMapping
    public ResponseEntity<Exchange> createExchange(@RequestBody Exchange exchange) {
        Exchange savedExchange = exchangeRepository.save(exchange);
        return ResponseEntity.ok(savedExchange);
    }

    // 특정 교환 정보 조회
    @GetMapping("/{exchangeID}")
    public ResponseEntity<Exchange> getExchange(@PathVariable Integer exchangeID) {
        Optional<Exchange> exchange = exchangeRepository.findById(exchangeID);
        return exchange.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 교환 취소
    @DeleteMapping("/{exchangeID}")
    public ResponseEntity<Void> deleteExchange(@PathVariable Integer exchangeID) {
        exchangeRepository.deleteById(exchangeID);
        return ResponseEntity.ok().build();
    }
}
