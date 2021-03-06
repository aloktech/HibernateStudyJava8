/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.cache.test;

import com.imos.sample.base.HibernateService;
import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.cache.model.Person;
import com.imos.sample.cache.repo.PersonRepository;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author ameher
 */
@Log4j2
public class HazelcastCacheTest {

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
    public void personAdd() throws RepositoryException {
        Person person = new Person();
        person.setFirstName("Alok");

        PersonRepository repo = new PersonRepository(true);
        try {
            List<Person> persons = repo.findAllPersons();
            assertTrue(persons.isEmpty());
        } catch (RepositoryException ex) {
            throw  ex;
        }
        try {
            repo.addData(person);
        } catch (RepositoryException ex) {
            throw  ex;
        }
        try {
            List<Person> persons = repo.findAllPersons();
            assertFalse(persons.isEmpty());

            person = persons.get(0);

            assertEquals("Alok", person.getFirstName());
            assertNotNull(person.getId());

            person.setFirstName("Pintu");

            repo.updateData(person);

            persons = repo.findAllPersons();
            assertFalse(persons.isEmpty());

            person = persons.get(0);

            assertEquals("Pintu", person.getFirstName());
            assertNotNull(person.getId());

            repo.deleteData(person);

            persons = repo.findAllPersons();
            assertTrue(persons.isEmpty());
        } catch (RepositoryException ex) {
            throw  ex;
        }
    }

    @Test
    public void multiplePersonFind() throws RepositoryException {
        Person person = new Person();
        person.setFirstName("Alok");

        PersonRepository repo = new PersonRepository(true);
        try {
            List<Person> persons = repo.findAllPersons();
            assertTrue(persons.isEmpty());
        } catch (RepositoryException ex) {
            throw  ex;
        }
        try {
            repo.addData(person);
        } catch (RepositoryException ex) {
            throw  ex;
        }
        try {
            List<Person> persons;

            for (int i = 0; i < 5; i++) {
                persons = repo.findAllPersons();
                assertFalse(persons.isEmpty());

                person = persons.get(0);

                assertEquals("Alok", person.getFirstName());
                assertNotNull(person.getId());
                log.info("Count: {}", (i + 1));
            }

            repo.deleteData(person);

            persons = repo.findAllPersons();
            assertTrue(persons.isEmpty());
        } catch (RepositoryException ex) {
            throw  ex;
        }
    }

    @Test
    public void updatePersonInfo() throws RepositoryException {
        Person person = new Person();
        person.setFirstName("Alok");

        PersonRepository repo = new PersonRepository(true);
        try {
            List<Person> persons = repo.findAllPersons();
            assertTrue(persons.isEmpty());
        } catch (RepositoryException ex) {
            throw  ex;
        }
        try {
            repo.addData(person);
        } catch (RepositoryException ex) {
            throw  ex;
        }
        try {
            List<Person> persons;

            for (int i = 0; i < 5; i++) {
                persons = repo.findAllPersons();
                assertFalse(persons.isEmpty());

                person = persons.get(0);

                assertEquals("Alok", person.getFirstName());
                assertNotNull(person.getId());
                log.info("Count: {}", (i + 1));
            }
        } catch (RepositoryException ex) {
            throw  ex;
        }
        try {
            person = repo.findPersonByName("Alok").get();
            person.setFirstName("Pintu");
            repo.updateData(person);
        } catch (RepositoryException e) {
            log.error(e.getMessage());
        }
        try {
            List<Person> persons;

            for (int i = 0; i < 5; i++) {
                persons = repo.findAllPersons();
                assertFalse(persons.isEmpty());

                person = persons.get(0);

                assertEquals("Pintu", person.getFirstName());
                assertNotNull(person.getId());
                log.info("Count: {}", (i + 1));
            }

            repo.deleteData(person);

            persons = repo.findAllPersons();
            assertTrue(persons.isEmpty());
        } catch (RepositoryException ex) {
            throw  ex;
        }
    }
}
