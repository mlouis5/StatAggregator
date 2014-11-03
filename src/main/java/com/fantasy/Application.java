/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy;

import com.fantasy.stataggregator.ContextUser;
import com.fantasy.stataggregator.Task;
import com.fantasy.stataggregator.YearlyTask;
import com.fantasy.stataggregator.annotations.TaskRunner;
import com.fantasy.utilities.containers.YearContainer;
import com.fantasy.utilities.flags.YearFlag;
import com.fantasy.utilities.parsers.SpaceDelimitedCommandLineParser;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

/**
 *
 * @author Mac
 */
public class Application {

    public static void main(String[] args) throws ParseException, Exception {
        ConfigurableApplicationContext context;
        context = SpringApplication.run(AggregatorConfig.class);

        SpaceDelimitedCommandLineParser<YearFlag, YearContainer> argParser;
        argParser = context.getBean(SpaceDelimitedCommandLineParser.class);
        YearContainer container = argParser.parseFor(YearFlag.class, args);

        String packageName = container.getPackageName();
        String className = container.getClassName();
        int year = container.getYear();

        List<Class> taskRunners = findTypes(packageName);
        Class runner = null;

        if (Objects.nonNull(taskRunners)) {
            for (Class cl : taskRunners) {
                if (cl.getSimpleName().equalsIgnoreCase(className)) {
                    runner = cl;
                    break;
                }
            }

            if (Objects.nonNull(runner)) {
                Task task = (Task) context.getBean(runner);

                if (Objects.nonNull(task)) {
                    if (task instanceof ContextUser) {
                        ((ContextUser) task).haveContext(context);
                    }
                    if (task instanceof YearlyTask) {
                        ((YearlyTask) task).setYear(year);
                    }

                    try {
                        task.run();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        if (Objects.nonNull(context)) {
                            context.close();
                        }
                    }
                }
            }
        }
    }

    private static List<Class> findTypes(String basePackage) throws IOException, ClassNotFoundException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

        List<Class> candidates = new ArrayList();
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + resolveBasePackage(basePackage) + "/" + "**/*.class";
        Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                if (isCandidate(metadataReader)) {
                    candidates.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
                }
            }
        }
        return candidates;
    }

    private static String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
    }

    private static boolean isCandidate(MetadataReader metadataReader) throws ClassNotFoundException {
        try {
            Class c = Class.forName(metadataReader.getClassMetadata().getClassName());
            if (c.getAnnotation(TaskRunner.class) != null) {
                return true;
            }
        } catch (Throwable e) {
        }
        return false;
    }

}
