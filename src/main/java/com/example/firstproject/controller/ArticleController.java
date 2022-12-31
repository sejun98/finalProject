package com.example.firstproject.controller;

import com.example.firstproject.DTO.ArticleForm;
import com.example.firstproject.DTO.CommentDto;
import com.example.firstproject.DTO.PlaceDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Place;
import com.example.firstproject.entity.User;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.PlaceRepository;
import com.example.firstproject.repository.UserRepository;
import com.example.firstproject.service.ArticleService;
import com.example.firstproject.service.CarcampingService;
import com.example.firstproject.service.CommentService;
import lombok.Getter;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j // 로깅을 위한 골뱅이(어노테이션)
public class ArticleController {

    @Autowired
    private CarcampingService carcampingService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/articles/new")
    public String articlesNew () {
        return "articles/new";
    }

    @GetMapping("/index")
    public String index (Model model) {
        return "page/index";
    }
//    @GetMapping("/loginForm")
//    public String loginFrom() {
//        return "page/loginForm";
//    }
//    @GetMapping("/joinForm")
//    public String joinForm() {
//        return "page/joinForm";
//    }
    // 로그인 회원가입은 -> loginController 로 이동

    // 장소 리스트 저장폼
    @GetMapping("/state/write")
    public String stateWriteForm() {
        return "page/statewrite";
    }

    // 장소 리스트 저장
    @PostMapping("/state/writepro")
    public String stateWritePro(PlaceDto placeDto, Model model, MultipartFile file) throws IOException {
        Place place = placeDto.toEntity();
        Place saved = placeRepository.save(place);

        String projectPath = System.getProperty("user.dir")+"/src/main/resources/static/files";

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);

        file.transferTo(saveFile);

        place.setFilename(fileName);
        place.setFilepath("/files/"+ fileName);

        placeRepository.save(place);


        model.addAttribute("message","글 작성이완료되었습니다");
        model.addAttribute("searchUrl","/articles");
        return "redirect:/state/" + saved.getId();
    }

    // 장소 리스트 페이지
    @GetMapping("/state/list")
    public String stateList(String searchKeyword2, Model model,
                            @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                            Pageable pageable) {

        Page<Place> searchList = null;
        if (searchKeyword2 == null){
            searchList = carcampingService.stateList(pageable);
        }else {
            searchList = carcampingService.stateSearchList2(searchKeyword2, pageable);
        }


//        int nowPage = searchList.getPageable().getPageNumber() + 1 ;
//        int startPage = Math.max(nowPage-4,1) ;
//        int endPage = Math.min(nowPage + 5,searchList.getTotalPages());

        List<Place> PlaceEntityList = placeRepository.findAll();
        model.addAttribute("PlaceList", PlaceEntityList);

        model.addAttribute("list", searchList);
//        model.addAttribute("nowPage",nowPage);
//        model.addAttribute("startPage",startPage);
//        model.addAttribute("endPage",endPage);

        return "page/stateList";
    }

    // stateView 페이지 B
    @GetMapping("/state/{id}")
    public String stateShow(@PathVariable Long id, Model model) {
        Place place = placeRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);
        model.addAttribute("place", place);
        model.addAttribute("commentDtos", commentDtos);
        return "page/stateView";
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // 생성
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form, Long id) {
        log.info(form.toString()); // 값이 DTO로 담겨졌고
        Article article = form.toEntity();
        Article saved = articleRepository.save(article);
        return "redirect:/articles/" + saved.getId();
    }

    // 뷰 B
    @GetMapping("/articles/{id}") // {id}값을 받았을때 변동가능 아래 @와 id와 같이쓰a
    public String show(@PathVariable Long id, Model model) {
        Article articeEntity = articleRepository.findById(id).orElse(null); // 아이디값을 찾았는데 없으면 Null로 반환해라
        List<CommentDto> commentDtos = commentService.comments(id);
        model.addAttribute("article", articeEntity);
        model.addAttribute("commentDtos", commentDtos);
        return "articles/show";
    }
    // 리스트
    @GetMapping("/articles")
    public String placeList(Model model) {
        List<Article> articlesEntityList = articleRepository.findAll();
        model.addAttribute("articleList", articlesEntityList);
        return "articles/placeList";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Article articleEntity =  articleRepository.findById(id).orElse(null);

        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form ) {
        Article articleEntity = form.toEntity();

        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        if (target != null) {
            articleRepository.save(articleEntity);
        }

        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제요청=--=-=-=-=");
        Article target = articleRepository.findById(id).orElse(null);

        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제되었습니다.");
        }

        return "redirect:/articles";
    }
}
