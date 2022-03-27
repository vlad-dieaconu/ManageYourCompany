package com.vlad.ManageYourCompany.repositories;

import com.vlad.ManageYourCompany.model.PasswordResetToken;
import com.vlad.ManageYourCompany.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    PasswordResetToken findByToken(String token);
}
