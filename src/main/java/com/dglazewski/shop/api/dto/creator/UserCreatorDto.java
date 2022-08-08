package com.dglazewski.shop.api.dto.creator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public class UserCreatorDto {
    private String email;
    private String password;
}
