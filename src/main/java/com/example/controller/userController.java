package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class userController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> loginDto) {
        System.out.println("1112222222222222");
        String username = (String) loginDto.get("username");
        String password = (String) loginDto.get("password");
        System.out.println(username);
        String sql = "SELECT * FROM user WHERE userName = ? AND userPassword = ?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, username, password);
        JSONObject obj = new JSONObject();
        if (list.isEmpty()){
            return ResponseEntity.status(401).body("Authentication failed");
        } else{
            String token = jwtTokenUtil.generateToken(list.get(0).get("id").toString());
            System.out.println(token);
            Map<String, Object> firstMap = list.get(0);
            Object value = firstMap.get("id");
            obj.put("jwt", token);
            obj.put("userId", value.toString());
        }
        return ResponseEntity.ok(obj);
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> registerDto) {

        String username = (String) registerDto.get("username");
        String password = (String) registerDto.get("password");
        String sql = "SELECT * FROM user WHERE userName = ?";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, username);
        JSONObject obj = new JSONObject();
        if (list.isEmpty()){
            String insertSql = "INSERT INTO user (userName, userPassword) VALUES (?, ?)";
            jdbcTemplate.update(insertSql, username, password);
            obj.put("message", "success");
        } else{
            obj.put("message", "error");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(obj);
        }
        return ResponseEntity.ok(obj);
    }
}
