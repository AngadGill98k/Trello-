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
        ArrayList<Task> todos=this.todo;
        for (Task task:todos) {
            if(task.getId().equals(todoid)) {
                this.todo.remove(task);
            }
        }
    }
    public void removeProg(String prog) {
        ArrayList<Task> todos=this.prog;
        for (Task task:todos) {
            if(task.getId().equals(prog)) {
                this.todo.remove(task);
            }
        }
    }
    public void removeDone(String done) {
        ArrayList<Task> todos=this.done;
        for (Task task:todos) {
            if(task.getId().equals(done)) {
                this.todo.remove(task);
            }
        }
    }
}
