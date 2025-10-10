import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-search',
  imports: [CommonModule,FormsModule],
  templateUrl: './search.html',
  styleUrl: './search.scss'
})
export class Search {
  input:String="";
  results:any[]=[];
  Search(){
    console.log("search.Search",this.input);
    if(this.input.length<=0){return;}
    
    if(this.input.length>0){
      fetch('http://localhost:3000',{
        method:'POST',
        headers:{
          'Content-Type':'application/json',
        },
        body:JSON.stringify({input:this.input}),
        credentials:'include'
      })
      .then(res=>res.json())
      .then(data=>{
        console.log(data);
        if(data.msg){
          //show name  id and if frnd
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
