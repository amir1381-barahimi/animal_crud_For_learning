package com.animal.animal.shared;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(name = "response",description = "general animal api response")
public class MyApiResponse {

    private boolean action;
    private String message;
    private Date date;
    private Object result;
}
