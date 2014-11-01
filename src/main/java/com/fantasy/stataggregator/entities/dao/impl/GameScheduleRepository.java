/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.entities.dao.impl;

import com.fantasy.stataggregator.entities.GameSchedule;
import com.fantasy.stataggregator.entities.dao.AbstractRepository;

/**
 *
 * @author MacDerson
 */
public class GameScheduleRepository extends AbstractRepository<GameSchedule> {

    public GameScheduleRepository() {
        super(GameSchedule.class);
    }
}