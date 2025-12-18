package com.fintech.transaction_ingestion_service.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.Decimal128;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Data
@Builder
public class FinancialTransaction {

    @Id
    private String id;

    private String transactionId;
    private String accountIban;
    private Decimal128 amount;
    private Instant transactionDate;

    private String category;

    private MerchantInfo merchantInfo;

    private Flags flags;

    private Computed computed;

    private Metadata metadata;

    @Data
    @Builder
    public static class MerchantInfo {
        private String taxId;
        private String internalCode;
    }

    @Data
    @Builder
    public static class Flags {
        private boolean isDebit;
        private boolean isVerified;
        private boolean isForeign;
        private boolean isRecurring;
        private boolean needsReview;
        private boolean crossBorder;
    }

    @Data
    @Builder
    public static class Computed {
        private int riskScore;
        private String monthlyCategory;
        private boolean isAnomaly;
        private Instant processingTime;
    }

    @Data
    @Builder
    public static class Metadata {
        private String source;
        private String parserVersion;
        private String rawDataHash;
    }
}
