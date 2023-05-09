package com.credibaco.assessment.card.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString

@Entity
@Table(name="CARD")
/**
 * Representacion tabla base datos
 */
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "NUMERO_VALIDACION")
    private String numeroValidacion;
    @Column(name ="ESTADO")
    private String estado ;

    @Column(name = "PAN")

    private String pan;
    @Column(name = "TITULAR")
    private String titular;
    @Column(name = "CEDULA")
    @Size(min = 10, max = 15)
    private String cedula;
    @Column(name = "TIPO")
    private String tipo;
    @Column(name = "TELEFONO")
    @Size(min = 10, max = 10)
    private String telefono;
}
