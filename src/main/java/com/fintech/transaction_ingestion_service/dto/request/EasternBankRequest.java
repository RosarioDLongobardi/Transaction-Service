package com.fintech.transaction_ingestion_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EasternBankRequest {
    private String txRef;
    private String accountMasked;
    private String euroCents;
    private String timestampEpoch;
    private String catNum;
    private String merchantCode;
    private String flags;
}

