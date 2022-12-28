package com.example.firstproject.DTO;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Place;
import jdk.jfr.StackTrace;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Getter
@Setter
public class PlaceDto {

    private Long id;

    private String titleplace;

    private String content;

    private String state;

    private String theme;

    private String filename;

    private String filepath;

    private String address;

    private String benefit;

    public Place toEntity() {
        return new Place(id, titleplace, content, state, theme, filename, filepath, address ,benefit);
    }

}
