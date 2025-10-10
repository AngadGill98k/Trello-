import { Component } from '@angular/core';
import { Navbar } from "../navbar/navbar";
import { Projects } from "../projects/projects";
import { Search } from "../search/search";
import { Request } from "../request/request";
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home',
  imports: [Navbar, Projects, Search, Request,CommonModule,FormsModule],
  templateUrl: './home.html',
  styleUrl: './home.scss'
})
export class Home {
  active_btn: string = "Projects";
}
