package com.znaidi.ppmtool.services;

import com.znaidi.ppmtool.domain.Backlog;
import com.znaidi.ppmtool.domain.ProjectTask;
import com.znaidi.ppmtool.repositories.BacklogRepository;
import com.znaidi.ppmtool.repositories.ProjectTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {
    @Autowired
    BacklogRepository backlogRepository;

    @Autowired
    ProjectTaskRepository projectTaskRepository;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask){
        System.out.println("hello");
        //Exception projectNotFound
        //PTs to be added to a specific prooject, project != null , Backlog exists
        Backlog backlog=backlogRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        //set the backlog to projectTask
        projectTask.setBacklog(backlog);

        //update the Backlog sequence
        Integer backlogSequence= backlog.getPTSequence();
        backlogSequence++;
        System.out.println(backlogSequence);
        backlog.setPTSequence(backlogSequence);
        backlogRepository.save(backlog); //updating backlog with the new BacklogSequence
        //addSequence to PT
        projectTask.setProjectSequence(projectIdentifier+"-"+backlogSequence);
        System.out.println(projectTask.getProjectSequence());
        projectTask.setProjectIdentifier(projectIdentifier);

//        //initial priority when priority is null
        if (projectTask.getPriority()==null){
            projectTask.setPriority(3);
        }
        //intitial status when status is null
        if (projectTask.getStatus()=="" ||projectTask.getStatus()==null ){
            projectTask.setStatus("TO_DO");
        }

        return projectTaskRepository.save(projectTask);
    }
}
