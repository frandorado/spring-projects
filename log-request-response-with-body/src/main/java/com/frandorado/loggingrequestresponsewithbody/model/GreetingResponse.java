package com.frandorado.loggingrequestresponsewithbody.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
public class GreetingResponse implements Serializable {

    private Long id;
    private String message;
}
