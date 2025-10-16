package com.example.Backend.models;

import org.springframework.data.annotation.Id;
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

    public void genTodo(String todo){
     Task task = new Task();
     task.setName(todo);
     task.genId();
     this.todo.add(task);
    }

    public void setTodo(String todo,String todoid) {
        Task task=new Task();
        task.setName(todo);
        task.setId(todoid);
        this.todo.add(task);
    }
    public void setProg(String prog,String todoid) {
        Task task=new Task();
        task.setName(prog);
        task.setId(todoid);
        this.prog.add(task);
    }
    public void setDone(String done,String todoid) {
        Task task=new Task();
        task.setName(done);
        task.setId(todoid);
        this.done.add(task);
    }
    public void removeTodo(String todoid) {
        this.todo.removeIf(task -> task.getId().equals(todoid));
    }

    public void removeProg(String progId) {
        this.prog.removeIf(task -> task.getId().equals(progId));
    }

    public void removeDone(String doneId) {
        this.done.removeIf(task -> task.getId().equals(doneId));
    }

    public void tempTodo(Task todo){
        this.todo.add(todo);
    }
    public void tempProg(Task todo){
        this.prog.add(todo);
    }
    public void tempDone(Task todo){
        this.done.add(todo);
    }
}
