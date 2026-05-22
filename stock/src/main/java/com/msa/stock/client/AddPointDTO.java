package com.msa.stock.client;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddPointDTO {
    private Long userid;
    private Integer cnt;

}
