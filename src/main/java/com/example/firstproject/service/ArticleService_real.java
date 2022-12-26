//package com.example.firstproject.service;
//
//import com.example.firstproject.DTO.ArticleForm;
//import com.example.firstproject.entity.Article;
//import com.example.firstproject.repository.ArticleRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Slf4j
//@Service // 서비스 선언! (서비스 객체를 스프링부트에 선언!)
//public class ArticleService_real {
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    public List<Article> index() {
//        return  articleRepository.findAll();
//    }
//
//    public Article show(Long id) {
//        return articleRepository.findById(id).orElse(null);
//    }
//
//    // 글작성
//
//    public Article create(ArticleForm dto) {
//        Article article = dto.toEntity();
//        if (article.getId() != null) {
//            return null;
//        }
//        return articleRepository.save(article);
//    }
//
//    // 수정
//    public Article update(Long id, ArticleForm dto) {
//        // 1 : 수정용 엔티티 생성
//        Article article = dto.toEntity();
//
//        // 2 : 대상 엔티티를 조회
//        Article target = articleRepository.findById(id).orElse(null);
//
//        // 3 : 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
//        if (target == null || id != article.getId()) {
//            // 400 보여줘야함
//            return null;
//        }
//        // 4 : 업데이트
//        target.patch(article); // 데이터 일부 수정할때 Null값이 안들어가기위한 메소드 작성(path)
//        Article updated = articleRepository.save(target);
//        return updated;
////        return articleRepository.save(target); : 이렇게 리턴해도됨
//
//    }
//
//    public Article delete(Long id) {
//        // 대상 엔티티 찾기
//        Article target = articleRepository.findById(id).orElse(null);
//        // 잘못된 요청 처리
//        if (target == null) {
//            return null;
//        }
//        // 대상 삭제
//        // 데이터 반환
//        articleRepository.delete(target);
//        return target;
//    }
//    // for문으로 작성시
////        List<Article> articleList = new ArrayList<>();
////        for (int i = 0; i < dtos.size(); i++) {
////            ArticleForm dto = dtos.get(i);
////            Article entity = dto.toEntity();
////            articlesList.add(entity);
////        }
//    @Transactional
//    public List<Article> createArticles(List<ArticleForm> dtos) {
//        // dto 묶음을 entity 묶음으로 변환
//        List<Article> articleList = dtos.stream()
//                .map(dto -> dto.toEntity())
//                .collect(Collectors.toList());
//        // entity 묶음을 DB로 저장
//        articleList.stream()
//                .forEach(article -> articleRepository.save(article));
//        // 강제 예외 발생
//        articleRepository.findById(-1L).orElseThrow(
//                () -> new IllegalArgumentException("결제 실패!")
//        );
//        // 결과값 반환
//        return articleList;
//    }
//}
