package com.vlad.ManageYourCompany.controller;

import com.vlad.ManageYourCompany.controller.payload.ProjectRequest;
import com.vlad.ManageYourCompany.controller.payload.response.MessageResponse;
import com.vlad.ManageYourCompany.exceptions.ProjectNotFoundException;
import com.vlad.ManageYourCompany.model.Project;
import com.vlad.ManageYourCompany.model.User;
import com.vlad.ManageYourCompany.repositories.ProjectRepository;
import com.vlad.ManageYourCompany.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;


    @GetMapping("/getEmployees")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers(){
        List<User> users;
        users = userRepository.findAll();

        return users;
    }

    @GetMapping("/getProjects")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Project> getProjects(){
        List<Project> projects;
        projects = projectRepository.findAll();

        return projects;
    }

    @PostMapping("/addProject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addNewProject(@RequestBody ProjectRequest projectRequest) {

        Project project = new Project();
        project.setLocatie(projectRequest.getLocatie());
        project.setNumarActualResurse(0);
        project.setNume(projectRequest.getNume());
        project.setNumarResurseNecesare(projectRequest.getNumarResurseNecesare());

        projectRepository.save(project);

        return ResponseEntity.ok(new MessageResponse("Project added successfully!"));
    }

    @PutMapping("/addUserToProject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addUserToProject(@RequestParam Long idUser, @RequestParam Long idProject) {

        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + idUser));
        Project project = projectRepository.findById(idProject)
                .orElseThrow(() -> new ProjectNotFoundException(idProject));

        if (project.getNumarResurseNecesare() == 0) {
            return ResponseEntity.ok(new MessageResponse("This project have enough members"));
        }

        project.setNumarResurseNecesare(project.getNumarResurseNecesare() - 1);
        project.setNumarActualResurse(project.getNumarActualResurse() + 1);


        user.setProject(project);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }


}
