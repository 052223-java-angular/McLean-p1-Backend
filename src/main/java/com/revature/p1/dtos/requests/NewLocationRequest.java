package com.revature.p1.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewLocationRequest {

    private String name;
    private double longitude;
    private double latitude;
    //not sure i need a token here
    //private String token;

}

