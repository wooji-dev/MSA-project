package com.msa.stock;

import com.msa.stock.client.AccountClient;
import com.msa.stock.client.AddPointDTO;
import com.msa.stock.client.WithdrawalDTO;
import com.msa.stock.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository repository;
    private final StockMapper mapper;

    private final UserClient userClient;
    private final AccountClient accountClient;

    public StockDTO createStock(StockPurchaseDTO dto) {
        Stock stock = repository.save(mapper.toPurchase(dto));
        return mapper.toDTO(stock);
    }

    // @Transactional
    public StockDTO purchase(StockPurchaseDTO dto) {
        boolean didWithdrawal = false;
        boolean didPurchased = false;   // 실제 DB 저장 여부로 의미를 명확히

        Stock stock = repository.findByUserid(dto.getUserid())
                .orElseThrow(() -> new IllegalArgumentException("NotFound Stock"));
        BigDecimal amount = null;
        BigDecimal newPrice = null;

        try {
            amount = dto.getPrice().multiply(BigDecimal.valueOf(dto.getCnt()));
            accountClient.withdrawal(dto.getUserid(),
                    new WithdrawalDTO(amount, dto.getUserid(), dto.getAccountPasswd()));
            didWithdrawal = true;

            // 주식 체결
            stock.setCnt(stock.getCnt() + dto.getCnt());
            newPrice = dto.getPrice().multiply(BigDecimal.valueOf(dto.getCnt()));
            stock.setPrice(stock.getPrice().add(newPrice));
            StockDTO newer = mapper.toDTO(repository.save(stock));
            didPurchased = true;

            // 활동 포인트 지급
            userClient.addPoint(new AddPointDTO(dto.getUserid(), dto.getCnt()));

            return newer;
        } catch (Exception e) {
            // 성공한 단계를 역순으로 되돌린다

            // 1) 출금을 되돌림 (환불)
            if (didWithdrawal) {
                accountClient.deposit(dto.getUserid(), amount);
            }

            // 2) 주식 저장을 되돌림 (이전 값으로 복구 후 다시 저장)
            if (didPurchased) {
                stock.setCnt(stock.getCnt() - dto.getCnt());
                stock.setPrice(stock.getPrice().subtract(newPrice));
                repository.save(stock);
            }

            throw e;
        }
    }

    public StockDTO getStock(Long userid) {
        return mapper.toDTO(repository.findByUserid(userid).orElseThrow());
    }
}
