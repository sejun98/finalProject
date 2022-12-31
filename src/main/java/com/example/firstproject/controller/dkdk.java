//import com.momenting.book.springboot.domain.posts.Posts;
//import com.momenting.book.springboot.domain.posts.PostsRepository;
//import com.momenting.book.springboot.web.dto.PostsListResponseDto;
//import com.momenting.book.springboot.web.dto.PostsResponseDto;
//import com.momenting.book.springboot.web.dto.PostsSaveRequestDto;
//import com.momenting.book.springboot.web.dto.PostsUpdateRequestDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Service
//public class PostsService {
//
//    private final PostsRepository postsRepository;
//
//    @Transactional(readOnly = true)
//    public List<PostsListResponseDto> findAllDesc() {
//        postsRepository.fil
//        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
//    }
//
//    @Transactional
//    public Long save(PostsSaveRequestDto requestDto) {
//        return postsRepository.save(requestDto.toEntity()).getId();
//    }
//
//    @Transactional
//    public Long update(Long id, PostsUpdateRequestDto requestDto) {
//
//        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + id));
//        posts.update(requestDto.getTitle(), requestDto.getContent());
//
//        return id;
//    }
//
//    @Transactional //<-@@
//    public void  delete(Long id) {
//        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
//        postsRepository.delete(posts); //JpaRepository에서 제공하고 있는 delete 메소드 사용
//    } //<-@@
//
//    public PostsResponseDto findById (Long id) {
//        Posts entity = postsRepository.findById(id).orElseThrow( () -> new IllegalArgumentException("해당 게시글이 없습니다. id =" + id));
//
//        return  new PostsResponseDto(entity);
//    }
//}