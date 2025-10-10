import { Component, } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Service } from '../service';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {
name:String="";
pass:String="";
mail:String="";
toggle:boolean=false;
// static jwt:String="";


constructor(private router:Router,private service:Service){}
Singin(){
  console.log(this.name);
  console.log(this.pass);
  console.log(this.mail);
  fetch('http://localhost:8080/signin', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    credentials: 'include',
    body: JSON.stringify({
      name: this.name,
      mail: this.mail,
      pass: this.pass
    })
  })
  .then(response => response.json())
  .then(async data => {
    console.log(data);
    if(data.msg){
      this.service.set_access_token(data.access_token); 
      await this.service.csrf_token();
      console.log(this.service.access_token,this.service.get_csrf());
      this.router.navigate(['home']);
    }
  })
  .catch(error => {
    console.error(error);
  });
}

Signup(){
  console.log(this.name);
  console.log(this.pass);
  console.log(this.mail);
  fetch('http://localhost:8080/signup', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      name: this.name,
      mail: this.mail,
      pass: this.pass
    })
  })
  .then(response => response.json())
  .then(data => {
    console.log(data);
  })
  .catch(error => {
    console.error(error);
  });
}
}
