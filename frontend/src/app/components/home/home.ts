import { Component, effect, OnInit, Signal } from '@angular/core';
import { Navbar } from "../navbar/navbar";
import { Projects } from "../projects/projects";
import { Search } from "../search/search";
import { Request } from "../request/request";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Service } from '../service';
import { Workspace } from "../workspace/workspace";
import { TaskModal } from "../../task-modal/task-modal";
import { Socket } from '../../socket';

@Component({
  selector: 'app-home',
  imports: [Navbar, Projects, Search, Request, CommonModule, FormsModule, Workspace, TaskModal],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home implements OnInit{
  ngOnInit(): void {
      
      this.socket.connect()
  }

  constructor(protected service:Service,private socket:Socket){
    effect(()=>{
    })
  }
  
  active_btn: string = "Projects";
}
