package com.vlad.ManageYourCompany.controller;

import com.vlad.ManageYourCompany.controller.payload.ProjectRequest;
import com.vlad.ManageYourCompany.controller.payload.WorkingDayRequest;
import com.vlad.ManageYourCompany.controller.payload.response.MessageResponse;
import com.vlad.ManageYourCompany.exceptions.LeaveRequestNotFoundException;
import com.vlad.ManageYourCompany.exceptions.ProjectNotFoundException;
import com.vlad.ManageYourCompany.model.*;
import com.vlad.ManageYourCompany.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    WorkingDaysRepository workingDaysRepository;

    @Autowired
    LeaveRequestRepository leaveRequestRepository;

    @Autowired
    AdminNotificationRepository adminNotificationRepository;

    @Autowired
    ProjectCommitsRepository projectCommitsRepository;




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
        projects = projectRepository.findAllByOrderByIdDesc();
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
        project.setDescriere(projectRequest.getDescriere());

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

    @PutMapping("/editProject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editProject(@RequestParam Long id, @RequestBody ProjectRequest projectRequest){

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
        project.setDescriere(projectRequest.getDescriere());
        projectRepository.save(project);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/getProject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> getProject(@RequestParam Long id){
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));

        return ResponseEntity.ok(project);

    }

    @DeleteMapping("/deleteProject")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProject(@RequestParam Long id){
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
        projectRepository.delete(project);
    }


    @GetMapping("/getWorkingDays")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getWorkingDays(){

        List<WorkingDays> workingDays;
        workingDays = workingDaysRepository.findAll();

        return ResponseEntity.ok(workingDays);
    }

    @GetMapping("/getWorkingDaysForOneEmployee")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getWorkingDaysForOnEmployee(@RequestParam Long id){

        Collection<WorkingDays> workingDays;
        workingDays = workingDaysRepository.findWorkingDaysByUser(id);

        return ResponseEntity.ok(workingDays);
    }

    @PostMapping("/getWorkingDayByDate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getWorkingDayByDate(@RequestBody WorkingDayRequest date){

        Collection<WorkingDays> workingDays;
        workingDays = workingDaysRepository.findWorkingDaysByDate(date.getDate());

        return ResponseEntity.ok(workingDays);
    }

    @GetMapping("/getWorkingDaysFromCurrentMonth")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getWorkingDaysFromCurrentMonth(){

        Collection<WorkingDays> workingDays;
        LocalDate todayDate = LocalDate.now();
        LocalDate firstDayOfTheMonth = todayDate.withDayOfMonth(1);

        System.out.println("First day of the month "+firstDayOfTheMonth);

        LocalDate lastDayOfTheMonth = todayDate.withDayOfMonth(todayDate.lengthOfMonth());

        System.out.println("Last day of the month "+lastDayOfTheMonth);

        Date startDate = Date.from(firstDayOfTheMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(lastDayOfTheMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());

        workingDays = workingDaysRepository.findWorkingDaysByMonth(startDate, endDate);

        return ResponseEntity.ok(workingDays);
    }


    @DeleteMapping("/removeEmployee")
    @PreAuthorize("hasRole('ADMIN')")
    public void removeEmployee(@RequestParam Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + id));
        userRepository.delete(user);
    }

    @PutMapping("/acceptLeaveRequest")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> acceptLeaveRequest(@RequestParam Long id){

        LeaveRequest leaveRequest = leaveRequestRepository.findById(id).orElseThrow(() -> new LeaveRequestNotFoundException(id));
        leaveRequest.setSeenBySuperior(true);
        leaveRequest.setApproved(true);

        User user = leaveRequest.getUser();

        long differenceBetweenDates = leaveRequest.getEndDate().getTime() - leaveRequest.getStartDate().getTime();
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(differenceBetweenDates) % 365;

        if(differenceInDays == 0){
            user.setFreeDays(user.getFreeDays() - 1);
            user.setFreeDaysTaken(user.getFreeDaysTaken() + 1);
        }
        else if (user.getFreeDays() > differenceInDays) {
            user.setFreeDays(user.getFreeDays() - (int) differenceInDays);
            user.setFreeDaysTaken(user.getFreeDaysTaken() + (int) differenceInDays);
            userRepository.save(user);
        }

        leaveRequest.setNumberOfDays((int) differenceInDays);
        leaveRequestRepository.save(leaveRequest);

        return ResponseEntity.ok("Vacation request was accepted!");

    }

    @PutMapping("/declineLeaveRequest")
    @PreAuthorize("hasRole('ADMIN')")
    public void declineLeaveRequest(@RequestParam Long id){

        LeaveRequest leaveRequest = leaveRequestRepository.findById(id).orElseThrow(() -> new LeaveRequestNotFoundException(id));
        leaveRequest.setSeenBySuperior(true);
        leaveRequest.setApproved(false);
        leaveRequestRepository.save(leaveRequest);
    }

    @GetMapping("/getAllLeaveRequests")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllLeaveRequests(){
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAllByOrderByIdDesc();
        return ResponseEntity.ok(leaveRequests);
    }

    @GetMapping("/getAllLeaveRequestsForOneUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getLeaveRequestsForOneUser(@RequestParam Long userId){

        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + userId));

        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByUserOrderByIdDesc(user);
        return ResponseEntity.ok(leaveRequests);
    }

    @GetMapping("/getAllNotifications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllNotifications(){
        List<AdminNotification> notifications = adminNotificationRepository.findAllByOrderByIdDesc();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/getAllNotificationsForOneUser")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getNotificationsForOneUser(@RequestParam Long userId){

        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + userId));

        List<AdminNotification> notifications = adminNotificationRepository.findByUser(user);
        return ResponseEntity.ok(notifications);
    }

    @DeleteMapping("/deleteNotification")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteNotification(@RequestParam Long id){

        adminNotificationRepository.deleteById(id);


        return ResponseEntity.ok(adminNotificationRepository.findAll());

    }

    @GetMapping("/getTheMostPopularProject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProjectWithMostCommits(){

        List<Project> projects = projectRepository.findAll();

        //from projects get the project with the projectCommits of the largest size
        Project projectWithMostCommits = projects.stream()
                .max(Comparator.comparing(Project::getSizeOfCommits))
                .orElseThrow(() -> new ProjectNotFoundException("No projects found"));

        List<ProjectCommits> projectCommits = projectWithMostCommits.getProjectCommits();

        return ResponseEntity.ok(projectWithMostCommits);
    }


}
