package com.grandcircus.spring.controller;

import com.grandcircus.spring.models.CustomersEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

/**
 * Created by USER on 5/8/2017.
 */


@Controller
public class HomeController {

    /*@RequestMapping("/")
    public ModelAndView helloWorld(){
        return new ModelAndView("welcome","hello","Hello World");
    }*/

    @RequestMapping("/")

    public ModelAndView listCustomer() {
        //Represents one approach for bootstrapping Hibernate. In fact, historically this was the way to bootstrap Hibernate.
        //The approach here is to define all configuration and mapping sources in one API and to then build the SessionFactory in one-shot.
        // The configuration and mapping sources defined here are just held here until the SessionFactory is built.
        // This is an important distinction from the legacy behavior of this class,
        // where we would try to incrementally build the mappings from sources as they were added.
        // The ramification of this change in behavior is that users can add configuration and mapping sources here,
        // but they can no longer query the in-flight state of mappings (PersistentClass, Collection, etc) here.
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");



        //The main contract here is the creation of Session instances.
        // Usually an application has a single SessionFactory instance and threads servicing
        // client requests obtain Session instances from this factory.
        // The internal state of a SessionFactory is immutable.
        // Once it is created this internal state is set. This internal state
        // includes all of the metadata about Object/Relational Mapping.
        SessionFactory sessionFact = cfg.buildSessionFactory();

        //The main runtime interface between a Java application and Hibernate.
        // This is the central API class abstracting the notion of a persistence service.

        //The lifecycle of a Session is bounded by the beginning and end of a logical transaction.
        // (Long transactions might span several database transactions.)

        //The main function of the Session is to offer create,
        // read and delete operations for instances of mapped entity classes.
        // Instances may exist in one of three states:

        //transient: never persistent, not associated with any Session
        //persistent: associated with a unique Session
        //detached: previously persistent, not associated with any Session

        Session selectCustomers = sessionFact.openSession();

        selectCustomers.beginTransaction();

        //Criteria is a simplified API for retrieving entities by composing Criterion objects.
        // This is a very convenient approach for functionality like "search" screens where there is a
        // variable number of conditions to be placed upon the result set.
        Criteria c = selectCustomers.createCriteria(CustomersEntity.class);

        ArrayList<CustomersEntity> customerList = (ArrayList<CustomersEntity>) c.list();

        return new
                ModelAndView("welcome", "cList", customerList);
    }
}

