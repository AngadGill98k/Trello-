import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-workspace',
  imports: [CommonModule,FormsModule],
  templateUrl: './workspace.html',
  styleUrl: './workspace.scss'
})
export class Workspace {
  todo: any[] = [];
  inprogress: any[] = [];
  done: any[] = [];
  projectid: String = "";
  constructor(){
    //effetc() to litn to teh signal
  }

  newTask:String="";
  AddTask(){
    console.log("workspace.AddTask",this.newTask);
    if(this.newTask.length<=0){return;}
   
    fetch('http://localhost:3000',{
      method:'POST',
      headers:{ 
        'Content-Type':'application/json',
      },
      body:JSON.stringify({todo:{todo:this.newTask,projectid:this.projectid}}),
      credentials:'include'
    })
    .then(res=>res.json())
    .then(data=>{
      console.log(data);
      if(data.msg){
        //add to todo list and sne ddata with eachtodo id 
        this.todo.push(data);
        this.newTask="";
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
}
