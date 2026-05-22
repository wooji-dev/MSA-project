package com.msa.stock.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "account-service", url = "${client.account}")
public interface AccountClient {
    @PutMapping("/internal/accounts/{userid}")
    AccountDTO deposit(@PathVariable Long userid, @RequestParam("amount") BigDecimal amount);

    @PatchMapping("/internal/accounts/{userid}")
    AccountDTO withdrawal(@PathVariable Long userid, @RequestBody WithdrawalDTO dto);
}
