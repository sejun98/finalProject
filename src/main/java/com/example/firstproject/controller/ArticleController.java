package com.example.firstproject.controller;

import com.example.firstproject.DTO.CommentDto;
import com.example.firstproject.DTO.PlaceDto;
import com.example.firstproject.entity.Place;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.PlaceRepository;
import com.example.firstproject.repository.UserRepository;
import com.example.firstproject.service.ArticleService;
import com.example.firstproject.service.CarcampingService;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

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


        List<Place> PlaceEntityList = placeRepository.findAll();
        model.addAttribute("PlaceList", PlaceEntityList);

        model.addAttribute("list", searchList);

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
}
