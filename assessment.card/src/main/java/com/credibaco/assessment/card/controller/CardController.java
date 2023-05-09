package com.credibaco.assessment.card.controller;

import com.credibaco.assessment.card.dtos.card.CardDto;
import com.credibaco.assessment.card.dtos.card.CardResponseDto;
import com.credibaco.assessment.card.dtos.transaction.TransactionResponseDto;
import com.credibaco.assessment.card.service.CardService;
import com.credibaco.assessment.card.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardService cardService;

    @Autowired
    TransactionService transactionService;

    /**
     *Metodo buscar todas las tarjetas
     * se coloca provicional ya que retorna el numero de la tarjeta visible
     *
     */
    @GetMapping("/")
    public ResponseEntity<CardResponseDto<List<CardDto>>>findAll(){

        CardResponseDto<List<CardDto>>findCard= cardService.findAll();

        return new ResponseEntity<>(findCard,HttpStatus.OK);
    }

    /**
     *Metodo enrolar, su funcion es activiar una tarjeta "pan" con el numero de validacion que se genero
     * al momento de crear la tarjeta, con el fin de poder crear transacciones
     * @param pan numero de tarjeta
     * @param numerValidacion  numero autogenerado al momento de crear tarjeta
     */
    @PutMapping("/pan/{pan}/numeroValidacion/{numeroValidacion}")
    public ResponseEntity<CardResponseDto<CardDto>>enrolarCard(@PathVariable("pan") String pan, @PathVariable("numeroValidacion") String numerValidacion){

        CardResponseDto <CardDto>cardResponseDto=cardService.enrolarCard(pan,numerValidacion);
        return new ResponseEntity<>(cardResponseDto,HttpStatus.OK);
    }


    /**
     *Metodo creacion de tarjeta
     * @param pan = numero de tarjeta con un minimo de 16 caracteres y un maximo de 19 "obligatorio"
     * @param cedula = cedula del titular, con una cantidad fija de 10 caracteres "obligatorio"
     * @param tipo =  solo acepta dos tipos "Crédito" "Débito"  se debe escribir exactamente
     * @param telefono = con una cantidad fija de 10 caracteres "obligatorio"
     */
    @PostMapping("/pan/{pan}/titular/{titular}/cedula/{cedula}/tipo/{tipo}/telefono/{telefono}")
    public ResponseEntity<CardResponseDto>createCard( @Size(min = 16, max = 19) @PathVariable("pan") String pan,
                                                      @PathVariable("titular") String titular,
                                                      @Size(min = 10, max = 10) @PathVariable("cedula") String cedula,
                                                      @Pattern(regexp = "^(Crédito|Débito)$") @PathVariable("tipo") String tipo,
                                                      @Size(min = 10, max = 10) @PathVariable("telefono") String telefono){
        CardResponseDto cardById= cardService.createCard(pan, titular, cedula, tipo, telefono);
        return new ResponseEntity<>(cardById, HttpStatus.CREATED);
    }

    /**
     *Metodo buscar  tarjeta
     * @param pan busca tarjeta por su numero
     */

    @GetMapping("/pan/{pan}")
    public ResponseEntity<CardResponseDto<CardDto>>findCard(@PathVariable("pan") String pan){

        CardResponseDto<CardDto> cardResponseDto= cardService.findCard(pan);
        return new ResponseEntity<>(cardResponseDto,HttpStatus.OK);
    }

    /**
     *Metodo eliminar tarjeta
     * elimina tarjeta, pero si una tarjeta esta asociada a una transaccion, primero elimina la transaccion
     * @param pan numero de la tarjeta a eliminar
     * @param numeroValidacion numero que se autogenero al crear tarjeta
     *
     */
    @Transactional
    @DeleteMapping("/pan/{pan}/numeroValidacion/{numeroValidacion}")
    public ResponseEntity<CardResponseDto<CardDto>>deleteCard(@PathVariable("pan") String pan,@PathVariable("numeroValidacion") String numeroValidacion){

        CardResponseDto<CardDto>cardResponseDto= cardService.deleteCard(pan,numeroValidacion);
        return new ResponseEntity<>(cardResponseDto,HttpStatus.OK);
    }

    /**
     *Metodo crear transaccion
     * se crea una transaccion  si la tarjeta se encuentra "enrolada"
     * y no existen 2 transacciones iguales
     * @param pan numero de tarjeta
     * @param numeroReferencia numero creado al momento de enrolar tarjeta
     * @param totalCompra total de compra
     * @param direccionCompra direccion de compra
     *
     */

    @PostMapping("/pan/{pan}/numeroReferencia/{numeroReferencia}/totalCompra/{totalCompra}/direccionCompra/{direccionCompra}")
    public ResponseEntity<TransactionResponseDto>createTransation(@PathVariable("pan") String pan, @PathVariable("numeroReferencia")String numeroReferencia,@PathVariable("totalCompra") Integer totalCompra,@PathVariable("direccionCompra") String direccionCompra){

        TransactionResponseDto transactionResponseDto= transactionService.createTransation( pan, numeroReferencia, totalCompra, direccionCompra);
        return new ResponseEntity<>(transactionResponseDto,HttpStatus.CREATED);
    }
    /**
     *Metodo anular transaccion
     * permite eliminar una transaccion si se hizo hace menos de 5 minutos
     *
     */
    @Transactional
    @DeleteMapping("/pan/{pan}/numeroReferencia/{numeroReferencia}/totalCompra/{totalCompra}")
    public ResponseEntity<TransactionResponseDto>anularTransation(@PathVariable("pan") String pan, @PathVariable("numeroReferencia")String numeroReferencia,@PathVariable("totalCompra") Integer totalCompra){

        TransactionResponseDto transactionResponseDto= transactionService.anularTransation(pan, numeroReferencia, totalCompra);
        return new ResponseEntity<>(transactionResponseDto,HttpStatus.OK);
    }

}
