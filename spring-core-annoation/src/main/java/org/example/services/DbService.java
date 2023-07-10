package org.example.services;

import org.example.dao.SomeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbService {

    private final SomeDao someDao;

    public DbService(SomeDao someDao) {
        this.someDao = someDao;
    }

    public void select() {
        System.out.println("DbService.select");
        someDao.select();
    }

}
