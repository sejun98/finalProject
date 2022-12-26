package com.example.firstproject.controller;

import com.example.firstproject.DTO.ArticleForm;
import com.example.firstproject.DTO.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.Getter;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j // 로깅을 위한 골뱅이(어노테이션)
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String articlesNew () {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form, Long id) {
        // form에서 보낸 값이 DTO로 보내줬고 1.번 처럼 DTo를 Entity로 변환한다음 repository에
        log.info(form.toString()); // 값이 DTO로 담겨졌고
        // 1. DTO -> entity로 변환
        Article article = form.toEntity();
        log.info(article.toString()); // DTO가 Entity로 변환되었으며
        // 2. Repository에게 Entity를 DB안에 저장
        Article saved = articleRepository.save(article);
        log.info(saved.toString()); // 위 Entity를 Repsoitory에게 저장하라고 시켰더니 결과가 되는 Entity가 반환이 되었다.
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}") // {id}값을 받았을때 변동가능 아래 @와 id와 같이쓰a
    public String show(@PathVariable Long id, Model model) {
        log.info("id=" + id);
        // 1 : id로 데이터를 가져옴
        Article articeEntity = articleRepository.findById(id).orElse(null); // 아이디값을 찾았는데 없으면 Null로 반환해라
        List<CommentDto> commentDtos = commentService.comments(id);
    //        2 : 가져온 데이터를 모델에 등록!
        model.addAttribute("article", articeEntity);
        model.addAttribute("commentDtos", commentDtos);
    //        3 : 보여줄 페이지를 설정!
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1. 모든 아티클을 가져온다
        List<Article> articlesEntityList = articleRepository.findAll();
        // 2. 가져온 아티클 묶음을 뷰로 전달!
        model.addAttribute("articleList", articlesEntityList);
        // 3. 뷰 페이지를 설정!
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터를 가져오기!
        Article articleEntity =  articleRepository.findById(id).orElse(null);

        // 모델에 데이터를 등록
        model.addAttribute("article", articleEntity);

        // 뷰 페이지 설정!
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form ) {
        // 1. db에 저장하기 위해서는 DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();

        // 2. 엔티티를 db로 저장
        // 2-1 : db에 기존 데이터를 가져온다. db에서 가져올때는 리파지터리를 이용한다.
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2 : 기존 데이터에 값을 갱신하다.
        if (target != null) {
            articleRepository.save(articleEntity);
        }

        // 3. 수정 겨로가 페이지로 리다이렉트 한다.
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제요청=--=-=-=-=");
        // 1 : 삭제대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);

        // 2 : 대상을 삭제한다.
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제되었습니다.");
        }

        // 3 : 결과 페이지로 리다이렉트한다!
        return "redirect:/articles";
    }
}
