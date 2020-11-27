package com.aipl.store.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.aipl.store.domain.Article;

public interface ArticleService {

	List<Article> findAllArticles();
	
	Page<Article> findArticlesByCriteria(Pageable pageable, Integer priceLow, Integer priceHigh, List<String> sizes,
			List<String> categories, List<String> brands, String search);
	
	
	//Page<Article> findArticlesByCategories(Pageable pageable, List<String> categories);
	
		
	List<Article> findFirstArticles();

	
	Article findArticleById(Long id);
	
	Article saveArticle(Article article);

	void deleteArticleById(Long id);
	
	List<String> getAllSizes();

	List<String> getAllCategories();

	List<String> getAllBrands();
	
	List<String> getAllArticleImages();

}
