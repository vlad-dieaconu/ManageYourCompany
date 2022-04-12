package com.vlad.ManageYourCompany.controller;


import com.vlad.ManageYourCompany.controller.payload.EditProfileRequest;
import com.vlad.ManageYourCompany.controller.payload.LeaveTypeRequest;
import com.vlad.ManageYourCompany.controller.payload.ProjectCommitsRequest;
import com.vlad.ManageYourCompany.controller.payload.WorkingDayRequest;
import com.vlad.ManageYourCompany.model.*;
import com.vlad.ManageYourCompany.repositories.LeaveRequestRepository;
import com.vlad.ManageYourCompany.repositories.ProjectCommitsRepository;
import com.vlad.ManageYourCompany.repositories.UserRepository;
import com.vlad.ManageYourCompany.repositories.WorkingDaysRepository;
import com.vlad.ManageYourCompany.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkingDaysRepository workingDaysRepository;

    @Autowired
    ProjectCommitsRepository projectCommitsRepository;

    @Autowired
    LeaveRequestRepository leaveRequestRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/getPersonalDetails")
    public ResponseEntity<?> getUserData(@RequestParam Long id) {

        Optional<User> user = userRepository.findById(id);

        return ResponseEntity.ok(user);
    }


    @PutMapping("/editProfile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public User editProfile(@RequestBody EditProfileRequest editProfileRequest, HttpServletRequest request) {
        User user = getUser(request);
        if (editProfileRequest.getCnp() != null) {
            user.setCnp(editProfileRequest.getCnp());
        }
        if (editProfileRequest.getNume() != null) {
            user.setNume(editProfileRequest.getNume());
        }
        if (editProfileRequest.getPrenume() != null) {
            user.setPrenume(editProfileRequest.getPrenume());
        }
        if (editProfileRequest.getEmail() != null) {
            user.setEmail(editProfileRequest.getEmail());
        }
        userRepository.save(user);
        return user;
    }

    @PutMapping("/editProfilePicture")
    @PreAuthorize("hasRole('USER')")
    public User editProfilePicture(@RequestParam("file") MultipartFile imageFile, HttpServletRequest request) throws IOException {
        User user = getUser(request);
        user.setProfilePicture(imageFile.getBytes());
        userRepository.save(user);
        return user;
    }

    @PostMapping("/addWorkingDayDetails")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addNewWorkingDay(@RequestBody WorkingDayRequest workingDayRequest, HttpServletRequest request) {

        User user = getUser(request);

        WorkingDays workingDays = new WorkingDays();
        workingDays.setDetails(workingDayRequest.getDetails());
        workingDays.setDate(workingDayRequest.getDate());
        workingDays.setUser(user);

        workingDaysRepository.save(workingDays);

        return ResponseEntity.ok(workingDays);
    }

    @GetMapping("/getPersonalWorkingDaysDetails")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getWorkingDays(HttpServletRequest request) {

        User user = getUser(request);

        Long id = user.getId();

        Collection<WorkingDays> workingDays = workingDaysRepository.findWorkingDaysByUser(id);

        return ResponseEntity.ok(workingDays);
    }

    @GetMapping("/getProject")
    public ResponseEntity<?> getProject(HttpServletRequest request) {
        User user = getUser(request);
        return ResponseEntity.ok(user.getProject());
    }

    @PostMapping("/setProjectCommit")
    public ResponseEntity<?> setProjectCommit(@RequestBody ProjectCommitsRequest projectCommitsRequest, HttpServletRequest request) {
        User user = getUser(request);

        Project project = user.getProject();

        Date date = new Date();

        ProjectCommits projectCommit = new ProjectCommits();
        projectCommit.setCommit(projectCommitsRequest.getCommit());
        projectCommit.setDate(date);
        projectCommit.setProject(project);
        projectCommit.setUser(user);

        projectCommitsRepository.save(projectCommit);

        return ResponseEntity.ok(project);
    }

    @GetMapping("/getProjectCommits")
    public ResponseEntity<?> getProjectCommits(HttpServletRequest request) {

        User user = getUser(request);

        Project project = user.getProject();

        List<ProjectCommits> projectCommits = projectCommitsRepository.findByProject(project);

        return ResponseEntity.ok(projectCommits);

    }

    @PostMapping("/leaveRequest")
    public ResponseEntity<?> leaveRequest(@RequestBody LeaveTypeRequest leaveTypeRequest, HttpServletRequest request) {

        User user = getUser(request);
        LeaveRequest newLeaveRequest = new LeaveRequest();

        Date startDay = leaveTypeRequest.getStartDay();
        Date endDay = leaveTypeRequest.getEndDay();

        newLeaveRequest.setUser(user);
        newLeaveRequest.setStartDate(startDay);
        newLeaveRequest.setEndDate(endDay);


        String leavingType = leaveTypeRequest.getLeavingType();

        //get difference between start and end date in days
        long diff = endDay.getTime() - startDay.getTime();
        long differenceInDays = TimeUnit.MILLISECONDS.toDays(diff) % 365;

        System.out.println("diff in days" + differenceInDays);
        if(differenceInDays > user.getFreeDays()) {
            return ResponseEntity.badRequest().body("You don't have enough vacation days left !");
        }

        if (leavingType.equals("vacation")) {
            newLeaveRequest.setLeaveType(LeaveType.VACATION);
        } else if (leavingType.equals("medical")) {
            newLeaveRequest.setLeaveType(LeaveType.MEDICAL_REASON);
        } else {
            newLeaveRequest.setLeaveType(LeaveType.FAMILY_PROBLEM);
        }

        leaveRequestRepository.save(newLeaveRequest);

        return ResponseEntity.ok("The request was send to your superior!");
    }

    @GetMapping("/getLeavingPermissionRequests")
    public ResponseEntity<?> getLeavingRequests(HttpServletRequest request){

        User user = getUser(request);
        return ResponseEntity.ok(leaveRequestRepository.findByUserOrderByIdDesc(user));
    }


    private User getUser(HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromCookies(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
    }


}
