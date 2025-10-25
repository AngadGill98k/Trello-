package com.example.Backend.controllers;

import com.example.Backend.config.Jwt;
import com.example.Backend.config.Log;
import com.example.Backend.dto.Socket_res;
import com.example.Backend.models.Member;
import com.example.Backend.models.Task;
import com.example.Backend.models.User;
import com.example.Backend.repository.Projects_repo;
import com.example.Backend.repository.User_repo;
import com.example.Backend.services.Socket_ser;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Controller
public class Socket {
    private User_repo user_repo;
    private Map<String, Set<Member>> OnlineMem=new ConcurrentHashMap<>();
    private Socket_ser socket_services;
    private Jwt jwt;
    Socket (Socket_ser socket_services,Jwt jwt,User_repo user_repo){
        this.socket_services = socket_services;
        this.jwt = jwt;
        this.user_repo = user_repo;
    }

    @MessageMapping("/add_todo/{roomid}")
    @SendTo("/client/room/{roomid}")
    public Socket_res addtodo(@DestinationVariable String roomid, Socket_res res){
        return res;
    }


    @MessageMapping("/drop_todo/{roomid}")
    @SendTo("/client/room/{roomid}")
    public Socket_res droptodo(@DestinationVariable String roomid, Socket_res res){
        Log.log.info("socket.droptodo");
        return res;
    }


    @MessageMapping("/delete_todo/{roomid}")
    @SendTo("/client/room/{roomid}")
    public Socket_res delete_todo(@DestinationVariable String roomid, Socket_res res){
        Log.log.info("socket.delete_todo");
        return res;
    }


    @MessageMapping("/online/{roomid}")
    @SendTo("/client/room/{roomid}")
    public Socket_res online(@DestinationVariable String roomid, Socket_res res,@Header("Authorization") String authHeader){
        Log.log.info("socket.online");
        String token = authHeader.replace("Bearer ", "");

        // validate JWT and extract userId
        String userId = jwt.extract_token(token);
        Optional<User> optuser=user_repo.findById(userId);
        User user=optuser.get();
        Member member=new Member();
        member.setId(userId);
        member.setName(user.getName());

        Set<Member> members = OnlineMem.computeIfAbsent(roomid, k -> ConcurrentHashMap.newKeySet());

        // Only add if user is not already in the set
        boolean alreadyExists = members.stream().anyMatch(m -> m.getId().equals(userId));
        if (!alreadyExists) {
            members.add(member);
        }

        res.setMembers(members);
        return res;
    }

    @MessageMapping("/assign_member/{roomid}")
    @SendTo("/client/room/{roomid}")
    public Socket_res assign_member(@DestinationVariable String roomid, Socket_res res){
        Log.log.info("socket.assign_member");
        return res;
    }

    @MessageMapping("/add_note/{roomid}")
    @SendTo("/client/room/{roomid}")
    public Socket_res add_note(@DestinationVariable String roomid, Socket_res res){
        Log.log.info("socket.add_note");
        return res;
    }
}
