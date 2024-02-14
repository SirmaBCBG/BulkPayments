package com.sirmabc.bulkpayments.persistance.repositories;

import com.sirmabc.bulkpayments.persistance.entities.ParticipantsEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ParticipantsRepository extends CrudRepository<ParticipantsEntity, Integer> {

    ParticipantsEntity findByBic(String bic);

    @Procedure(procedureName = "ubxipbulk.CHECKPARTICIPANT", outputParameterName = "p_count")
    int checkParticipant(@Param("p_bic") String bic);

    @Procedure(procedureName = "ubxipbulk.archiveParticipants")
    @Modifying
    void archive();

}
