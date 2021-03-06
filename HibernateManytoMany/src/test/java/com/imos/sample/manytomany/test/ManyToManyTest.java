/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.manytomany.test;

import com.imos.sample.base.HibernateService;
import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.manytomany.model.Event;
import com.imos.sample.manytomany.model.Person;
import com.imos.sample.manytomany.model.PersonAddress;
import com.imos.sample.manytomany.model.PersonDetail;
import com.imos.sample.manytomany.model.support.PersonInfo;
import com.imos.sample.manytomany.repo.PersonRepository;
import com.imos.sample.manytomany.util.EventType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author ameher
 */
@Log4j2
public class ManyToManyTest {

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
    public void addPersonCheck() throws RepositoryException {
        Person person = new Person();

        PersonDetail personDetail = new PersonDetail();
        personDetail.setFirstName("Alok");

        person.setPersonDetail(personDetail);
        personDetail.setPerson(person);

        PersonAddress address1 = new PersonAddress();
        address1.setCity("Bengaluru");

        person.getAddresses().add(address1);
        address1.setPerson(person);

        PersonAddress address2 = new PersonAddress();
        address2.setCity("Balangir");

        person.getAddresses().add(address2);
        address2.setPerson(person);

        Event event = new Event();
        event.setEventType(EventType.BIRTHDAY);
        event.setDateOfEvent(LocalDate.of(2018, 6, 14).plusDays(6));
        event.setStartTime(LocalDateTime.now());
        event.getAttendent().add(person);
        
        person.getEvents().add(event);
        
        PersonRepository repo = new PersonRepository();
        try {
            repo.addData(person, personDetail, address1, address2, event);
        } catch (RepositoryException ex) {
            throw  ex;
        }

        List<Person> persons = repo.findAllPersons();
        assertFalse(persons.isEmpty());
        assertEquals(1, persons.size());
        
        Optional<PersonInfo> personInfoOp = repo.findPersonByFirstName("Alok");
        assertTrue(personInfoOp.isPresent());
        PersonInfo personInfo = personInfoOp.get();
        assertEquals("Alok", personInfo.getFirstName());
        
        List<PersonAddress> addresses = repo.findAllAddressOfPersonByFirstName("Alok");
        assertFalse(addresses.isEmpty());
        assertEquals(2, addresses.size());
        List<String> cities = Arrays.asList("Bengaluru", "Balangir");
        assertTrue(cities.contains(addresses.get(0).getCity()));
        assertTrue(cities.contains(addresses.get(1).getCity()));
        
        Optional<PersonAddress> personAddressOp = repo.findAddressOfPersonByFirstNameAndCity("Alok", "Bengaluru");
        assertTrue(personAddressOp.isPresent());
        address1 = personAddressOp.get();
        assertEquals("Bengaluru", address1.getCity());
        assertNull(address1.getCountry());
        
        personAddressOp = repo.findAddressOfPersonByFirstNameAndCity("Alok", "Balangir");
        assertTrue(personAddressOp.isPresent());
        address1 = personAddressOp.get();
        assertEquals("Balangir", address1.getCity());
        assertNull(address1.getCountry());
        
        List<Event> events = repo.findEventOfPersonByFirstNameAndDate("Alok", LocalDate.of(2018, 6, 14).plusDays(6));
        assertFalse(events.isEmpty());
        assertEquals(LocalDate.of(2018, 6, 14).plusDays(6), events.get(0).getDateOfEvent());
    }
}
