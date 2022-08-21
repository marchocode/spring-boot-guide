package xyz.chaobei.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import xyz.chaobei.mongo.entity.StudentEntity;

public interface StudentEntityRepository extends MongoRepository<StudentEntity, String> {

}
