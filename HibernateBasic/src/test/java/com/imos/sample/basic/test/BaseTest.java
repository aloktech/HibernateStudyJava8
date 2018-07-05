/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.basic.test;

import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.base.HibernateService;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author ameher
 */
public class BaseTest {
    
    @BeforeAll
    public static void setUp() throws RepositoryException {
        try {
            HibernateService.INSTANCE.config("test.hibernate.cfg.xml");
        } catch (RepositoryException ex) {
            throw  ex;
        }
    }
    
    @AfterAll
    public static void shutDown() throws RepositoryException {
        try {
            HibernateService.INSTANCE.shutDown();
        } catch (RepositoryException ex) {
            throw  ex;
        }
    }
    
    @Test
    public void hibernateConnectionCheck() throws RepositoryException {
        try {
            Session session = HibernateService.INSTANCE.openConnection();
            HibernateService.INSTANCE.closeConnection(session);
        } catch (RepositoryException ex) {
            throw  ex;
        }
    }
}
