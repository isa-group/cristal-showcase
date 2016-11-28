package es.us.isa.cristal.showcase.ram2bpmn;

import es.us.isa.bpmn.handler.Bpmn20ModelHandler;
import es.us.isa.bpmn.handler.Bpmn20ModelHandlerImpl;
import es.us.isa.bpmn.xmlClasses.bpmn20.TFlowElement;
import es.us.isa.cristal.ram.Activity;
import es.us.isa.cristal.ram.RAM;
import es.us.isa.cristal.ram2bpmn.Ram2Bpmn;
import es.us.isa.cristal.ram2bpmn.templates.TemplateAssignment;
import es.us.isa.cristal.ram2bpmn.templates.repository.FileTemplateRepository;
import es.us.isa.cristal.ram2bpmn.templates.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ShowcaseResource
 * Copyright (C) 2016 Universidad de Sevilla
 *
 * @author resinas
 */
@RestController
@RequestMapping("/showcase")
public class ShowcaseResource {

    private TemplateRepository repository;

    @Value("${directory}")
    private String directory;

    @PostConstruct
    public void loadRepository() {
        this.repository = new FileTemplateRepository(new File(directory));
    }

    @RequestMapping(value="/bpmn", method= RequestMethod.POST, produces = "application/json")
    public Set<String> bpmn(InputStream bpmn) throws Exception {
        Bpmn20ModelHandler handler = new Bpmn20ModelHandlerImpl();
        handler.load(bpmn);
        return handler.getTaskMap().values().stream().map(TFlowElement::getName).collect(Collectors.toSet());
    }

    @RequestMapping(value="/transform", method=RequestMethod.POST, consumes="application/json", produces="application/xml")
    @ResponseBody
    public byte[] transform(@RequestBody TransformRequest request) throws Exception {
        Bpmn20ModelHandler handler = new Bpmn20ModelHandlerImpl();
        handler.load(new ByteArrayInputStream(request.getBpmn().getBytes()));

        Ram2Bpmn ram2Bpmn = new Ram2Bpmn();
        ram2Bpmn.transformProcess(request.getRam(), handler, repository.buildAssignmentFrom(request.getTempl()));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        handler.save(outputStream);

        return outputStream.toByteArray();


    }

    @RequestMapping(value="/validate", method=RequestMethod.POST, consumes="application/json", produces="application/json")
    public Map<String, Boolean> validate(@RequestBody TransformRequest request) {
        RAM ram = request.getRam();
        TemplateAssignment assignment = repository.buildAssignmentFrom(request.getTempl());
        Set<Activity> nonCompatible = assignment.listNotCompatible(ram);

        return ram.getActivities().stream().collect(Collectors.toMap(Activity::getName, activity -> nonCompatible.contains(activity)));
    }

    @RequestMapping(value="/templates", method=RequestMethod.GET, produces="application/json")
    public Set<String> templates() {
        return repository.listTemplateNames();
    }
}
