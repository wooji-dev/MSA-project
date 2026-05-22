package com.msa.stock;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockPurchaseDTO {
    @Positive
    private Integer cnt;

    @Positive
    private BigDecimal price;

    @NotNull
    private Long userid;

    @Size(min = 6, max = 6, message = "비밀번호는 6자리입니다!")
    @Pattern(regexp = "^\\d{6}$", message = "비밀번호는 6자리 숫자만 가능합니다!")
    private String passwd;
}
