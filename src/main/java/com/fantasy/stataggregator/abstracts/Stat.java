/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.abstracts;

import com.fantasy.stataggregator.Athlete;
import com.fantasy.stataggregator.Stats;
import java.util.Objects;

/**
 *
 * @author MacDerson
 * @param <T>
 */
public abstract class Stat<T extends Stats> implements Stats<T>, Athlete {

    private final String id;
    private final String name;
    private final String position;
    private final String team;

    public Stat(String id, String name, String team, String position) {
        this.id = id;
        this.name = name;
        this.team = team;
        this.position = position;
    }

    @Override
    public final String getId() {
        return this.id;
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final String getTeam() {
        return this.team;
    }

    @Override
    public final String getPosition() {
        return this.position;
    }

    @Override
    public abstract T getStat();

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 + hash + Objects.hashCode(this.team);
        hash = 89 + hash + Objects.hashCode(this.position);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Stat other = (Stat) obj;

        return Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name)
                && Objects.equals(this.team, other.team) && Objects.equals(this.position, other.position);
    }

}
