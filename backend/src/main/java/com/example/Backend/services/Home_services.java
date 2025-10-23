package com.example.Backend.services;

import com.example.Backend.config.Log;
import com.example.Backend.dto.Project_dto;
import com.example.Backend.dto.Response;
import com.example.Backend.models.*;
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

    public Response<Projects> getProject(String projectid){
        try {
            Optional<Projects> project=projectsRepo.findById(projectid);
            Projects projects=project.get();
            Response<Projects> res=new Response();
            res.setMsg(true);
            res.setMessage("sb ok hai");
            res.setData(projects);
            return res;
        }catch (Exception e){
            Response<Projects> res = new Response<>();
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
            Log.log.info("id i s"+userid);
            Optional<User> optuser=user_repo.findById(userid);
            User user=optuser.get();
            Projects projects=new Projects();
            projects.setName(project.getName());
            Member member=new Member();
            member.setId(userid);
            member.setName(user.getName());
            projects.setMembers(member);
            user.setProjetcs(projects);

            projectsRepo.save(projects);
            user_repo.save(user);
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

    public Response<User> searchfriend(String name){
        try{
            Log.log.info("req came to (home_ser.searchfriend)");
            Response<User> res=new Response();
            User user=user_repo.findByName(name);
            Log.log.info(""+user.getId());
            res.setData(user);
            res.setMessage("mil gya");
            res.setMsg(true);
            return res;
        }catch(Exception e){
            Response<User> res = new Response<>();
            res.setMsg(false);
            res.setMessage(e.getMessage());
            Log.log.error(e.getMessage());
            return res;
        }
    }

    //may add request system

    public Response<ArrayList<String>> addfriend(String userid,String frienndName){
        try {
            Response<ArrayList<String>> res = new Response<>();

            User friend=user_repo.findByName(frienndName);
            ArrayList<String> friendlist=friend.getFriends();
            if(friendlist.contains(userid)){
                res.setMsg(true);
                res.setMessage("dost already");
            }else{
                friend.setFriends(userid);
                user_repo.save(friend);

            }
            Optional<User> optuser=user_repo.findById(userid);
            User user= optuser.get();;
            ArrayList<String> userlist=user.getFriends();
            if(userlist.contains(frienndName)){
                res.setMsg(true);
                res.setMessage("dost already");
            }else{
                user.setFriends(frienndName);
                user_repo.save(user);
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

    public Response<Task> addtodo(String ProjectId,String TodoName){
        try {
            Log.log.info("req came to (home_ser.addtodo");
            Response<Task> res=new Response();
            Optional<Projects> projects=projectsRepo.findById(ProjectId);
            Projects project=projects.get();
            project.genTodo(TodoName);
            projectsRepo.save(project);
            res.setMsg(true);
            res.setData(project.getTodo().getLast());
            res.setMessage("todo bn gya");
            return res;
        }catch(Exception e){
            Response<Task> res = new Response<>();
            res.setMsg(false);
            res.setMessage(e.getMessage());
            Log.log.error(e.getMessage());
            return res;
        }
    }

    public Response<Projects> changeArea(String ProjectId,Task todo,String TodoId,String drop,String DragStart){
        try{
            Log.log.info("req came to (home_ser.changeArea");
            Response<Projects> res=new Response();
            Optional<Projects> projects = projectsRepo.findById(ProjectId);
            Projects project=projects.get();

            if(DragStart.equals("todo")){
                project.removeTodo(TodoId);
                if(drop.equals("inprogress")){project.tempProg(todo);}
                if(drop.equals("done")){project.tempDone(todo);}
            } else if (DragStart.equals("inprogress")) {
                project.removeProg(TodoId);
                if(drop.equals("todo")){project.tempTodo(todo);}
                if(drop.equals("done")){project.tempDone(todo);}
            } else if (DragStart.equals("done")) {
                project.removeDone(TodoId);
                if(drop.equals("todo")){project.tempTodo(todo);}
                if(drop.equals("inprogress")){project.tempProg(todo);}
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

    public Response add_member(String ProjectId,String MemberName,String MemberId,String zone){
        try {
            Log.log.info("req came to (home_ser.add_member");
            Optional<Projects> projects=projectsRepo.findById(ProjectId);
            Projects project=projects.get();
            Member member=new Member();
            member.setId(MemberId);
            member.setName(MemberName);
            project.setMembers(member);
            Response res=new Response();
            res.setMsg(true);
            res.setData(project);
            res.setMessage("member added");
            return res;
        }catch (Exception e){
            Response res = new Response();
            res.setMsg(false);
            res.setMessage(e.getMessage());
            Log.log.error(e.getMessage());
            return res;
        }
    }

    public Response<Projects> Add_AssignMember(String ProjectId,String TodoId,String MemberName ,String MemberId){
        Log.log.info("req came to (home_ser.Add_AssignMember");
        try{
            Response<Projects> res=new Response<Projects>();
            Optional<Projects> projects=projectsRepo.findById(ProjectId);
            Projects project=projects.get();
            for(Member mem:project.getMembers()){
                if(mem.getId().equals(MemberId)){
                    ArrayList<Task>todos=project.getTodo();
                    ArrayList<Task>InProg=project.getProg();
                    ArrayList<Task>Done=project.getDone();
                    Member member=new Member();
                    member.setId(MemberId);
                    member.setName(MemberName);
                    Log.log.info(""+ProjectId+" "+TodoId+" "+MemberName+" "+MemberId);
                    for(Task task:todos){
                        if(TodoId.equals(task.getId().toString())){
                            if(task.getMembers().contains(member)){}else{
                                task.addMember(member);}
                        }
                    }
                    for(Task task:InProg){
                        if(TodoId.equals(task.getId().toString())){
                            if(task.getMembers().contains(member)){}else{
                                task.addMember(member);}
                        }
                    }
                    for(Task task:Done){
                        if(TodoId.equals(task.getId().toString())){
                            if(task.getMembers().contains(member)){}else{
                                task.addMember(member);}
                        }
                    }
                    projectsRepo.save(project);
                    res.setMsg(true);
                    res.setData(project);
                    res.setMessage("assign members");
                    return res;

                }
            }
            res.setMsg(true);
            res.setData(project);
            res.setMessage("not members");
            return res;
        }catch (Exception e){
            Response<Projects> res = new Response<Projects>();
            res.setMsg(false);
            res.setMessage(e.getMessage());
            Log.log.error(e.getMessage());
            return res;
        }
    }

    public Response DeleteTodo(Task todo,String ProjectId,String zone){
        try{
            Log.log.info("req came to (home_ser.DeleteTodo");
            Optional<Projects> projects=projectsRepo.findById(ProjectId);
            Projects project=projects.get();
            if(zone.equals("todo")){project.removeTodo(todo.getId());}
            if (zone.equals("inprogress")) {project.removeProg(todo.getId());}
            if (zone.equals("done")) {project.removeDone(todo.getId());}
            Response res=new Response();
            projectsRepo.save(project);
            res.setMsg(true);
            res.setData(project);
            res.setMessage("todo ta ta ");
            return res;
        }catch(Exception e){
            Response res=new Response();
            res.setMsg(false);
            res.setMessage(e.getMessage());
            Log.log.error(e.getMessage());
            return res;
        }
    }

    public Response AddNote(String note ,String projectid,Task task,String userid){
        Log.log.info("req came to (home_ser.AddNote");
        try {
            Optional<Projects> projects=projectsRepo.findById(projectid);
            Optional<User> optionalUser=user_repo.findById(userid);
            User user= optionalUser.get();
            Projects project=projects.get();
            Note note1=new Note(note, user.getName(),userid);
            for(Task task1:project.getTodo()){
                if(task1.getId().equals(task.getId().toString())){
                    task1.AddNote(note1);
                }
            }
            for(Task task2:project.getProg()){
                if(task2.getId().equals(task.getId().toString())){
                    task2.AddNote(note1);
                }
            }
            for (Task task3 : project.getDone()) {
                if(task3.getId().equals(task.getId().toString())){
                    task3.AddNote(note1);
                }
            }
            Response res=new Response();
            projectsRepo.save(project);
            res.setMsg(true);
            res.setData(project);
            res.setMessage("note ok ");
            return res;
        }catch (Exception e){
            Response<Projects> res = new Response<>();
            res.setMsg(false);
            res.setMessage(e.getMessage());
            Log.log.error(e.getMessage());
            return res;
        }
    }
}
