package com.example.firstproject.service;

import com.example.firstproject.DTO.ArticleForm;
import com.example.firstproject.DTO.PlaceDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Place;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
@Slf4j
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;


    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }


    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if (article.getId() != null) return null; // 기존에 아이디가 있을때 수정되지않기 위한 코드
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        log.info("id : {}, article : {}, ", id, article.toString());
        Article target = articleRepository.findById(id).orElse(null);
        if (target == null || id != article.getId()) {
            log.info("잘못된 요청! id:{}, aricle : {}", id, article.toString());
            return null;
        }
        // 4. 업데이트 및 정상 응답(200)
        target.patch(article); // 새로 입력한 데이터가 null일 경우 기존 데이터를 삽입하는 메소드 추가
        Article updated =  articleRepository.save(target);
        return updated;
    }

    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if (target == null) return null;
        articleRepository.delete(target);
        return target;
    }
}
