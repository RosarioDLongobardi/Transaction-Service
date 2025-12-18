package com.fintech.transaction_ingestion_service.mapper;

import com.fintech.transaction_ingestion_service.dto.request.EasternBankRequest;
import com.fintech.transaction_ingestion_service.model.FinancialTransaction;
import org.bson.types.Decimal128;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EasternBankMapper {

    @Mapping(source = "txRef", target = "transactionId")
    @Mapping(source ="accountMasked", target = "accountIban", qualifiedByName = "lookupIban")
    @Mapping(source = "euroCents", target = "amount", qualifiedByName = "convertCentsToEuros")
    @Mapping(source = "timestampEpoch", target ="transactionDate", qualifiedByName = "parseEpochTimestamp")
    @Mapping(source = "catNum", target = "category", qualifiedByName = "lookupCat")
    @Mapping(source = "merchantCode", target = "merchantInfo.internalCode")
    @Mapping(target = "merchantInfo.taxId", ignore = true)
    @Mapping(target = "flags", ignore = true)
    @Mapping(target = "computed", ignore = true)
    @Mapping(target = "metadata", ignore = true)
    FinancialTransaction toEntity(EasternBankRequest request);



    @Named("lookupIban")
    default String lookupIban(String masked) {
        return EasternBankMapperHelper.IBAN_LOOKUP.getOrDefault(masked, masked);
    }

    @Named("convertCentsToEuros")
    default Decimal128 convertCentsToEuros(String euroCents) {
        if (euroCents == null || euroCents.isBlank()) {
            return new Decimal128(BigDecimal.ZERO);
        }


        BigDecimal cents = new BigDecimal(euroCents);
        BigDecimal euros = cents.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        return new Decimal128(euros);
    }

    @Named("parseEpochTimestamp")
    default Instant parseEpochTimestamp(String timestampEpoch) {
        if (timestampEpoch == null || timestampEpoch.isBlank()) {
            return Instant.now(); // Fallback al momento corrente
        }

        try {
            long epochSeconds = Long.parseLong(timestampEpoch);
            return Instant.ofEpochSecond(epochSeconds);
        } catch (NumberFormatException e) {
            return Instant.now();
        }
    }

    @Named("lookupCat")
    default String lookupCat(String categ) {
        return EasternBankMapperHelper.CATEGORY_MAP.getOrDefault(categ, categ);
    }

    @AfterMapping
    default void parseFlags(@MappingTarget FinancialTransaction transaction,
                            EasternBankRequest request) {
        String[] flags = request.getFlags().split(",");

        boolean isDebit = false;
        boolean isVerified = false;
        boolean isForeign = false;
        boolean isRecurring = false;

        for (String flag : flags) {
            flag = flag.trim();
            switch (flag) {
                case "D": isDebit = true; break;
                case "C": isDebit = false; break;
                case "V": isVerified = true; break;
                case "F": isForeign = true; break;
                case "R": isRecurring = true; break;
            }
        }

        transaction.setFlags(FinancialTransaction.Flags.builder()
                .isDebit(isDebit)
                .isVerified(isVerified)
                .isForeign(isForeign)
                .isRecurring(isRecurring)
                .needsReview(false)
                .crossBorder(false)
                .build());
    }
}
