import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-projects',
  imports: [],
  templateUrl: './projects.html',
  styleUrl: './projects.scss'
})
export class Projects implements OnInit {
  projects_list: any[] = [];
  ngOnInit(): void {
    fetch('http://localhost:3000',{
      method:'GET',
      headers:{
        'Content-Type':'application/json'
      },
      credentials:'include'
    })
    .then(res=>res.json())
    .then(data=>{
      console.log(data);
      if(data.msg){
        //projects name and their _id
      }
    })
    .catch(error=>{
      console.error('Error fetching projects:', error);
    })
  }


  OpenProject(project:any){
    console.log("projects.OpenProject event",project);

    //servie or signal adn use eeffect() in teh workspace compoennt 
  }
}
