package com.msa.stock;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository repository;
    private final StockMapper mapper;

    public StockDTO createStock(StockPurchaseDTO dto) {
        Stock stock = repository.save(mapper.toPurchase(dto));
        return mapper.toDTO(stock);
    }

    @Transactional
    public StockDTO purchaseStock(StockPurchaseDTO dto) {
        Stock stock = repository.findByUseridForUpdate(dto.getUserid())
                .orElseThrow(() -> new IllegalArgumentException("Notfound Stock"));

        stock.setCnt(stock.getCnt() + dto.getCnt());
        stock.setPrice(stock.getPrice().add(dto.getPrice()));
        return mapper.toDTO(repository.save(stock));
    }

    public StockDTO getStock(Long userid) {
        return mapper.toDTO(repository.findByUserid(userid).orElseThrow());
    }
}
