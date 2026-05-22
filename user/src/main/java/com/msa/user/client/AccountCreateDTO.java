package com.msa.user.client;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AccountCreateDTO(
        String username,

        @Size(min = 6, max = 6, message = "계좌 비밀번호는 6자리입니다!")
        @Pattern(regexp = "^\\d{6}$", message = "계좌 비밀번호는 6자리 숫자만 가능합니다!")
        String passwd,

        Long userid
) {
}