package com.ynv.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

  private String message;
  private Long userId;
  private String accessToken;

}