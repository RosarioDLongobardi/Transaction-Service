package com.fintech.transaction_ingestion_service.dto.request;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "Transaction", namespace = "http://scu.bank/2024")
@XmlAccessorType(XmlAccessType.FIELD)
public class SouthernCreditRequest {

    @XmlElement(name = "Header", namespace = "http://scu.bank/2024")
    private Header header;

    @XmlElement(name = "Details", namespace = "http://scu.bank/2024")
    private Details details;

    @XmlElement(name = "Counterparty", namespace = "http://scu.bank/2024")
    private Counterparty counterparty;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Header {
        @XmlElement(name = "Id", namespace = "http://scu.bank/2024")
        private String id;

        @XmlElement(name = "ProcessingDate", namespace = "http://scu.bank/2024")
        private String processingDate;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Details {
        @XmlElement(name = "AccountNumber", namespace = "http://scu.bank/2024")
        private String accountNumber;

        @XmlElement(name = "Amount", namespace = "http://scu.bank/2024")
        private Amount amount;

        @XmlElement(name = "CategoryCode", namespace = "http://scu.bank/2024")
        private String categoryCode;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Amount {
        @XmlAttribute(name = "currency")
        private String currency;

        @XmlAttribute(name = "sign")
        private String sign;

        @XmlValue
        private String value;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Counterparty {
        @XmlAttribute(name = "type")
        private String type;

        @XmlElement(name = "TaxId", namespace = "http://scu.bank/2024")
        private String taxId;
    }
}


