package com.msa.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockService service;

    // 주식 구매
    @PatchMapping("{userid}")
    ResponseEntity<StockDTO> purchaseStock(@RequestBody StockPurchaseDTO dto){
        var purchase = service.purchaseStock(dto);
        return ResponseEntity.ok(purchase);
    }
}
