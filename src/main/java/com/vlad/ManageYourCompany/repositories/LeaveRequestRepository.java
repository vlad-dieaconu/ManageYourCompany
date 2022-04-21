package com.vlad.ManageYourCompany.repositories;

import com.vlad.ManageYourCompany.model.LeaveRequest;
import com.vlad.ManageYourCompany.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findAllByOrderByIdDesc();
    List<LeaveRequest> findByUserOrderByIdDesc(User user);
}
