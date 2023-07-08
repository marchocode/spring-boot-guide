package xyz.chaobei.mongo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import xyz.chaobei.mongo.entity.StudentEntity;
import xyz.chaobei.mongo.repository.StudentEntityRepository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
@EnableMongoRepositories
@Slf4j
public class MongoStarterApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MongoStarterApplication.class, args);
    }

    @Resource
    private StudentEntityRepository studentEntityRepository;


    @Override
    public void run(String... args) {

        // insert one student;
        StudentEntity student = new StudentEntity("Anna", 0);
        studentEntityRepository.insert(student);

        log.info("insert student after id={}", student.getId());

        // query by id
        Optional<StudentEntity> byId = studentEntityRepository.findById(student.getId());
        log.info("query student by id student={}", byId.get());


        // update
        student.setUpdateTime(new Date());
        student.setName("Mongo");

        studentEntityRepository.save(student);

        // delete
        studentEntityRepository.deleteById("6301c47bc038fc049f40cb1e");
    }
}
