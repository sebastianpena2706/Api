package com.credibaco.assessment.card.dtos.card;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CardResponseDto<Response>{

    private String codigoRespuesta;
    private String numeroValidacion;
    private String mensaje;
    @JsonProperty("PAN")
    private String pan;
    private Response data;

    private String titular;
    private String cedula;
    private String telefono;
    private String estado;





}
