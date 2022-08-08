package com.dglazewski.shop.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Getter
@SuperBuilder
public class CustomerDto extends UserDto {
    private String name;
    private String secondName;
}
