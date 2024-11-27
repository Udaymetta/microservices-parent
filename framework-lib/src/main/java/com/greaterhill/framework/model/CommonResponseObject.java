package com.greaterhill.framework.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommonResponseObject {

    private String status;
    private String message;
}
