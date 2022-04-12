package com.vlad.ManageYourCompany.repositories;

import com.vlad.ManageYourCompany.model.Project;
import com.vlad.ManageYourCompany.model.ProjectCommits;
import com.vlad.ManageYourCompany.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectCommitsRepository extends JpaRepository<ProjectCommits, Long> {
    //TODO IMPLEMENT ADMIN FEATURE
    ProjectCommits findProjectCommitsByUser(User user);
    List<ProjectCommits> findByProject(Project project);
}