package com.example.demo.service;

import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        projectRepository.findAll().forEach(projects::add);
        return projects;
    }

    public Optional<Project> getProject(Long id) {
        return projectRepository.findById(id);
    }

    public void addProject(Project project, String email) {
        User owner = userRepository.findByEmail(email);
        project.setOwner(owner);
        project.setCreateDate(LocalDateTime.now());
        project.setUpdateDate(LocalDateTime.now());
        projectRepository.save(project);
    }

    public void updateProject(Long id, Project project) {
        Optional<Project> existing = projectRepository.findById(id);
        if (existing.isPresent()) {
            Project p = existing.get();
            p.setName(project.getName());
            p.setDescription(project.getDescription());
            p.setUpdateDate(LocalDateTime.now());
            projectRepository.save(p);
        }
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}