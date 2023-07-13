package com.sirmabc.bulkpayments.persistance.repositories;

import com.sirmabc.bulkpayments.persistance.entities.PropertiesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertiesRepository extends CrudRepository<PropertiesEntity, Integer> {

    PropertiesEntity findByName(String name);

}
