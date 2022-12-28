package com.example.firstproject.controller;

import com.example.firstproject.DTO.ArticleForm;
import com.example.firstproject.DTO.CommentDto;
import com.example.firstproject.DTO.PlaceDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Place;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.PlaceRepository;
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

    @GetMapping("/articles/new")
    public String articlesNew () {
        return "articles/new";
    }

    @GetMapping("/index")
    public String index () {
        return "page/index";
    }
    @GetMapping("/loginform")
    public String loginFrom() {
        return "page/loginForm";
    }
    @GetMapping("/joinform")
    public String joinForm() {
        return "page/joinForm";
    }
    // 장소 리스트 저장폼
    @GetMapping("/state/write")
    public String stateWriteForm() {
        return "page/statewrite";
    }

    // 장소 리스트 저장
    @PostMapping("/state/writepro")
    public String stateWritePro(PlaceDto placeDto, Model model, MultipartFile file) throws IOException { //폼에서 날아온게 여기 들어감 전에는 String title 이런식으로 받았는데
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
    public String stateList(String searchKeyword, Model model,
                            @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                            Pageable pageable) {

        Page<Place> list = null;

        if (searchKeyword == null){
            list = carcampingService.stateList(pageable);
        }else {
            list = carcampingService.stateSearchList2(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1 ;
        int startPage = Math.max(nowPage-4,1) ;
        int endPage = Math.min(nowPage + 5,list.getTotalPages());

        List<Place> PlaceEntityList = placeRepository.findAll();
        model.addAttribute("PlaceList", PlaceEntityList);

        model.addAttribute("list",list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "page/stateList";
    }

    // stateView 페이지
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

    // 뷰
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
        // 1. 모든 아티클을 가져온다
        List<Article> articlesEntityList = articleRepository.findAll();
        // 2. 가져온 아티클 묶음을 뷰로 전달!
        model.addAttribute("articleList", articlesEntityList);
        // 3. 뷰 페이지를 설정!
        return "articles/placeList";
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
