/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.entities.dao.impl;

import com.fantasy.stataggregator.entities.Team;
import com.fantasy.stataggregator.entities.dao.AbstractRepository;

/**
 *
 * @author Mac
 */
public class TeamRepository extends AbstractRepository<Team>  {
    
    public TeamRepository() {
        super(Team.class);
    }
    
}