package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Override
    ArrayList<Article> findAll();
}