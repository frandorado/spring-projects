package com.frandorado.logrequestresponseundertow;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Song {

    private Long id;
    private String name;
    private String author;
}
