package com.credibaco.assessment.card.repository;

import com.credibaco.assessment.card.model.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository  extends JpaRepository<CardEntity,Long> {
    /**
     *Query para buscar una tarjeta por "pan"
     * @param pan numero de la tarjeta
     */
    @Query(value = "Select * from CARD  WHERE pan =:pan",nativeQuery = true)
    Optional<CardEntity> finByPan(@Param("pan") String pan);

}
