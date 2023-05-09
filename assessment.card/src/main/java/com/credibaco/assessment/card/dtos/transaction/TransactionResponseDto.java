package com.credibaco.assessment.card.dtos.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)

public class TransactionResponseDto{

    private String codigoRespuesta;
     private String mensaje;
    private String estado;
    private String numeroReferencia;






}
