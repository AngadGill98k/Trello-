package com.example.Backend.dto;

import com.example.Backend.models.Member;
import com.example.Backend.models.Note;
import com.example.Backend.models.Task;

import java.util.ArrayList;

public class Project_dto {
    private String id;
    private String name;
    private ArrayList<Member> members = new ArrayList<>();

    private ArrayList<Task> todo=new ArrayList<>();
    private ArrayList<Task> prog=new ArrayList<>();
    private ArrayList<Task> done=new ArrayList<>();

    private ArrayList<Note> note=new ArrayList<>();

    private String from;
    private String to;
    private Task task;
    private String note1;

    public String getNote1(){
        return note1;
    }
    public void setNote1(String note1){
        this.note1 = note1;
    }
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public ArrayList<Member> getMembers(){
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
    public String getFrom(){
        return from;
    }
    public String getTo(){
        return to;
    }
    public Task getTask(){
        return task;
    }
    public ArrayList<Note> getNote(){
        return note;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setMembers(Member member) {
        this.members.add(member);
    }

    public void genTodo(String todo){
        Task task = new Task();
        task.setName(todo);
        task.genId();
        this.todo.add(task);
    }
    public void AddNote(Note note){
        this.note.add(note);
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
    public void setFrom(String from) {
        this.from = from;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public void removeTodo(String todoid) {
        ArrayList<Task> todos=this.todo;
        for (Task task:todos) {
            if(task.getId().equals(todoid)) {
                this.todo.remove(task);
            }
        }
    }
    public void setNote(Note note){
        this.note.add(note);
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
    public Note SearchNote(String noteid){
        for(Note note:this.note){
            if(note.getId().equals(noteid)){
                return note;
            }
        }
        return null;
    }
}
