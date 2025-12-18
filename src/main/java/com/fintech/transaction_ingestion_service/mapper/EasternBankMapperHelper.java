package com.fintech.transaction_ingestion_service.mapper;

import org.springframework.stereotype.Component;


    @Component
    class EasternBankMapperHelper {

        public static final java.util.Map<String, String> IBAN_LOOKUP =
                java.util.Map.of(
                        "****3456", "IT60X0542811101000000123456",
                        "****7890", "IT28W8000000292100645211157890",
                        "****1111", "IT40S0542811101000000001111"
                );

        public static final java.util.Map<String, String> CATEGORY_MAP =
                java.util.Map.of(
                        "17", "FOOD",
                        "22", "TRANSPORT",
                        "31", "SHOPPING",
                        "45", "HEALTH",
                        "52", "ENTERTAINMENT"
                );
    }

