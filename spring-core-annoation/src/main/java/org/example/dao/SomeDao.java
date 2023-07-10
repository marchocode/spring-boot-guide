package org.example.dao;

import org.springframework.stereotype.Repository;

@Repository
public class SomeDao {

    public void select() {
        System.out.println("SomeDao.select");
    }

}
