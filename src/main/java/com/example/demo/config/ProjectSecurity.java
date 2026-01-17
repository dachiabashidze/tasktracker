package com.example.demo.config;

import com.example.demo.enums.Role;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component("projectSecurity")
public class ProjectSecurity {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    public ProjectSecurity(ProjectRepository projectRepository, UserRepository userRepository){
        this.projectRepository=projectRepository;
        this.userRepository=userRepository;
    }

    public boolean isManagerOfProject(Long projectId, Long userId){
        Project project = projectRepository.findById(projectId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if(user == null || project == null ){
            return false;
        }
        return user.getRole() == Role.MANAGER && project.getOwner().getId().equals(userId);
    }
}
