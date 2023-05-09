package com.credibaco.assessment.card.service.impl;

import com.credibaco.assessment.card.dtos.card.CardDto;
import com.credibaco.assessment.card.dtos.card.CardResponseDto;
import com.credibaco.assessment.card.model.CardEntity;
import com.credibaco.assessment.card.model.TransactionEntity;
import com.credibaco.assessment.card.repository.CardRepository;
import com.credibaco.assessment.card.repository.TransactionRepository;
import com.credibaco.assessment.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    TransactionRepository transactionRepository;


    @Override
    public CardResponseDto createCard(String pan, String titular, String cedula, String tipo, String telefono) {

        CardResponseDto responseDto = new CardResponseDto();
        Optional<CardEntity> cardByPan = cardRepository.finByPan(pan);

        if (cardByPan.isEmpty()) {

            String numeroValidacion = String.valueOf((int) (Math.random() * 100) + 1);
            CardEntity carToCreate = CardEntity.builder()
                    .pan(pan)
                    .estado("Creada")
                    .telefono(telefono)
                    .titular(titular)
                    .cedula(cedula)
                    .tipo(tipo)
                    .numeroValidacion(numeroValidacion)
                    .build();
            CardEntity cardTosave = cardRepository.save(carToCreate);
            cardTosave.setId(cardTosave.getId());

            responseDto.setNumeroValidacion(cardTosave.getNumeroValidacion());
            responseDto.setPan(cardTosave.getPan().substring(0, 6) + "****" + cardTosave.getPan().substring(cardTosave.getPan().length() - 4));
            responseDto.setMensaje("Éxito");
            responseDto.setCodigoRespuesta("00");

        } else {
            responseDto.setMensaje("Fallido");
            responseDto.setCodigoRespuesta("01");


        }
        return responseDto;
    }

    @Override
    public CardResponseDto findAll() {
        List<CardEntity> cardEntities = cardRepository.findAll();
        List<CardDto> cardDtoList = new ArrayList<>();

        for (CardEntity entity : cardEntities) {
            cardDtoList.add(CardDto.builder()
                    .cedula(entity.getCedula())
                    .titular(entity.getTitular())
                    .tipo(entity.getTipo())
                    .telefono(entity.getTelefono())
                    .id(entity.getId())
                    .estado(entity.getEstado())
                    .numeroValidacion(entity.getNumeroValidacion())
                    .pan(entity.getPan())
                    .build());
        }
        CardResponseDto cardDto = new CardResponseDto();
        cardDto.setData(cardDtoList);

        return cardDto;
    }

    @Override //crear validacion si ya esta enrolada
    public CardResponseDto enrolarCard(String pan, String numeroValidacion) {

        Optional<CardEntity> cardByPan = cardRepository.finByPan(pan);
        CardResponseDto responseDto = new CardResponseDto();

        if (cardByPan.isEmpty()) {
            responseDto.setMensaje("Tarjeta No Exitste");
            responseDto.setCodigoRespuesta("01");

        } else if

        (cardByPan.get().getNumeroValidacion().equals(numeroValidacion)) {
            CardEntity cardEntity = cardByPan.get();
            cardEntity.setEstado("Enrolada");
            cardEntity.setPan(cardByPan.get().getPan());
            cardEntity.setId(cardByPan.get().getId());
            cardEntity.setCedula(cardByPan.get().getCedula());
            cardEntity.setTipo(cardByPan.get().getTipo());
            cardEntity.setTitular(cardByPan.get().getTitular());
            cardEntity.setTelefono(cardByPan.get().getTelefono());
            cardEntity.setNumeroValidacion(cardByPan.get().getNumeroValidacion());

            cardRepository.save(cardEntity);

            responseDto.setCodigoRespuesta("00");
            responseDto.setMensaje("Exito");
            responseDto.setPan(cardEntity.getPan().substring(0, 6) + "****" + cardEntity.getPan().substring(cardEntity.getPan().length() - 4));


        } else {
            responseDto.setMensaje("Número de validación inválido");
            responseDto.setCodigoRespuesta("02");

        }

        return responseDto;
    }

    @Override
    public CardResponseDto findCard(String pan) {
        Optional<CardEntity> findByPan = cardRepository.finByPan(pan);
        CardResponseDto cardResponseDto = new CardResponseDto();
        if (findByPan.isPresent()) {
            CardDto cardDto = CardDto.builder()
                    .estado(findByPan.get().getEstado())
                    .cedula(findByPan.get().getCedula())
                    .titular(findByPan.get().getTitular())
                    .tipo(findByPan.get().getTipo())
                    .pan(findByPan.get().getPan())
                    .numeroValidacion(findByPan.get().getNumeroValidacion())
                    .id(findByPan.get().getId())
                    .telefono(findByPan.get().getTelefono())
                    .build();
            cardResponseDto.setCedula(cardDto.getCedula());
            cardResponseDto.setPan(cardDto.getPan().substring(0, 6) + "****" + cardDto.getPan().substring(cardDto.getPan().length() - 4));
            cardResponseDto.setTitular(cardDto.getTitular());
            cardResponseDto.setTelefono(cardDto.getTelefono());
            cardResponseDto.setEstado(findByPan.get().getEstado());
        }

        return cardResponseDto;
    }

    @Override
    public CardResponseDto deleteCard(String pan, String numeroValidacion) {
        Optional<CardEntity> findCard = cardRepository.finByPan(pan);
        Optional<TransactionEntity> findTransactionByIdCard = transactionRepository.findByCardEntity(findCard.get().getId());
        CardResponseDto cardResponseDto = new CardResponseDto();
        if (findCard.isPresent()) {
            if (findCard.get().getNumeroValidacion().equals(numeroValidacion)) {
                if (findTransactionByIdCard.isPresent()) {
                    transactionRepository.deleteByCardEntity(findCard.get().getId());
                } else {
                    cardRepository.deleteById(findCard.get().getId());
                    cardResponseDto.setCodigoRespuesta("00");
                    cardResponseDto.setMensaje("Se ha eliminado la tarjeta");
                }

            }
            cardResponseDto.setCodigoRespuesta("00");
            cardResponseDto.setMensaje("Se ha eliminado la tarjeta");

        } else {
            cardResponseDto.setCodigoRespuesta("01");
            cardResponseDto.setMensaje("No se ha eliminado la tarjeta");
        }

        return cardResponseDto;
    }


}
