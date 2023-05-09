package com.credibaco.assessment.card.service;

import com.credibaco.assessment.card.dtos.transaction.TransactionResponseDto;

public interface TransactionService {


    TransactionResponseDto createTransation (String pan, String numeroReferencia, Integer totalCompra, String direccionCompra);
    TransactionResponseDto anularTransation (String pan, String numeroReferencia, Integer totalCompra);
}
