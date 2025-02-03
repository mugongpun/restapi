package com.example.restapi.controller;

import com.example.restapi.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RMemberDTO {
    private String mid;
    private String email;
    private String name;

    public RMemberDTO(MemberDTO memberDTO) {
        this.mid = memberDTO.getMid();
        this.email = memberDTO.getEmail();
        this.name = memberDTO.getName();
    }
}
