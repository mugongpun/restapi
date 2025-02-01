package com.example.restapi.dto;

import com.example.restapi.entity.Member;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {

    private String mid;
    private String pwd;
    private String name;
    private String email;
    private LocalDateTime joinDate;
    private LocalDateTime modifiedDate;
    private String role;

    public Map<String, Object> getDataMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("mid", mid);
        dataMap.put("name", name);
        dataMap.put("email", email);
        dataMap.put("role", role);
        return dataMap;
    }

    public MemberDTO(Member memberEntity) {
        this.mid = memberEntity.getMid();
        this.pwd = memberEntity.getPwd();
        this.name = memberEntity.getName();
        this.email = memberEntity.getEmail();
        this.joinDate = memberEntity.getJoinDate();
        this.modifiedDate = memberEntity.getModifiedDate();
        this.role = memberEntity.getRole();
    }
}
