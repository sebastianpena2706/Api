package com.credibaco.assessment.card.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

@Entity
@Table(name = "TRANSACTION")
/**
 * Representacion tabla base datos
 */
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private CardEntity cardEntity;

    @Column(name = "NUMERO_REFERNCIA")
    private String numeroReferencia;
    @Column(name = "TOTAL_COMPRA")
    private Integer totalCompra;
    @Column(name = "DIRECCION_COMPRA")
    private String direccionCompra;
    @Column(name = "FECHA_TRANSACCION")
    private Date fechaTransaccion;

}
