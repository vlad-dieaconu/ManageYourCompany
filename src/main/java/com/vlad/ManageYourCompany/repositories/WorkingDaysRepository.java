package com.vlad.ManageYourCompany.repositories;

import com.vlad.ManageYourCompany.model.WorkingDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

public interface WorkingDaysRepository extends JpaRepository<WorkingDays,Long> {

    @Query("SELECT w FROM WorkingDays w WHERE w.user.id = :id")
    Collection<WorkingDays> findWorkingDaysByUser(@Param("id") Long id);

    @Query("SELECT w FROM WorkingDays w WHERE w.date = :date")
    Collection<WorkingDays> findWorkingDaysByDate(@Param("date") Date date);

    //get working days by the current month
    @Query("SELECT w FROM WorkingDays w WHERE w.date BETWEEN :startDate AND :endDate")
    Collection<WorkingDays> findWorkingDaysByMonth(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


}
