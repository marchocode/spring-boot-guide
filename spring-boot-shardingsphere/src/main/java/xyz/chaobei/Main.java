package xyz.chaobei;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.chaobei.entity.UserEntity;
import xyz.chaobei.repository.UserEntityRepository;

@SpringBootApplication
public class Main implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public void run(String... args) throws Exception {

        UserEntity u1 = new UserEntity();
        u1.setUsername("admin");
        u1.setPassword("123456");

        userEntityRepository.save(u1);

    }
}