package com.znaidi.ppmtool.web;

import com.znaidi.ppmtool.domain.ProjectTask;
import com.znaidi.ppmtool.repositories.BacklogRepository;
import com.znaidi.ppmtool.repositories.ProjectTaskRepository;
import com.znaidi.ppmtool.services.ErrorValidationService;
import com.znaidi.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {



    @Autowired
    private ProjectTaskService projectTaskService;
    @Autowired
    private ErrorValidationService errorValidationService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                            BindingResult result, @PathVariable String backlog_id){
        ResponseEntity<?> errorMap = errorValidationService.errorValidation(result);
        if (errorMap != null) return errorMap;

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask);

        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }

}
