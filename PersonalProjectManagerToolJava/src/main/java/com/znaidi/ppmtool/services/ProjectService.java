package com.znaidi.ppmtool.services;


import com.znaidi.ppmtool.domain.Project;
import com.znaidi.ppmtool.exceptions.ProjectIdException;
import com.znaidi.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdate(Project project){
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch(Exception e)
        {
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectIdentifier){
        Project project=projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
        if (project== null)
        {
            throw new ProjectIdException("Project ID '"+projectIdentifier+"'does not exist");
        }
        return project ;
    }
}
