package com.sirmabc.bulkpayments.persistance.repositories;

import com.sirmabc.bulkpayments.persistance.entities.ParticipantsEntity;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantsRepository extends CrudRepository<ParticipantsEntity, Integer> {

    public ParticipantsEntity findByBic (String bic);

    @Procedure(procedureName = "ubxip.CHECKPARTICIPANT", outputParameterName = "p_count")
    int checkParticipant(@Param("p_bic") String bic);

    @Procedure("ubxip.archiveParticipant")
    void archive();

}
