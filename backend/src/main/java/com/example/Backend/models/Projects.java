package com.example.Backend.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "Proects")
public class Projects {
    @Id
    private String id;
    private String name;
    private ArrayList<String> members = new ArrayList<>();

    private ArrayList<Task> todo=new ArrayList<>();
    private ArrayList<Task> prog=new ArrayList<>();
    private ArrayList<Task> done=new ArrayList<>();

    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public ArrayList<String> getMembers(){
        return members;
    }
    public ArrayList<Task> getTodo(){
        return todo;
    }
    public ArrayList<Task> getProg(){
        return prog;
    }
    public ArrayList<Task> getDone(){
        return done;
    }


    public void setName(String name) {
        this.name = name;
    }
    public void setMembers(String member) {
        this.members.add(member);
    }

    public void setTodo(String todo) {
        Task task=new Task();
        task.setName(todo);
        task.setId();
        this.todo.add(task);
    }
    public void setProg(String prog) {
        Task task=new Task();
        task.setName(prog);
        task.setId();
        this.prog.add(task);
    }
    public void setDone(String done) {
        Task task=new Task();
        task.setName(done);
        task.setId();
        this.done.add(task);
    }
//    public void removeTodo(String todo) {
//        Task task=new Task();
//        task.setName(todo);
//        task.setId();
//        this.todo.remove(todo);
//    }
//    public void removeProg(String prog) {
//        this.prog.remove(prog);
//    }
//    public void removeDone(String done) {
//        this.done.remove(done);
//    }
}
