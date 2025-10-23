import { Component, effect, OnInit, signal, WritableSignal } from '@angular/core';
import { Service } from '../components/service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-task-modal',
  imports: [CommonModule,FormsModule],
  templateUrl: './task-modal.html',
  styleUrl: './task-modal.scss'
})
export class TaskModal implements OnInit {
  todo: any;
  AlreadyAssigned = signal<any[]>([]);
  Assign = signal<any[]>([]);
  toggle: boolean = true
  note: string = ""
  notes: any[] = []
  constructor(protected service: Service) {
    effect(() => {
      this.todo = this.service.ModalInfo();
      this.notes = this.service.ModalInfo()?.note
      this.AlreadyAssigned.set(this.todo.members);
      this.Assign.set(this.service.CurrentProject()?.members?.filter((member: any) => !this.AlreadyAssigned()?.includes(member)) || []);
    })
  }

  ngOnInit() {

  }

  CloseModal() {
    this.toggle = true
    this.service.closeModal();
  }

  AssignTask(member: any) {


    fetch(`http://localhost:8080/AssignMember`, {
      method: "POST",
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.service.access_token}`,
        'X-CSRF-TOKEN': this.service.get_csrf() || ""

      },
      body: JSON.stringify({
        id: this.service.CurrentProject()?.id,
        task: this.todo,
        members: member,
      }),
      credentials: 'include'
    })
      .then(res => res.json())
      .then(data => {
        console.log(data);
        if (data.msg) {

          this.AlreadyAssigned.set(this.service.CurrentProject()?.members);
          this.Assign.set(this.service.CurrentProject()?.members?.filter((member: any) => !this.AlreadyAssigned()?.includes(member)) || []);
        }
      })
      .catch(error => {
        console.error(error);
      });
  }

  SendNote() {
    fetch(`http://localhost:8080/addnote`, {
      method: "POST",
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.service.access_token}`,
        'X-CSRF-TOKEN': this.service.get_csrf() || ""
      }
      , body: JSON.stringify({
        id: this.service.CurrentProject()?.id,
        task: this.todo,
        note1: this.note
      }), 
      credentials: 'include'
      })  
      .then(res => res.json())
      .then(data => {
        console.log(data);
        if (data.msg) {

        }
      })
      .catch(error => {
        console.error(error);
      });
  }
}
