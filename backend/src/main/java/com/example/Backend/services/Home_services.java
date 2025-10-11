package com.example.Backend.services;

import com.example.Backend.config.Log;
import com.example.Backend.dto.Project_dto;
import com.example.Backend.dto.Response;
import com.example.Backend.models.Projects;
import com.example.Backend.models.Task;
import com.example.Backend.models.User;
import com.example.Backend.repository.Projects_repo;
import com.example.Backend.repository.User_repo;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class Home_services {
    private final User_repo user_repo;
    private Projects_repo projectsRepo;
    public Home_services(Projects_repo projectsRepo, User_repo user_repo) {
        this.projectsRepo = projectsRepo;
        this.user_repo = user_repo;
    }

    public Response<ArrayList<Projects>> getprojects(String userid){
        try {
            Log.log.info("req came to (home_ser.getprojets");
            Response<ArrayList<Projects>> res=new Response();
            Optional<User> User=user_repo.findById(userid);
            User user=User.get();
            ArrayList<Projects> projects=user.getProjects();

            res.setMsg(true);
            res.setMessage("sb ok hai");
            res.setData(projects);
            return res;
        }catch(Exception e) {
            Response<ArrayList<Projects>> res = new Response<>();
            res.setMsg(false);
            res.setMessage(e.getMessage());
            Log.log.error(e.getMessage());
            return res;
        }
    }

    public Response<Projects> addproject(String userid, Project_dto project){
        try{
            Log.log.info("req came to (home_ser.addproject");
            Response<Projects> res=new Response();
            Optional<User> optuser=user_repo.findById(userid);
            User user=optuser.get();
            Projects projects=new Projects();
            projects.setName(project.getName());
            projects.setMembers(userid);
            user.setProjetcs(projects);
            user_repo.save(user);
            projectsRepo.save(projects);
            res.setMessage("project bn gya");
            res.setMsg(true);
            res.setData(projects);
            return res;
        }catch(Exception e){
            Response<Projects> res = new Response<>();
            res.setMsg(false);
            res.setMessage(e.getMessage());
            Log.log.error(e.getMessage());
            return res;
        }
    }

    public Response searchfriend(String name){
        try{
            Log.log.info("req came to (home_ser.searchfriend)");
            Response res=new Response();
            User user=user_repo.findByName(name);

            res.setMessage("dost bn gya");
            res.setMsg(true);
            return res;
        }catch(Exception e){
            Response res = new Response<>();
            res.setMsg(false);
            res.setMessage(e.getMessage());
            Log.log.error(e.getMessage());
            return res;
        }
    }

    //may add request system

    public Response<ArrayList<String>> addfriend(String userid,String friend_identifier){
        try {
            Response<ArrayList<String>> res = new Response<>();
            Optional<User> optuser=user_repo.findById(userid);
            User user= optuser.get();;
            ArrayList<String> userlist=user.getFriends();
            if(userlist.contains(friend_identifier)){
                res.setMsg(true);
                res.setMessage("dost already");
            }else{
                user.setFriends(friend_identifier);
                user_repo.save(user);
            }

            Optional<User> optfriend=user_repo.findById(friend_identifier);
            User friend = optfriend.get();
            ArrayList<String> friendlist=friend.getFriends();
            if(friendlist.contains(userid)){
                res.setMsg(true);
                res.setMessage("dost already");
            }else{
                friend.setFriends(userid);
                user_repo.save(friend);
                res.setMsg(true);
                res.setMessage("dost bn gya");
                res.setData(user.getFriends());
            }

            return res;
        }catch (Exception e){
            Response res = new Response<>();
            res.setMsg(false);
            res.setMessage(e.getMessage());
            Log.log.error(e.getMessage());
            return res;
        }
    }

    public Response<ArrayList<Task>> addtodo(String ProjectId,String TodoName){
        try {
            Log.log.info("req came to (home_ser.addtodo");
            Response<ArrayList<Task>> res=new Response();
            Optional<Projects> projects=projectsRepo.findById(ProjectId);
            Projects project=projects.get();
            project.genTodo(TodoName);
            projectsRepo.save(project);
            res.setMsg(true);
            res.setData(project.getTodo());
            res.setMessage("todo bn gya");
            return res;
        }catch(Exception e){
            Response<ArrayList<Task>> res = new Response<>();
            res.setMsg(false);
            res.setMessage(e.getMessage());
            Log.log.error(e.getMessage());
            return res;
        }
    }

    public Response<Projects> changeArea(String ProjectId,String todo,String TodoId,String drop,String DragStart){
        try{
            Log.log.info("req came to (home_ser.changeArea");
            Response<Projects> res=new Response();
            Optional<Projects> projects = projectsRepo.findById(ProjectId);
            Projects project=projects.get();
            if(DragStart.equals("Todo")){
                project.removeTodo(TodoId);
                if(drop.equals("Prog")){project.setProg(todo,TodoId);}
                if(drop.equals("Done")){project.setDone(todo,TodoId);}
            } else if (DragStart.equals("Prog")) {
                project.removeProg(TodoId);
                if(drop.equals("Todo")){project.setTodo(todo,TodoId);}
                if(drop.equals("Done")){project.setDone(todo,TodoId);}
            } else if (DragStart.equals("Done")) {
                project.removeDone(TodoId);
                if(drop.equals("Todo")){project.setTodo(todo,TodoId);}
                if(drop.equals("Prog")){project.setProg(todo,TodoId);}
            }
            projectsRepo.save(project);
            res.setMsg(true);
            res.setData(project);
            res.setMessage("area chnages");
            return res;
        }catch(Exception e){
            Response<Projects> res = new Response<>();
            res.setMsg(false);
            res.setMessage(e.getMessage());
            Log.log.error(e.getMessage());
            return res;

        }
    }

}
