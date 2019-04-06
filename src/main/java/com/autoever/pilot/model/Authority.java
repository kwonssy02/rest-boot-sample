package com.autoever.pilot.model;

import lombok.*;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Authority {
    String username;
    String authority;
}
