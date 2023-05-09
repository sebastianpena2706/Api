package com.credibaco.assessment.card.service;

import com.credibaco.assessment.card.dtos.card.CardResponseDto;

public interface CardService {


    CardResponseDto createCard(String pan,String titular,String cedula, String tipo, String telefono);
    CardResponseDto findAll();
    CardResponseDto enrolarCard(String pan,String numeroValidacion);
    CardResponseDto findCard(String pan);
    CardResponseDto deleteCard(String pan,String numeroValidacion);

}
