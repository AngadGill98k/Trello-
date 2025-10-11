package com.example.Backend.controllers;

import com.example.Backend.config.Jwt;
import com.example.Backend.config.Log;
import com.example.Backend.dto.Project_dto;
import com.example.Backend.dto.Response;
import com.example.Backend.models.Projects;
import com.example.Backend.services.Home_services;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/")
public class Home {
    private Home_services homeServices;
    private Jwt jwt;

    Home(Home_services homeServices,Jwt jwt) {
        this.homeServices = homeServices;
        this.jwt = jwt;
    }

    @GetMapping("/get_projects")
    public Response<ArrayList<Projects>> getProjects(@CookieValue(value = "token")String refresh_token) {
        String userid= jwt.extract_token(refresh_token);
        Log.log.info("req came in (home_con.getproj)");
        Response<ArrayList<Projects>> res=homeServices.getprojects(userid);
        return res;
    }

    @PostMapping("/add_projects")
    public Response<Projects> addproject(@CookieValue (value = "token")String refresh_token, @RequestParam Project_dto project){
        String userid= jwt.extract_token(refresh_token);
        Log.log.info("req came in (home_con.addproj)");
        Response<Projects> res=homeServices.addproject(userid,project);
        return res;
    }

}
