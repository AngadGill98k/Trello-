import { Injectable } from '@angular/core';
import { Client, IMessage, StompSubscription } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { Service } from './components/service';


@Injectable({
  providedIn: 'root'
})
export class Socket {
  private stompClient: Client;

  constructor(private service: Service) {
    this.stompClient = new Client({
      // Use native WebSocket URL
      brokerURL: 'ws://localhost:8080/ws', // replace with wss:// for SSL
      reconnectDelay: 5000,               // auto reconnect every 5s if connection drops
      debug: (msg) => console.log('[STOMP]', msg) // optional debugging
    });
  }

  connect() {
    this.stompClient.onConnect = () => {
      console.log('✅ Connected to WebSocket');

    };

    this.stompClient.onStompError = (frame) => {
      console.error('❌ Broker error:', frame.headers['message']);
      console.error('Details:', frame.body);
    };

    this.stompClient.activate(); // <-- establishes the WebSocket connection
  }

  sendMessage(msg: string) {
    this.stompClient.publish({
      destination: '/app/send',
      body: msg
    });
  }

  JoinRoom(roomid: String) {
    this.stompClient.subscribe('/client/room/' + roomid, (msg: IMessage) => {
      let data = JSON.parse(msg.body);
      console.log("Received in room " + roomid + ":", data);
      if (data.action == "TodoAdd") {
        this.service.CurrentProject.update((project) => {
          return ({
            ...project,
            todo: [...project.todo, data.task]
          })
        })
      }

      
      if (data.action == "TodoDrop") {
        console.log("Handling TodoDrop:", data);
        if (data.from == "todo") {
          this.service.CurrentProject.update((project) => {
            return ({
              ...project,
              todo: project.todo.filter((item:any) => item.id !== data.task.id),
            })
          })
        }
        if (data.from == "inprogress") {
          this.service.CurrentProject.update((project) => {
            return ({
              ...project,
              inprogress: project.inprogress.filter((item:any) => item.id !== data.task.id),
            })
          })
        }
        if (data.from == "done") {
          this.service.CurrentProject.update((project) => {
            return ({
              ...project,
              done: project.done.filter((item:any) => item.id !== data.task.id),
            })
          })
        }
        if (data.to == "todo") {
          this.service.CurrentProject.update((project) => {
            return ({
              ...project,
              todo: [...project.todo, data.task],
            })
          })
        }
        if (data.to == "inprogress") {
          this.service.CurrentProject.update((project) => {
            return ({
              ...project,
              inprogress: [...project.inprogress, data.task],
            })
          })
        }
        if (data.to == "done") {
          this.service.CurrentProject.update((project) => {
            return ({
              ...project,
              done: [...project.done, data.task],
            })
          })
        }
        
      }


      if(data.action=="TodoDelete"){
        console.log("Handling TodoDelete:", data);
        if(data.from=='todo'){
          this.service.CurrentProject.update((project)=>{
            return{
              ...project,
              todo:project.todo.filter((item:any)=>item.id!==data.task.id),
            }
          })
        }
        if(data.from=='inprogress'){
          this.service.CurrentProject.update((project)=>{
            return{
              ...project,
              prog:project.prog.filter((item:any)=>item.id!==data.task.id),
            }}
          )
        }
        if(data.from=='done'){
          this.service.CurrentProject.update((project)=>{
            return{
              ...project,
              done:project.done.filter((item:any)=>item.id!==data.task.id),
            }}
          )
        }
      }


      if(data.action=="JoinRoom"){
        console.log("A user has joined the room:", data);
        this.service.OnlineMembers.set(data.members)
      }


      if(data.action=="AssignMember"){
        console.log("Handling AssignMember:", data);
        this.service.CurrentProject.update((project)=>{
          let task=data.task;
          let member=data.member;
          let todo=this.service.CurrentProject().todo.map((t:any)=>{
            if(t.id==task.id){
              return{
                ...t,
                members:[...t.members,member]
              }
            }
            return t;
          })
          let prog=this.service.CurrentProject().prog.map((t:any)=>{
            if(t.id==task.id){
              return{
                ...t,
                members:[...t.members,member]
              }
            }
            return t;
          })
          let done=this.service.CurrentProject().done.map((t:any)=>{
            if(t.id==task.id){
              return{
                ...t,
                members:[...t.members,member]
              }
            }
            return t;
          })
          return{
            ...project,
            todo,
            prog,
            done
          }
        })

      }

      if(data.action=="NoteAdd"){
        console.log("Handling NoteAdd:", data);
        if(data.to=="todo"){
          let todo=this.service.CurrentProject().todo.map((t:any)=>{
            if(t.id==data.task.id){
              return{
                ...t,
                note:[...t.note,data.note]
              }
            }
            return t;
          })
          this.service.CurrentProject.update((project)=>{
            return{
              ...project,
              todo
            }
          })
        }
        if(data.to=="inprogress"){
          let prog=this.service.CurrentProject().prog.map((t:any)=>{
            if(t.id==data.task.id){
              return{
                ...t,
                note:[...t.note,data.note]
              }
            }
            return t;
          })
          this.service.CurrentProject.update((project)=>{
            return{
              ...project,
              prog
            }
          })
        }
        if(data.to=="done"){
          let done=this.service.CurrentProject().done.map((t:any)=>{
            if(t.id==data.task.id){
              return{
                ...t,
                note:[...t.note,data.note]
              }
            }
            return t;
          })
          this.service.CurrentProject.update((project)=>{
            return{
              ...project,
              done
            }
          })
        }
      }
    })


    this.stompClient.publish({
      destination: "/app/online/" + roomid,
      body: JSON.stringify({ action: "JoinRoom" }),
      headers: { Authorization: `Bearer ${this.service.access_token}` }
    })
    console.log("Joined room:", roomid);
  }

  LeaveRoom(roomid: String) {
    this.stompClient.unsubscribe('/client/room/' + roomid);
    console.log("Left room:", roomid);
  }

  TodoAdd(roomid: String, task: any) {
    console.log("Sending TodoAdd to room:", roomid, task);
    this.stompClient.publish({
      destination: "/app/add_todo/" + roomid,
      body: JSON.stringify({ task, action: "TodoAdd" })
    })
  }

  TodoDrop(roomid: String, task: any, from: String, to: String) {
    console.log("Sending TodoDrop to room:", roomid, task, from, to);
    this.stompClient.publish({
      destination: "/app/drop_todo/" + roomid,
      body: JSON.stringify({ task, from, to, action: "TodoDrop" })
    })
  }

  TodoDelete(roomid: String, task: any,from:String) {
    console.log("Sending TodoDelete to room:", roomid, task);
    this.stompClient.publish({
      destination: "/app/delete_todo/" + roomid,
      body: JSON.stringify({ task,from, action: "TodoDelete" })
    })
  }

  AssignMember(roomid:String,task:any,member:any){
    console.log("Sending AssignMember to room:", roomid, task, member);
    this.stompClient.publish({
      destination: "/app/assign_member/" + roomid,
      body: JSON.stringify({ task, member, action: "AssignMember" })
    })
  }

  NoteAdd(roomid:String,task:any,note:any,zone:string){
    console.log("Sending NoteAdd to room:", roomid, task, note);
    this.stompClient.publish({
      destination: "/app/add_note/" + roomid,
      body: JSON.stringify({ task, note, action: "NoteAdd",to:zone })
    })
  }
  disconnect() {
    this.stompClient.deactivate();
    console.log('❌ Disconnected');
  }
}
