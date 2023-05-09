package com.credibaco.assessment.card.service.impl;

import com.credibaco.assessment.card.dtos.transaction.TransactionResponseDto;
import com.credibaco.assessment.card.model.CardEntity;
import com.credibaco.assessment.card.model.TransactionEntity;
import com.credibaco.assessment.card.repository.CardRepository;
import com.credibaco.assessment.card.repository.TransactionRepository;
import com.credibaco.assessment.card.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {


    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CardRepository cardRepository;

    @Override
    public TransactionResponseDto createTransation(String pan, String numeroReferencia, Integer totalCompra, String direccionCompra) {
        Optional<CardEntity> findCardByPan = cardRepository.finByPan(pan);
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();
        Optional<TransactionEntity> findNumberReferenci = transactionRepository.finByNumberReferenci(numeroReferencia);
        if (findNumberReferenci.get().getCardEntity().equals(findCardByPan.get().getId())) {

            transactionResponseDto.setMensaje("Numero Referencia Duplicado");
            transactionResponseDto.setEstado("Rechazada");
            transactionResponseDto.setNumeroReferencia(numeroReferencia);
        } else {


            if (findCardByPan.isPresent()) {
                if (findCardByPan.get().getEstado().contains("Enrolada")) {

                    TransactionEntity transactionEntity = TransactionEntity.builder()
                            .numeroReferencia(numeroReferencia)
                            .totalCompra(totalCompra)
                            .direccionCompra(direccionCompra)
                            .cardEntity(findCardByPan.get())
                            .fechaTransaccion(new Date())
                            .build();
                    TransactionEntity entity = transactionRepository.save(transactionEntity);
                    transactionResponseDto.setCodigoRespuesta("00");
                    transactionResponseDto.setMensaje("Compra exitosa");
                    transactionResponseDto.setEstado("Aprobada");
                    transactionResponseDto.setNumeroReferencia(numeroReferencia);

                } else {
                    transactionResponseDto.setCodigoRespuesta("02");
                    transactionResponseDto.setMensaje("Tarjeta no enrolada");
                    transactionResponseDto.setEstado("Rechazada");
                    transactionResponseDto.setNumeroReferencia(numeroReferencia);

                }
            } else {
                transactionResponseDto.setCodigoRespuesta("01");
                transactionResponseDto.setMensaje("Tarjeta no existe");
                transactionResponseDto.setEstado("Rechazada");
                transactionResponseDto.setNumeroReferencia(numeroReferencia);

            }
        }
        return transactionResponseDto;
    }

    @Override
    public TransactionResponseDto anularTransation(String pan, String numeroReferencia, Integer totalCompra) {
        Optional<CardEntity> findByPan = cardRepository.finByPan(pan);
        Optional<TransactionEntity> findTransaction = transactionRepository.finByNumberReferenci(numeroReferencia);
        TransactionResponseDto transactionResponseDto = new TransactionResponseDto();

        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, -5);

        if (findTransaction.isPresent()) {

            if (findTransaction.get().getNumeroReferencia().equals(numeroReferencia)) {
                if (findTransaction.get().getFechaTransaccion().after(now.getTime())) {

                    transactionRepository.deleteById(findTransaction.get().getId());
                    transactionResponseDto.setCodigoRespuesta("00");
                    transactionResponseDto.setMensaje("Compra anulada");
                    transactionResponseDto.setNumeroReferencia(numeroReferencia);
                } else {
                    transactionResponseDto.setCodigoRespuesta("02");
                    transactionResponseDto.setMensaje("No se puede anular transacción  supera el tiempo");
                    transactionResponseDto.setNumeroReferencia(numeroReferencia);
                }
            } else {
                transactionResponseDto.setCodigoRespuesta("01");
                transactionResponseDto.setMensaje("Numero de Referencia Invalido ");
                transactionResponseDto.setNumeroReferencia(numeroReferencia);
            }
        } else {
            transactionResponseDto.setCodigoRespuesta("00");
            transactionResponseDto.setMensaje("Compra anulada no coincide pan con transaccion");
        }


        return transactionResponseDto;
    }


}
