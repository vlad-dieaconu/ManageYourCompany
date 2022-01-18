package com.vlad.ManageYourCompany.controller;


import com.vlad.ManageYourCompany.controller.payload.EditProfileRequest;
import com.vlad.ManageYourCompany.model.User;
import com.vlad.ManageYourCompany.repositories.UserRepository;
import com.vlad.ManageYourCompany.security.jwt.JwtUtils;
import org.hibernate.EntityMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @PutMapping("/editProfile")
    @PreAuthorize("hasRole('USER')")
    public User editProfile(@RequestBody EditProfileRequest editProfileRequest, HttpServletRequest request) {
        String jwt = jwtUtils.getJwtFromCookies(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        if (editProfileRequest.getCnp() != null) {
            user.setCnp(editProfileRequest.getCnp());
        }
        if (editProfileRequest.getNume() != null) {
            user.setNume(editProfileRequest.getNume());
        }
        if (editProfileRequest.getPrenume() != null){
            user.setPrenume(editProfileRequest.getPrenume());
        }
        if (editProfileRequest.getEmail() != null){
            user.setEmail(editProfileRequest.getEmail());
        }
        userRepository.save(user);
        return user;
    }

    @PutMapping("/editProfilePicture")
    @PreAuthorize("hasRole('USER')")
    public User editProfilePicture(@RequestParam("file") MultipartFile imageFile, HttpServletRequest request) throws IOException {
        String jwt = jwtUtils.getJwtFromCookies(request);
        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        user.setProfilePicture(imageFile.getBytes());
        userRepository.save(user);
        return user;
    }


}
