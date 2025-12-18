package com.fintech.transaction_ingestion_service.mapper;

import com.fintech.transaction_ingestion_service.dto.request.SouthernCreditRequest;
import com.fintech.transaction_ingestion_service.model.FinancialTransaction;
import org.bson.types.Decimal128;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SouthernCreditMapper {

    @Mapping(source = "header.id", target = "transactionId")
    @Mapping(source = "header.processingDate", target = "transactionDate", qualifiedByName = "parseIsoDate")
    @Mapping(source = "details.accountNumber", target = "accountIban", qualifiedByName = "decodeBase64Iban")
    @Mapping(source = "details.amount", target = "amount", qualifiedByName = "parseSignedAmount")
    @Mapping(source = "details.categoryCode", target = "category")
    @Mapping(source = "counterparty.taxId", target = "merchantInfo.taxId")
    @Mapping(target = "merchantInfo.internalCode", ignore = true)
    @Mapping(target = "flags", ignore = true)
    @Mapping(target = "computed", ignore = true)
    @Mapping(target = "metadata", ignore = true)
    FinancialTransaction toEntity(SouthernCreditRequest request);


    @Named("decodeBase64Iban")
    default String decodeBase64Iban(String encoded) {
        try {
            return new String(Base64.getDecoder().decode(encoded));
        } catch (Exception e) {
            throw new RuntimeException("Failed to decode IBAN: " + encoded, e);
        }
    }

    @Named("parseIsoDate")
    default Date parseIsoDate(String isoDate) {
        return Date.from(Instant.parse(isoDate));
    }

    @Named("parseSignedAmount")
    default Decimal128 parseSignedAmount(SouthernCreditRequest.Amount amount) {
        double value = Double.parseDouble(amount.getValue());

        if ("-".equals(amount.getSign())) {
            value = -value;
        }

        return new Decimal128(BigDecimal.valueOf(value));
    }
}