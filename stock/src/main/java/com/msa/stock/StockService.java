package com.msa.stock;

import com.msa.stock.client.AccountClient;
import com.msa.stock.client.AccountWithdrawalDTO;
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
    public StockDTO purchaseStock(StockPurchaseDTO dto) {
        boolean didWithdrawal = false;
        boolean didSaveStock = false;   // 실제 DB 저장 여부로 의미를 명확히

        Long userid = dto.getUserid();
        Stock stock = repository.findByUserid(userid)
                .orElseThrow(() -> new IllegalArgumentException("Notfound Stock"));

        // 보상에 필요한 '이전 상태' 백업
        Integer prevCnt = stock.getCnt();
        BigDecimal prevPrice = stock.getPrice();

        AccountWithdrawalDTO withdrawalDTO = new AccountWithdrawalDTO(dto.getPrice(), userid, dto.getPasswd());

        try {
            accountClient.withdrawal(withdrawalDTO, userid);
            didWithdrawal = true;

            stock.setCnt(stock.getCnt() + dto.getCnt());
            stock.setPrice(stock.getPrice().add(dto.getPrice()));
            repository.save(stock);
            didSaveStock = true;

            userClient.addPoint(userid, dto.getCnt());

            return mapper.toDTO(stock);

        } catch (Exception e) {
            // 성공한 단계를 역순으로 되돌린다

            // 2) 주식 저장을 되돌림 (이전 값으로 복구 후 다시 저장)
            if (didSaveStock) {
                stock.setCnt(prevCnt);
                stock.setPrice(prevPrice);
                repository.save(stock);
            }

            // 1) 출금을 되돌림 (환불)
            if (didWithdrawal) {
                accountClient.deposit(userid, dto.getPrice());
            }

            throw e;
        }
    }

    public StockDTO getStock(Long userid) {
        return mapper.toDTO(repository.findByUserid(userid).orElseThrow());
    }
}
