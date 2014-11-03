/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.workers;

import com.fantasy.stataggregator.Task;
import com.fantasy.stataggregator.YearlyTask;
import com.fantasy.stataggregator.annotations.TaskRunner;
import com.fantasy.stataggregator.entities.GameData;
import com.fantasy.stataggregator.entities.GameDataPK;
import com.fantasy.stataggregator.entities.GameDataPK_;
import com.fantasy.stataggregator.entities.GameData_;
import com.fantasy.stataggregator.entities.GameSchedule;
import com.fantasy.stataggregator.entities.GameSchedule_;
import com.fantasy.stataggregator.entities.dao.impl.GameDataRepository;
import com.fantasy.stataggregator.entities.dao.impl.GameScheduleRepository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * StatCompilerTask compiles game statistics into whole season statistics for each player<br>
 * who played within each individual game.
 * 
 * @version %I%, %G%
 * @author Mac
 */
@TaskRunner
public class StatCompilerTask implements YearlyTask{
    
    @Autowired
    private ApplicationContext ctx;
    private int year;
    private List<GameData> gamesData;
    
    @Override
    public void setYear(int year) throws Exception {
        this.year = year;
        
            GameDataRepository gdr = ctx.getBean(GameDataRepository.class);
            if (year == Integer.MAX_VALUE) {
                gamesData = gdr.findAll();
            } else {
                SimpleDateFormat sdf = ctx.getBean(SimpleDateFormat.class);
                sdf.applyLocalizedPattern("yyyyMMdd");

                Date min = sdf.parse(year + START_OF_YEAR);
                Date max = sdf.parse(year + END_OF_YEAR);

                CriteriaBuilder cb = gdr.getCriteriaBuilder();
                CriteriaQuery<GameData> cq = gdr.getCriteriaQuery();
                Root<GameData> gameData = gdr.getRoot();
                Path<GameDataPK> pk = gameData.get(GameData_.gameDataPK);
                cq.select(gameData).where(cb.equal(pk.get(GameDataPK_.year), year)).orderBy(cb.asc(pk.get(GameDataPK_.gameIdentifier)));
                
                gamesData = gdr.getCriteriaList(cq);
                System.out.println(gamesData.size());
            }
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
