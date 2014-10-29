/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.entities.dao.impl;

import com.fantasy.stataggregator.entities.GameData;
import com.fantasy.stataggregator.entities.dao.AbstractRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Mac
 */
public class GameDataRepository extends AbstractRepository<GameData>  {
    
    public GameDataRepository() {
        super(GameData.class);
    }
    
}
