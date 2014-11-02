/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fantasy.stataggregator;

import org.json.JSONObject;

/**
 *
 * @author Mac
 */
public interface ServiceConsumer {
    public static final String RESOURCE_BASE_LOCATOR = "http://www.fantasyfootballnerd.com/service/";
    
    JSONObject makeRequest();
}
