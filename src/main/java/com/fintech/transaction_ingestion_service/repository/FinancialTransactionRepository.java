package com.fintech.transaction_ingestion_service.repository;

import com.fintech.transaction_ingestion_service.model.FinancialTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FinancialTransactionRepository extends MongoRepository<FinancialTransaction, String> {
}
