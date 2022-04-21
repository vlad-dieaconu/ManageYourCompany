package com.vlad.ManageYourCompany.repositories;

import com.vlad.ManageYourCompany.model.AdminNotification;
import com.vlad.ManageYourCompany.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminNotificationRepository extends JpaRepository<AdminNotification,Long> {

    List<AdminNotification> findAllByOrderByIdDesc();
    List<AdminNotification> findByUser(User user);
}
