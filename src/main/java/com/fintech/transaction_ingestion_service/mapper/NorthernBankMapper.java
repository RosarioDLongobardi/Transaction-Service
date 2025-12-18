package com.fintech.transaction_ingestion_service.mapper;

import com.fintech.transaction_ingestion_service.dto.request.NorthernBankRequest;
import com.fintech.transaction_ingestion_service.model.FinancialTransaction;
import org.bson.types.Decimal128;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface NorthernBankMapper {

    @Mapping(source = "txnId", target = "transactionId")
    @Mapping(source = "account", target = "accountIban")
    @Mapping(source = "amount", target = "amount", qualifiedByName = "parseAmount")
    @Mapping(source = "date", target = "transactionDate", qualifiedByName = "parseCompactDate")
    @Mapping(source = "category", target = "category")
    @Mapping(source = "merchant", target = "merchantInfo")
    @Mapping(target = "flags", ignore = true)
    @Mapping(target = "computed", ignore = true)
    @Mapping(target = "metadata", ignore = true)
    FinancialTransaction toEntity(NorthernBankRequest request);

    @Mapping(source = "vat", target = "taxId")
    @Mapping(source = "id", target = "internalCode")
    FinancialTransaction.MerchantInfo toMerchantInfo(NorthernBankRequest.MerchantInfo merchant);

    @Named("parseAmount")
    default Decimal128 parseAmount(String amount) {
        if (amount == null || amount.isBlank()) {
            return new Decimal128(BigDecimal.ZERO);
        }
        return new Decimal128(new BigDecimal(amount));
    }

    @Named("parseCompactDate")
    default Instant parseCompactDate(String date) {
        if (date == null || date.isBlank()) {
            return Instant.now();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return dateTime.toInstant(ZoneOffset.UTC);
    }

}