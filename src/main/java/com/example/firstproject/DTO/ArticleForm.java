package com.example.firstproject.DTO;


import com.example.firstproject.entity.Article;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ArticleForm {

    private Long id; // id 필드 추가!
    private String title;
    private String content;

    // dto을 entity로 바꿔주는 메소드
    // Article(entity)에 toEntity라는 메소드를 넣어준다.
    public Article toEntity() {
        return new Article(id, title, content);
    }
}