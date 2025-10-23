package com.example.Backend.controllers;

import com.example.Backend.config.Jwt;
import com.example.Backend.config.Log;
import com.example.Backend.dto.Project_dto;
import com.example.Backend.dto.Response;
import com.example.Backend.models.*;
import com.example.Backend.services.Home_services;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

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

    @PostMapping("/get_project")
    public Response<Projects> getProject(@CookieValue(value = "token")String refresh_token,@RequestBody Project_dto project) {
        Log.log.info("req came in (home_con.getproj)");
        Response<Projects> res=homeServices.getProject(project.getId());
        return res;
    }

    @PostMapping("/add_projects")
    public Response<Projects> addproject(@CookieValue (value = "token")String refresh_token, @RequestBody Project_dto project){
        String userid= jwt.extract_token(refresh_token);
        Log.log.info("req came in (home_con.addproj)");
        Response<Projects> res=homeServices.addproject(userid,project);
        return res;
    }

    @PostMapping("/search_user")
    public Response<User> SearchUser(@CookieValue(value ="token") String refresh_token, @RequestBody Map<String, String> body) {
        String name = body.get("name");
        Response<User> res=homeServices.searchfriend(name);
        return res;
    }

    @PostMapping("/add_friend")
    public Response<User> AddFriend(@CookieValue(value ="token") String refresh_token, @RequestBody Map<String, String> body) {
        String name = body.get("name");
        String userid= jwt.extract_token(refresh_token);
        Response res=homeServices.addfriend(userid,name);
        return res;
    }

    @PostMapping("/add_todo")
    public Response<Task> AddTodo(@RequestBody Project_dto body){
        String TodoName= body.getName();
        String ProjectId= body.getId();
        Log.log.info("req came in (home_con.addtodo)");

        Response<Task> res=homeServices.addtodo(ProjectId,TodoName);
        return res;
    }

    @PostMapping("/change_todo")
    public Response changetodo(@RequestBody Project_dto body){
        Log.log.info("req came in (home_con.changetodo)");
        String projectid=body.getId();
        String todoid=body.getTask().getId();
        Task todo=body.getTask();
        String drop= body.getTo();
        String zone= body.getFrom();
        Response res=homeServices.changeArea(projectid,todo,todoid,drop,zone);
        return res;
    }

    @PostMapping("/delete_todo")
    public Response deletetodo(@RequestBody Project_dto body){
        Task todo=body.getTask();
        String projectid=body.getId();
        String to= body.getTo();
        Log.log.info(""+projectid + " " + todo.getName() + " " + to);
        Response res=homeServices.DeleteTodo(todo,projectid,to);
        return res;
    }

    @PostMapping("/AssignMember")
    public Response assignmeber(@RequestBody Project_dto body){
        String projectid=body.getId();
        String todoid=body.getTask().getId();
        ArrayList<Member> members=body.getMembers();
        Member member=members.get(0);
        String memberid=member.getId();
        String membername=member.getName();
        Response res=homeServices.Add_AssignMember(projectid,todoid,membername,memberid);
        return res;
    }

    @PostMapping("/addnote")
    public Response addnote(@CookieValue(value = "token")String refresh_token, @RequestBody Project_dto body){
        Log.log.info("req came in (home_con.addnote)");
        String projectid=body.getId();
        Task task =body.getTask();

        String note=body.getNote1();
        String userid= jwt.extract_token(refresh_token);
        Response res=homeServices.AddNote(note,projectid, task,userid);
        return res;
    }
}
