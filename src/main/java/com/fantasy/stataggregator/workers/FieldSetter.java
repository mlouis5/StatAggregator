/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.workers;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Mac
 */
public abstract class FieldSetter {

    protected static SimpleDateFormat SDF;

    public void fieldSetter(Object instance, Field field, String value) throws 
            IllegalArgumentException, IllegalAccessException, ParseException {
        if (Objects.nonNull(value)) {
            if (!value.isEmpty()) {
                Class<?> type = field.getType();
                field.setAccessible(true);
                if (type.equals(Integer.class)) {
                    field.set(instance, Integer.parseInt(value));
                } else if (type.equals(Double.class)) {
                    field.set(instance, Double.parseDouble(value));
                } else if (type.equals(Date.class)) {
                    field.set(instance, SDF.parse(value));
                } else if (type.equals(String.class)) {
                    field.set(instance, value);
                }
                field.setAccessible(false);
            }
        }
    }

}
