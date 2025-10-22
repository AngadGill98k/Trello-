import { CommonModule } from '@angular/common';
import { Component, effect, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Service } from '../service';

@Component({
  selector: 'app-workspace',
  imports: [CommonModule,FormsModule],
  templateUrl: './workspace.html',
  styleUrl: './workspace.scss'
})
export class Workspace implements OnInit {
  todos: any[] = [];
  inprogress: any[] = [];
  done: any[] = [];
  projectid: String = "";
  
  csrftoken: string = "";
  accesstoken: string = "";
  constructor(private service:Service){
    //effetc() to litn to teh signal
    effect(()=>{
    this.projectid=this.service.CurrentProject()?.id||"";
    this.todos=this.service.CurrentProject()?.todo||[];
    this.inprogress=this.service.CurrentProject()?.prog||[];
    this.done=this.service.CurrentProject()?.done||[];
    console.log("workspace.ngOnInit",this.projectid,this.todos,this.inprogress,this.done);
    })
  }
  ngOnInit(): void {
    this.csrftoken=this.service.get_csrf()||"";
    this.accesstoken=this.service.access_token||"";  
  }
  newTask:String="";
  AddTask(){
    console.log("workspace.AddTask",this.projectid,this.newTask);
    if(this.newTask.length<=0){return;}
    
    fetch('http://localhost:8080/add_todo',{
      method:'POST',
      headers:{ 
        'Content-Type':'application/json',
        'X-CSRF-TOKEN': this.csrftoken,
        'Authorization': `Bearer ${this.accesstoken}`
      },
      body:JSON.stringify({name:this.newTask,id:this.projectid}),
      credentials:'include'
    })
    .then(res=>res.json())
    .then(data=>{
      console.log(data);
      if(data.msg){
        this.service.CurrentProject.update(project=>({
          ...project,
          todo:[...project.todo,data.data]
        }))
      }
    })
    
  }


  friend:String="";
  Addpeople(){
    console.log("workspace.Addpeople",this.friend);
    if(this.friend.length<=0){return;}
    fetch('http://localhost:3000',{
      method:'POST',
      headers:{
        'Content-Type':'application/json',
      },
      body:JSON.stringify({friend:{friend:this.friend,projectid:this.projectid}}),
      credentials:'include'
    })
    .then(res=>res.json())
    .then(data=>{
      console.log(data);
      if(data.msg){
        //show in list of people
        this.friend="";
      }
    })
  }

  //drag and ropn usin angular cdk or maunal moseup down adn drop
  dragStart(event:DragEvent, todo:any,zone:String) {
    console.log("dragStart",event,todo);
    event.dataTransfer?.setData("text/plain", JSON.stringify({todo,zone}));
    (event.currentTarget as HTMLElement).style.opacity = '0.5';
  }
  dragEnd(event:DragEvent) {
    (event.currentTarget as HTMLElement).style.opacity = '1';
  }
  dragOver(event:DragEvent,zone:String) {
    event.preventDefault(); 
    if(zone==='todo'){
    (event.currentTarget as HTMLElement).style.background = 'rgba(221, 233, 221, 0.42)';
    }
    if(zone==='inprogress'){
    (event.currentTarget as HTMLElement).style.background = '#ffffe0';
    }
    if(zone==='done'){
    (event.currentTarget as HTMLElement).style.background = ' rgba(149, 245, 152, 1)';
    }
  }
  dragLeave(event:DragEvent) {
    (event.currentTarget as HTMLElement).style.background = '';
  }
  drop(event: DragEvent, listType: string) {
  event.preventDefault();
  (event.currentTarget as HTMLElement).style.background = '';
  const data = JSON.parse(event.dataTransfer?.getData("text/plain") || '{}');
  console.log("drop", event, data, listType);
  const dragged = data.todo;
  const from = data.zone;
  const to = listType;
  this.FetchChange(from, to, dragged);
  if (from === 'todo') this.todos = this.todos.filter(t => t.id !== dragged.id);
  if (from === 'inprogress') this.inprogress = this.inprogress.filter(t => t.id !== dragged.id);
  if (from === 'done') this.done = this.done.filter(t => t.id !== dragged.id);
  if (to === 'todo') this.todos = [...this.todos, dragged];
  if (to === 'inprogress') this.inprogress = [...this.inprogress, dragged];
  if (to === 'done') this.done = [...this.done, dragged];
  this.service.CurrentProject.update(project => ({
    ...project,
    todo: this.todos,
    prog: this.inprogress,
    done: this.done
  }));
  }



  FetchChange(zone:String,drop:String,todo:any){
    console.log(todo,zone,drop);
    
    fetch('http://localhost:8080/change_todo',{
      method:'POST',
      headers:{
        'Content-Type':'application/json',
        'X-CSRF-TOKEN': this.csrftoken,
        'Authorization': `Bearer ${this.accesstoken}`
      },
      body:JSON.stringify({from:zone,to:drop,task:todo,id:this.projectid}),
      credentials:'include'
    })
    .then(res=>res.json())
    .then(data=>{
      console.log(data);
      if(data.msg){
        //update the local lists if needed
      }
    })
  }


  DeleteTodo(todo:any,zone:String){
    console.log("workspace.DeleteTodo",zone,todo);
    fetch('http://localhost:8080/delete_todo',{
      method:'POST',
      headers:{
        'Content-Type':'application/json',
        'X-CSRF-TOKEN': this.csrftoken,
        'Authorization': `Bearer ${this.accesstoken}`
      },
      body:JSON.stringify({task:todo,id:this.projectid,to:zone}),
      credentials:'include'
    })
    .then(res=>res.json())
    .then(data=>{
      console.log(data);
      if(data.msg){
        if(zone==='todo'){
          this.todos=this.todos.filter(item=>item.id!==todo.id);
          this.service.CurrentProject.update((project)=>{
            return{
              ...project,
              todo:this.todos,
              prog:this.inprogress,
              done:this.done
            }
          })
        }
        if(zone==='inprogress'){
          this.inprogress=this.inprogress.filter(item=>item.id!==todo.id);
          this.service.CurrentProject.update((project)=>{
            return{
              ...project,
              todo:this.todos,
              prog:this.inprogress,
              done:this.done
            }
          })
        }
        if(zone==='done'){
          this.done=this.done.filter(item=>item.id!==todo.id);
          this.service.CurrentProject.update((project)=>{
            return{
              ...project,
              todo:this.todos,
              prog:this.inprogress,
              done:this.done
            }
          })
        }
      }})
  }


  Assignment(todo:any){

  }
  OpenModal(todo:any){
    this.service.showModal(todo);
  }  
}
