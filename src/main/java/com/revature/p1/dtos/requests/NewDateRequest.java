package com.revature.p1.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewDateRequest {

    private String name;
    private long created_at;
    private double mercury_phase;
    private double venus_phase;
    private double mars_phase;
    private double jupiter_phase;
    private double saturn_phase;
    private double uranus_phase;
    private double neptune_phase;
    private double pluto_phase;
    private String token;

}
