import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-request',
  imports: [],
  templateUrl: './request.html',
  styleUrl: './request.scss'
})
export class Request implements OnInit {
  requests_list: any[] = [];
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
          //req ,name and requsters id
        }
      })
  }


  Accept(request:any){
    console.log("request.Accept event",request);
    fetch('http://localhost:3000/',{
      method:'POST',
      headers:{
        'Content-Type':'application/json',

      },
      body:JSON.stringify({request}),
      credentials:'include'
    })
    .then(res=>res.json())
    .then(data=>{
      console.log(data);
      if(data.msg){
        //remove from list
        this.requests_list=this.requests_list.filter((req,index)=>{
          if(req._id!=request._id){
            return true;
          }else{
            return false;
          }
        })
      }
    })
  }
  Decline(request:any){
    console.log("request.Decline event",request);
    fetch('http://localhost:3000/',{
      method:'POST',
      headers:{
        'Content-Type':'application/json',

      },
      body:JSON.stringify({request}),
      credentials:'include'
    })
    .then(res=>res.json())
    .then(data=>{
      console.log(data);
      if(data.msg){
        //remove from list
        this.requests_list=this.requests_list.filter((req,index)=>{
          if(req._id!=request._id){
            return true;
          }else{
            return false;
          }
        })
      }
    })
  }
}
