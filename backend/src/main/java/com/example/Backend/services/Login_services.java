package com.example.Backend.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Backend.config.Jwt;
import com.example.Backend.dto.Login_dto;
import com.example.Backend.dto.Response;
import com.example.Backend.dto.User_dto;
import com.example.Backend.models.User;
import com.example.Backend.repository.User_repo;
@Service
public class Login_services {
    private User_repo user_repo;
    private PasswordEncoder hash;
    private Jwt jwt;
    public Login_services(User_repo User,PasswordEncoder hash,Jwt jwt) {
        this.user_repo = User;
        this.hash = hash;
        this.jwt = jwt;
    }


    public User signup(Login_dto user){
        User new_user=new User();
        new_user.setMail(user.getMail());
        new_user.setName(user.getName());
        String hashed_pass=hash.encode(user.getPass());
        new_user.setPaas(hashed_pass);
        user_repo.save(new_user);
        return new_user;
    }
    public Response<User_dto > signin(Login_dto user){

        Response<User_dto> res=new Response<>();
        String mail=(user.getMail());
        String name=(user.getName());
        User found_user=user_repo.findByMail(mail);
        if(found_user==null){
            res.setMessage(" Invalid mail");
            res.setMsg(false);
            return res;
        }
        boolean hashed_pass=hash.matches(user.getPass(),found_user.getPass());
        if(hashed_pass){
            res.setMsg(true);
            res.setMessage("Signin successful");
            String accessToken= jwt.access_Token(found_user.getId());
            res.setAccess_token(accessToken);
            User_dto temp=new User_dto();
            temp.setMail(found_user.getMail());
            temp.setName(found_user.getName());
            temp.setId(found_user.getId());
            res.setData(temp);

            return res;
        }
        res.setMsg(false);
        res.setMessage("Invalid password");
        return res;
    }
}