package com.example.Backend.dto;

import com.example.Backend.models.Task;

import java.util.ArrayList;

public class Project_dto {
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
        task.genId();
        this.todo.add(task);
    }
    public void setProg(String prog) {
        Task task=new Task();
        task.setName(prog);
        task.genId();
        this.prog.add(task);
    }
    public void setDone(String done) {
        Task task=new Task();
        task.setName(done);
        task.genId();
        this.done.add(task);
    }
//    public void removeTodo(String todo) {
//        Task task=new Task();
//        task.setName(todo);
//        task.genId();
//        this.todo.remove(todo);
//    }
//    public void removeProg(String prog) {
//        this.prog.remove(prog);
//    }
//    public void removeDone(String done) {
//        this.done.remove(done);
//    }
}
