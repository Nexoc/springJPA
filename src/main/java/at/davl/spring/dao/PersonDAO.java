package at.davl.spring.dao;


import at.davl.spring.models.Person;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class PersonDAO {

    private final EntityManager em;

    @Autowired
    public PersonDAO(EntityManager em) {
        this.em = em;
    }

    @Transactional(readOnly = true)
    public void testNPlus1() {
        Session session = em.unwrap(Session.class);

        /*
        List<Person> people = session.createQuery("select p from Person p", Person.class)
                .getResultList();
        for (Person p : people) {
            System.out.println("person " + p.getName() + " has " + p.getItems());
         */
        Set<Person> people = new HashSet<Person>(session.createQuery(
                "select p from Person p LEFT JOIN FETCH p.items", Person.class)
                .getResultList());

        for (Person p : people) {
            System.out.println("person " + p.getName() + "has: " + p.getItems());
        }
    }
}