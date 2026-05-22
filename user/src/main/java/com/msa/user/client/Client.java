package com.msa.user.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class Client {
    private final RestClient accountClient;
    private final RestClient stockClient;

    public Client(@Value("${client.account}") String accountUri, @Value("${client.stock}") String stockUri) {
        System.out.println("accountUrl = " + accountUri);
        this.accountClient = RestClient.builder().baseUrl(accountUri).build();
        this.stockClient = RestClient.builder().baseUrl(stockUri).build();
    }

    public AccountDTO getAccountInfo(Long userid) {
        try {
            AccountDTO account = this.accountClient.get()
                    .uri("/internal/accounts/{userid}", userid)
                    .retrieve().body(AccountDTO.class);
            return account;
        } catch (Exception e) {
            return null;
        }

    }

    public StockDTO getStockInfo(Long userid) {
        try {
            StockDTO stock = this.stockClient.get()
                    .uri("/internal/stocks/{userid}", userid)
                    .retrieve().body(StockDTO.class);
            return stock;
        } catch (Exception e) {
            return null;
        }

    }

}

