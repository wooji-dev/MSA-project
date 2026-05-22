package com.msa.user;

import com.msa.user.client.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    private final Client client;
    private final AccountClient accountClient;
    private final StockClient stockClient;

    @Transactional
    public UserDTO regist(UserRegistDTO dto) {
        User user = mapper.toEntity(dto);
        user.setPasswd(passwordEncoder.encode(dto.getPasswd()));
        user.addRole(UserRole.ROLE_USER);
        User newer = repository.save(user);
        Long userid = newer.getId();

        accountClient.createAccount(new AccountCreateDTO(newer.getName(), dto.getAccountPasswd(), userid));
        stockClient.createStock(new StockDTO(1, BigDecimal.valueOf(1000), userid));

        return mapper.toDTO(newer);
    }


    public UserDTO getUser(Long id) {
        User user = repository.findById(id).orElseThrow();
        UserDTO dto = mapper.toDTO(user);
        dto.setPasswd("");
        dto.setAccount(client.getAccountInfo(id));
        dto.setStock(client.getStockInfo(id));
        return mapper.toDTO(user);
    }
}
