package xyz.chaobei.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.chaobei.entity.UserEntity;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {

}
