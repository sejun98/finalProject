package com.example.firstproject.api;

import com.example.firstproject.DTO.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@Slf4j
public class ArticleApiController {

    @Autowired
    private ArticleService articleService;

    // GET
//    @GetMapping("/api/articles")
//    public List<Article> index() {
//        return articleRepository.findAll();
//    }
    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleService.index();
    }

//    @GetMapping("/api/articles/{id}")
//    public Article index(@PathVariable Long id) {
//        return articleRepository.findById(id).orElse(null);
//    }
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

//    // POST
//    @PostMapping("/api/articles")
//    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
//        Article saved = articleRepository.save(dto.toEntity());
//        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//    }
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto) {
        Article created = articleService.create(dto);
        return (created != null) ? ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        Article updated =  articleService.update(id,dto);
        return (updated != null) ? ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // PATCH
//    @PatchMapping("/api/articles/{id}")
//    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto) {
        // 1. 수정용 엔티티 생성
//        Article article = dto.toEntity();
//        log.info("id : {}, article : {}, ", id, article.toString());
//        // 2. 대상 엔티티를 조회
//        Article target = articleRepository.findById(id).orElse(null);
//        // 3. 잘못된 데이터 처리
//        if (target == null || id != article.getId()) {
//            // 잘못된 요청을 응답!(400) 보내기
//            log.info("잘못된 요청! id:{}, aricle : {}", id, article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//            // 잘못된 요청이 들어왔을때는 ResponseEntity.status(HttpStatus.BAD_REQUEST) <- 400번 .body(null)을 보내준다.
//        }
//        // 4. 업데이트 및 정상 응답(200)
//        target.patch(article); // 새로 입력한 데이터가 null일 경우 기존 데이터를 삽입하는 메소드 추가
//        Article updated =  articleRepository.save(target);
//        return ResponseEntity.status(HttpStatus.OK).body(updated);
//    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null) ? ResponseEntity.status(HttpStatus.OK).body(deleted) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
    //    @DeleteMapping("/api/articles/{id}")
//    public ResponseEntity<Article> delete(@PathVariable Long id) {
//        Article target = articleRepository.findById(id).orElse(null);
//        if (target == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//        articleRepository.delete(target);
//        return ResponseEntity.status(HttpStatus.OK).build();
//    }

