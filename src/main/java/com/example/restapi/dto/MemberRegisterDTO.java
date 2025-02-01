package com.example.restapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterDTO {

    @NotBlank(message = "아이디는 공백이 아니어야 합니다.")
    @Size(max = 20, message = "아이디는 5자 이상, 20자 이하로 입력해주세요.")
    private String mid;
    @NotBlank(message = "패스워드는 공백이 아니여야 합니다")
    @Size(max = 20, message = "비밀번호 최대 길이 20자 입니다")
    private String pwd;
    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;
    @NotBlank(message = "이름이 공백이 아니여야 합니다")
    private String name;
}
