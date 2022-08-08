package com.dglazewski.shop.api.database_response;

import com.dglazewski.shop.api.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DataBaseStatusResponse<T> {

    private Status status;
    private T entity;

    public DataBaseStatusResponse(Status status) {
        this.status = status;
        this.entity = null;
    }
}
