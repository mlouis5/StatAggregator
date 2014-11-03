/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator.workers;

import com.fantasy.stataggregator.ServiceConsumer;
import static com.fantasy.stataggregator.ServiceConsumer.RESOURCE_BASE_LOCATOR;
import com.fantasy.stataggregator.Task;
import static com.fantasy.stataggregator.workers.FieldSetter.SDF;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author Mac
 */
public abstract class DataRetriever extends FieldSetter implements Task, ServiceConsumer {

    private final String RESOURCE;
    private final String FORMAT = "json";
    private final String KEY = "guxtr5ihdj7t";

    @Autowired
    protected ApplicationContext ctx;

    DataRetriever(String resource) {
        RESOURCE = resource;
        SDF = ctx.getBean(SimpleDateFormat.class);
        SDF.applyLocalizedPattern("yyyy-MM-dd");
    }

    @Override
    public JSONObject makeRequest() {
        WebTarget target = getTarget();

        Response res = target.request(MediaType.APPLICATION_JSON_TYPE).get();
        if (res.getStatus() == Response.Status.OK.getStatusCode()) {
            String consumable = Optional.ofNullable(res.readEntity(String.class)).orElse("");
            if (!consumable.isEmpty()) {
                JSONObject sched;
                try {
                    sched = new JSONObject(consumable);
                    return sched;
                } catch (JSONException ex) {
                    Logger.getLogger(ScheduleRetrieverTask.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    protected WebTarget getTarget() {
        Client client = ctx.getBean(Client.class);
        WebTarget target = client.target(RESOURCE_BASE_LOCATOR).path(RESOURCE).path(FORMAT)
                .path(KEY);
        return target;
    }
}
