package com.fintech.transaction_ingestion_service.parser;

import com.fintech.transaction_ingestion_service.model.FinancialTransaction;



public interface TransactionParserStrategy<T> {
    FinancialTransaction parse(T request) throws Exception;
    String getSourceName();
    String getParserVersion();
}
