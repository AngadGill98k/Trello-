import { Component, computed, effect, OnInit, signal, WritableSignal } from '@angular/core';
import { Service } from '../components/service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Socket } from '../socket';

@Component({
  selector: 'app-task-modal',
  imports: [CommonModule, FormsModule],
  templateUrl: './task-modal.html',
  styleUrl: './task-modal.scss'
})
export class TaskModal implements OnInit {
  todo = computed(() => {
    console.log(this.service.ModalInfo());
    return (this.service.ModalInfo())
  });
  AlreadyAssigned = computed(() => { return this.todo()?.members || [] })
  Assign = computed(() => {
    let assigned = this.AlreadyAssigned();
    let projectMembers = this.service.CurrentProject()?.members ?? [];
    return projectMembers.filter((member: any) => {
      
      return !assigned.some((a: any) => a.id === member.id);
    });
  });
  toggle: boolean = true
  note: string = ""
  notes = computed(() => this.service.ModalInfo()?.note || []);
  constructor(protected service: Service, private socket: Socket) {
    effect(() => {
      
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
        task: this.todo(),
        members: member,
      }),
      credentials: 'include'
    })
      .then(res => res.json())
      .then(data => {
        console.log(data);
        if (data.msg) {
          this.service.CurrentProject.set({ ...data.data });
          this.socket.AssignMember(this.service.CurrentProject()?.id || "", this.todo, member);
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
        task: this.todo(),
        note1: this.note
      }),
      credentials: 'include'
    })
      .then(res => res.json())
      .then(data => {
        console.log(data);
        if (data.msg) {
          this.service.CurrentProject.set({ ...data.data });
          this.socket.NoteAdd(this.service.CurrentProject()?.id || "", this.todo(), data.note,this.service.TodoSelected()?.zone);
          this.note = ""
        }
      })
      .catch(error => {
        console.error(error);
      });
  }
}
