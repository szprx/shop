package com.dglazewski.shop.api.dto.creator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Getter
@SuperBuilder
public class CustomerCreatorDto extends UserCreatorDto {
    private String name;
    private String secondName;

}
