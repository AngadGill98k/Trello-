import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Service } from '../service';

@Component({
  selector: 'app-search',
  imports: [CommonModule,FormsModule],
  templateUrl: './search.html',
  styleUrl: './search.scss'
})
export class Search {
  csrfToken:string="";
  accessToken:string=""
  constructor(private service:Service){}
  input:String="";
  results:any={};

  Search(){
    this.csrfToken=this.service.get_csrf()||"";
    this.accessToken=this.service.access_token||"";
    console.log("search.Search",this.input);
    if(this.input.length<=0){return;}
    console.log(typeof this.input)
    if(this.input.length>0){
      let name=this.input;
      fetch('http://localhost:8080/search_user',{
        method:'POST',
        headers:{
          'Content-Type':'application/json',
          'X-CSRF-TOKEN': this.csrfToken,
          'Authorization': `Bearer ${this.accessToken}`
        },
        body: JSON.stringify({ name }),
        credentials:'include'
      })
      .then(res=>res.json())
      .then(data=>{
        console.log(data);
        if(data.msg){
          //show name  id and if frnd
          this.results=data.data;
        }
      })
    }
  }


  SendRequest(result:any){
    console.log("search.SendRequest",result);
    fetch('http://localhost:3000/',{
      method:'POST',
      headers:{
        'Content-Type':'application/json',
      },
      body:JSON.stringify({result}),
      credentials:'include'
    })
    .then(res=>res.json())
    .then(data=>{
      console.log(data);
      if(data.msg){
        
      } 
    })
  }
}
