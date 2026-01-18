package com.example.demo.controller;

import com.example.demo.model.Project;
import com.example.demo.service.ProjectService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/Project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService=projectService;
    }
    @GetMapping
    public List<Project> getAllProjects(){
        return projectService.getAllProjects();
    }
    @GetMapping("/{id}")
    public Optional<Project> getProject(@PathVariable Long id){
        return projectService.getProject(id);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @PostMapping
    public void addProject(@RequestBody Project project, Authentication authentication){
        String email = authentication.getName();
        projectService.addProject(project,email);
    }
    @PreAuthorize("""
    hasRole('ROLE_ADMIN')
    or (hasRole('ROLE_MANAGER') and @projectSecurity.isManagerOfProject(#projectId,#userId))
    """)
    @PutMapping("/{projectId}")
    public void updateProject(@PathVariable Long projectId, @RequestBody Project project,@RequestParam Long userId){
    projectService.updateProject(projectId,project);
    }





}
