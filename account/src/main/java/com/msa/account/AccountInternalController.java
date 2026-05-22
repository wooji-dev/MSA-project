package com.msa.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/internal/accounts")
@RequiredArgsConstructor
public class AccountInternalController {
    private final AccountService service;

    @PostMapping
    ResponseEntity<AccountDTO> createAccount(@RequestBody AccountCreateDTO dto) {
        var account = service.createAccount(dto);
        return ResponseEntity.ok(account);
    }

    @GetMapping("{userid}")
    ResponseEntity<AccountDTO> getAccount(@PathVariable Long userid) {
        var account = service.getAccountInfo(userid);
        return ResponseEntity.ok(account);
    }

}