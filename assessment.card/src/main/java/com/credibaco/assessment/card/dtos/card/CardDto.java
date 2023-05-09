package com.credibaco.assessment.card.dtos.card;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CardDto {

    private Long id;
    private String numeroValidacion;

    private String estado;
    private String pan;
    private String titular;
    private String cedula;
    private String tipo;
    private String telefono;


}
