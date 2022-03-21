package com.vlad.ManageYourCompany.repositories;

import com.vlad.ManageYourCompany.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
     List<Project> findAllByOrderByIdDesc();
}
