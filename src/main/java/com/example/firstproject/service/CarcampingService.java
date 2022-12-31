package com.example.firstproject.service;


import com.example.firstproject.entity.Place;
import com.example.firstproject.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class CarcampingService {

    @Autowired
    private PlaceRepository placeRepository;


    //게시글 리스트 처리
    public Page<Place> stateList(Pageable pageable){

        return placeRepository.findAll(pageable);
    }

//    public Page<Place> stateSearchList(String searchKeyword, Pageable pageable){
//        return placeRepository.findByThemeContaining(searchKeyword, pageable);
//    }
    public Page<Place> stateSearchList2(String searchKeyword2, Pageable pageable){
        return placeRepository.findByStateContaining(searchKeyword2, pageable);
    }
    //특정 게시글 불러오기
    public Place stateView(Long id){

        return placeRepository.findById(id).get();
    }

    //특정 게시글 삭제

    public void stateDelete(Long id){
        placeRepository.deleteById(id);
    }
}