package es.us.isa.cristal.showcase.ram2bpmn;

import es.us.isa.cristal.ram.RAM;

import java.util.Map;

/**
 * TranformRequest
 * Copyright (C) 2016 Universidad de Sevilla
 *
 * @author resinas
 */
public class TransformRequest {
    private String bpmn;
    private RAM ram;
    private Map<String, String> templ;

    public String getBpmn() {
        return bpmn;
    }

    public RAM getRam() {
        return ram;
    }

    public Map<String, String> getTempl() {
        return templ;
    }
}
