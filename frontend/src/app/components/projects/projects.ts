import { CommonModule } from '@angular/common';
import { Component, OnInit, signal, WritableSignal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Service } from '../service';

@Component({
  selector: 'app-projects',
  imports: [FormsModule, CommonModule],
  templateUrl: './projects.html',
  styleUrl: './projects.scss'
})
export class Projects implements OnInit {

  projects_list: WritableSignal<any[]> = signal([]);
  csrftoken: string = "";
  accesstoken: string = "";
  constructor(private service: Service) { }
  ngOnInit(): void {
    this.csrftoken = this.service.get_csrf() || "";
    this.accesstoken = this.service.access_token || "";
    fetch('http://localhost:8080/get_projects', {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'X-CSRF-TOKEN': this.csrftoken,
        'Authorization': `Bearer ${this.accesstoken}`
      },
      credentials: 'include'
    })
      .then(res => res.json())
      .then(data => {
        console.log(data);
        if (data.msg) {
          this.projects_list.set([...data.data]);
          console.log(this.projects_list);
        }
      })
      .catch(error => {
        console.error('Error fetching projects:', error);
      })
  }

  ProjectName: String = "";

  AddProject() {
    console.log("projects.AddProject event", this.ProjectName);
    if (this.ProjectName.length <= 0) { return; }
    console.log(this.accesstoken);
    console.log(this.csrftoken);
    fetch(`http://localhost:8080/add_projects`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'X-CSRF-TOKEN': this.csrftoken,
        'Authorization': `Bearer ${this.accesstoken}`
      },
      body: JSON.stringify({ name: this.ProjectName }),
      credentials: 'include'
    })
      .then(res => res.json())
      .then(data => {
        console.log(data);
        if (data.msg) {
          //add to projects_list with data
          this.projects_list.update(list => [...list, { name: this.ProjectName, id: data.data.id }]);
          this.ProjectName = "";
        }
      })
  }
  OpenProject(project: any) {
    console.log("projects.OpenProject event", project);
    fetch(`http://localhost:8080/get_project`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'X-CSRF-TOKEN': this.csrftoken,
        'Authorization': `Bearer ${this.accesstoken}`
      },
      body: JSON.stringify({ id: project.id }),
      credentials: 'include'
    })
      .then(res => res.json())
      .then(data => {
        console.log(data);
        if (data.msg) {
          this.service.setCurrentProject(data.data);
        }
      }
      )
    //servie or signal adn use eeffect() in teh workspace compoennt 
  }
}
