package com.vlad.ManageYourCompany.repositories;

import com.vlad.ManageYourCompany.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
     List<Project> findAllByOrderByIdDesc();
     Optional<Project> findById(Long id);
}
