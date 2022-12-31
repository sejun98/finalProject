package com.example.firstproject.repository;

import com.example.firstproject.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Page<Place> findByThemeContaining(String searchKeyword, Pageable pageable);
    Page<Place> findByStateContaining(String searchKeyword2, Pageable pageable);

}