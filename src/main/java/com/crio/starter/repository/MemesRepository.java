package com.crio.starter.repository;

import com.crio.starter.data.MemeEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemesRepository extends MongoRepository<MemeEntity, String> {

  List<MemeEntity> findFirst100ByOrderByIdDesc();

  Optional<MemeEntity> findById(String id);
}
