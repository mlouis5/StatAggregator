/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.entities.dao.impl;

import com.fantasy.stataggregator.entities.NflSchedule;
import com.fantasy.stataggregator.entities.dao.AbstractRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author MacDerson
 */
public class ScheduleRepository extends AbstractRepository<NflSchedule> {

    //@Autowired
    private EntityManager em;

    public ScheduleRepository(EntityManagerFactory emf) {
        super(NflSchedule.class);
        em = emf.createEntityManager();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<NflSchedule> findByNamedQuery(String name) {
        NamedQueries nq = NflSchedule.class.getAnnotation(NamedQueries.class);
        NamedQuery[] nqs = nq.value();
        String queryName = null;

        for (NamedQuery qn : nqs) {
            String qname = qn.name();
            if(qname.contains(name)){
                queryName = qname;
                break;
            }
        }
        List<NflSchedule> results = new ArrayList(1);
        if (Objects.nonNull(queryName)) {
            TypedQuery<NflSchedule> query
                    = getEntityManager().createNamedQuery(queryName, NflSchedule.class);
            results = query.getResultList();
        }
        return results;
    }

}
