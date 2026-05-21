package com.msa.stock;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMapper {
    StockDTO toDTO(Stock stock);
    Stock toEntity(StockDTO stockDTO);
    Stock toPurchase(StockPurchaseDTO dto);
}
