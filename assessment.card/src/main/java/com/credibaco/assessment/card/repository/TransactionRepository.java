package com.credibaco.assessment.card.repository;

import com.credibaco.assessment.card.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {
    /**
     * Query para buscar transaccion por id de tarjeta relacionada a la transaccion
     * @param cardId id de la tarjeta relacionada con la transaccion
     */
    @Query(value = "Select * from TRANSACTION  WHERE card_entity_id =:cardId",nativeQuery = true)
    Optional<TransactionEntity> findByCardEntity(@Param("cardId") long cardId);

    /**
     *Query para buscar transaccion por numero de referencia
     * @param numeroReferencia numero de referencia de la transaccion
     */
    @Query(value = "Select * from TRANSACTION  WHERE numero_referncia =:numeroReferencia",nativeQuery = true)
    Optional<TransactionEntity> finByNumberReferenci(@Param("numeroReferencia") String numeroReferencia);

    /**
     * Query para eliminar transaccion relacionada a la id de una tarjeta
     * @param cardId id de tarjeta relacionada con la transaccion
     */
    @Modifying
    @Query(value = "DELETE FROM transaction WHERE card_entity_id = :cardId", nativeQuery = true)
    void deleteByCardEntity(@Param("cardId") Long cardId);

}
