package com.znaidi.ppmtool.web;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.znaidi.ppmtool.domain.Project;
import com.znaidi.ppmtool.services.ErrorValidationService;
import com.znaidi.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ErrorValidationService errorValidationService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {
        ResponseEntity<?> errorMap= errorValidationService.errorValidation(result);
        if (errorMap!=null) {
            return errorMap;
        }
        projectService.saveOrUpdate(project);
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){

        Project project = projectService.findProjectByIdentifier(projectId);

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProjects(){

        return new ResponseEntity<Iterable<Project>>(projectService.findAllProjects(),HttpStatus.OK);
    }

    @DeleteMapping("/{projectID}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectID){
        projectService.deleteProjectByIdentifier(projectID);
        return new ResponseEntity<String>("Project Successfully Deleted",HttpStatus.OK);
    }

    @PutMapping("/{projectID}")
    public ResponseEntity<?> updateProject(@PathVariable String projectID,@Valid @RequestBody Project project) {
        project=projectService.updateProject(projectID,project);
        return  new ResponseEntity<Project>(project,HttpStatus.OK);
    }
}
