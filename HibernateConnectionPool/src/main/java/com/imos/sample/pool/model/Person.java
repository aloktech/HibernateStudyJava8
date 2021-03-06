/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imos.sample.pool.model;

import com.imos.sample.base.model.Base;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author ameher
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table
public class Person extends Base {
    
    private static final long serialVersionUID = -6002336611138110153L;
    
    @Column(name = "USER_NAME")
    private String userName;
    
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "SALT_VALUE")
    private String saltValue;
    
    @Column(name = "FIRST_NAME")
    private String firstName;
    
    @Column(name = "MIDDLE_NAME")
    private String middleName;
    
    @Column(name = "LAST_NAME")
    private String lastName;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;
    
    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;
    
    @Column(name = "EMAIL")
    private String email;
}
