package com.credibaco.assessment.card.dtos.transaction;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TransactionDto {

    private Long id;

    private String numeroReferencia;

    private Integer totalCompra;

    private String direccionCompra;

    private Date fechaTransaccion;

}
