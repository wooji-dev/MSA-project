package com.msa.stock.client;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${client.user}")
public interface UserClient {
    @PatchMapping("/internal/users/{userid}")
    UserDTO addPoint(@PathVariable Long userid, @RequestBody Integer stocks);
}
