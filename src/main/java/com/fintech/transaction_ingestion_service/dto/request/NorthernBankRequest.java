package com.fintech.transaction_ingestion_service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NorthernBankRequest {

    @JsonProperty("txn_id")
    @NotBlank
    private String txnId;

    @JsonProperty("acc")
    @NotBlank
    private String account;

    @JsonProperty("amt")
    @NotBlank
    private String amount;

    @JsonProperty("dt")
    @NotBlank
    private String date;

    @JsonProperty("cat")
    private String category;

    @JsonProperty("merchant")
    private MerchantInfo merchant;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MerchantInfo {
        @JsonProperty("id")
        private String id;

        @JsonProperty("vat")
        private String vat;
    }
}