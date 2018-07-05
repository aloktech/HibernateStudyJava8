/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.onetoone.repo;

import com.imos.sample.base.QueryType;
import com.imos.sample.base.repo.AbstractRepository;
import com.imos.sample.base.exception.RepositoryException;
import com.imos.sample.onetoone.model.Person;
import com.imos.sample.onetoone.model.support.PersonInfo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author ameher
 */
@Log4j2
public class PersonRepository extends AbstractRepository {

    public List<Person> findAllPersonsAsJPAQuery() throws RepositoryException {
        List<Person> persons = new ArrayList<>();
        try {
            persons = extractListAsResult("from Person p", Person.class);
            log.info("Find all Person");
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
            throw ex;
        }
        return persons;
    }

    public List<Person> findAllPersonsAsNativeQuery() throws RepositoryException {
        List<Person> persons = new ArrayList<>();
        try {
            persons = extractListAsResult("select * from Person p", QueryType.NATIVE_QUERY, Person.class);
            log.info("Find all Person");
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
            throw ex;
        }
        return persons;
    }

    public Optional<Person> findPersonByFirstName(String firstName) throws RepositoryException {
        Optional<Person> personOpt = Optional.empty();
        addParameter("firstName", firstName);
        try {
            personOpt = extractUniqueResult("select p "
                    + "from Person p "
                    + "inner join p.personDetail pd "
                    + "where pd.firstName = :firstName", Person.class);
            log.info("Find a Person with firstName = " + firstName);
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
            throw ex;
        }
        return personOpt;
    }

    public Optional<PersonInfo> findPersonInfoByFirstNameAsJPAQuery(String firstName) throws RepositoryException {
        Optional<PersonInfo> personOpt = Optional.empty();
        Function<Object, PersonInfo> func = o -> {
            Object[] objArray = (Object[]) o;
            return new PersonInfo((String) objArray[0], (String) objArray[1], (Date) objArray[2]);
        };
        addParameter("firstName", firstName);
        try {
            personOpt = extractUniqueResult("select pd.firstName, pd.lastName, pd.dateOfBirth "
                    + "from Person p "
                    + "inner join p.personDetail pd "
                    + "where pd.firstName = :firstName", func);
            log.info("Find a Person with firstName = " + firstName);
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
            throw ex;
        }

        return personOpt;
    }

    public Optional<PersonInfo> findPersonInfoByFirstNameAsNativeQuery(String firstName) throws RepositoryException {
        Optional<PersonInfo> personOpt = Optional.empty();
        Function<Object, PersonInfo> func = o -> {
            Object[] objArray = (Object[]) o;
            System.out.println(objArray);
            return new PersonInfo((String) objArray[0], (String) objArray[1], (Date) objArray[2]);
        };
        addParameter("firstName", firstName);
        try {
            personOpt = extractUniqueResult("select pd.FIRST_NAME, pd.LAST_NAME, pd.DATE_OF_BIRTH "
                    + "from PERSON as p "
                    + "inner join PERSON_DETAIL as pd "
                    + "on p.id = pd.PERSON_ID_FK "
                    + "where pd.FIRST_NAME = :firstName", QueryType.NATIVE_QUERY, func);
            log.info("Find a Person with firstName = " + firstName);
        } catch (RepositoryException ex) {
            log.error(ex.getMessage());
            throw ex;
        }

        return personOpt;
    }
}
