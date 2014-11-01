/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.utilities.parsers;

import com.fantasy.stataggregator.exceptions.MalFormedArgException;
import com.fantasy.utilities.CommandLineContainer;
import com.fantasy.utilities.CommandLineFlag;
import com.fantasy.utilities.CommandLineParser;
import java.util.Objects;

/**
 *
 * @author Mac
 * @param <T>
 * @param <V>
 */
public class SpaceDelimitedCommandLineParser<T extends CommandLineFlag, V extends CommandLineContainer>
        implements CommandLineParser<T, V> {

    @Override
    public V parseFor(Class<T> flag, String[] args) throws
            InstantiationException,
            IllegalAccessException, MalFormedArgException, Exception {
        if (Objects.nonNull(flag) && Objects.nonNull(args)) {
            T[] flags = flag.getEnumConstants();
            Class<V> containerClass = flags[0].forContainer();
            V container = containerClass.newInstance();

            return buildContainer(args, flags, container);
        }
        return null;
    }

    private V buildContainer(String[] args, T[] flags, V container) throws MalFormedArgException, Exception {
        int index = 0;
        int hardStop = args.length;
        for (String token : args) {
            for (T flag : flags) {
                if (flag.value().matcher(token).matches()) {
                    if (flag.capturesData()) {
                        if ((index + 1) < hardStop) {
                            int locator = index + 1;
                            String val = args[locator];

                            if (!flag.isFlag(val) && flag.matches(flag.captureFormat(), val)) {
                                container.setValueFor(flag, val);
                            }
                        } else {
                            throw new MalFormedArgException("Index array out of "
                                    + "bounds exception captured: No input for: "
                                    + flag + " = " + token);
                        }
                    } else {
                        container.setValueFor(flag, null);
                    }
                }
            }
            index++;
        }
        return (V) container;
    }
}
