package com.vlad.ManageYourCompany.repositories;

import com.vlad.ManageYourCompany.model.ERole;
import com.vlad.ManageYourCompany.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
