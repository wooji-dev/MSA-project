package com.msa.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account-service", url = "${client.account}")
public interface AccountClient {
    @GetMapping("/internal/accounts/{userid}")
    AccountDTO getAccount(@PathVariable Long userid);

    @PostMapping("/internal/accounts")
    AccountDTO createAccount(@RequestBody AccountCreateDTO dto);
}
