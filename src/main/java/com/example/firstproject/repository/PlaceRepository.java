package com.example.firstproject.repository;

import com.example.firstproject.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Page<Place> findByStateContaining(String searchKeyword, Pageable pageable);
}
