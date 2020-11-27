package com.aipl.store.controller;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aipl.store.common.ReadSettingsFile;
import com.aipl.store.domain.Article;
import com.aipl.store.domain.ArticleBuilder;
import com.aipl.store.domain.Brand;
import com.aipl.store.domain.Category;
import com.aipl.store.domain.Size;
import com.aipl.store.service.ArticleService;

@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/add")
	public String addArticle(Model model) {
		Article article = new Article();
		model.addAttribute("article", article);
		model.addAttribute("allSizes", articleService.getAllSizes());
		model.addAttribute("allBrands", articleService.getAllBrands());
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "addArticle";
	}
	
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String addArticlePost(@ModelAttribute("article") Article article, HttpServletRequest request, @RequestParam("heroimage") MultipartFile heroimage ,@RequestParam("moreimages") List<MultipartFile> moreimages) {
		
		if (heroimage.isEmpty()) {
	            System.out.print("Please select a hero image");
	            return "redirect:addArticle";
	        }
		String propdetails = ""; // a-b|c-d
		String[] propList =  article.getTechdetails().toArray( new String[] {} );
		for (int i = 0; i < propList.length; i++) {
		    if(i % 2 == 0) { // index is even
		    	propdetails = propdetails + propList[i] + "-" ;	
		    }
		    else
		    {
		    	propdetails = propdetails + propList[i] + "|" ;	
		    }
		}
	
		System.out.print("Prop details as a string " + propdetails);
		
		   article = getImageNames(heroimage, moreimages,article);
	 
	    		Article newArticle = new ArticleBuilder()
	    				.withTitle(article.getTitle())
	    				.withDescription(article.getDescription())
	    				.withPropdetails(propdetails)
	    				.stockAvailable(article.getStock())
	    				.withPrice(article.getPrice())
	    				.withMRPPrice(article.getMrpPrice())
	    				.withTransferPrice(article.getTransferPrice())
	    				.imageLink(article.getPicture())
	    				.withMoreImages(article.getMoreImageNames())
	    				.sizesAvailable(Arrays.asList(request.getParameter("size").split("\\s*,\\s*")))
	    				.ofCategories(Arrays.asList(request.getParameter("category").split("\\s*,\\s*")))
	    				.ofBrand(Arrays.asList(request.getParameter("brand").split("\\s*,\\s*")))
	    				.build();	
	    		/*	    		 
	    				.withVolume(article.getVolume())
	    				.withWeight(article.getWeight())
	    				.withGstOf(article.getGst())
	    				.withUnitwidth(article.getUnitwidth())
	    				.withUnitlength(article.getUnitlength())
	    				.withUnitdepth(article.getUnitdepth())
	    			
	    		*/
	    		
	    		articleService.saveArticle(newArticle);	
	       
		return "redirect:article-list";
	}
	
public Article getImageNames(MultipartFile heroimage,List<MultipartFile> moreimages,Article article)
{	
	String picNewPath = "";
	 try {

		 if (!heroimage.isEmpty()) {
			 picNewPath = formatImages(heroimage);
			 System.out.println("Hero image Path " + picNewPath);
			if(picNewPath != "")
			 article.setPicture(picNewPath);
		 }
		 
		  List<String> fileNames = new ArrayList<String>();
	        if (null != moreimages && moreimages.size() > 0) 
	        {
	        	
	            for (MultipartFile multipartFile : moreimages) {
	 
	            	 picNewPath = formatImages(multipartFile);
	            	 System.out.println("More image Path " + picNewPath);
	            	 if(picNewPath != "")
	            	 {
	                   fileNames.add(picNewPath);
	                  
	            	 }
	            }
	            
	            article.setMoreImageNames(fileNames);
	        }
       
     } catch (Exception e) {
    	 System.out.print("Error in getImageNames " + e.getMessage());
     }
	
	return article;
}
	

public String formatImages(MultipartFile file)
{
String imgPath = "";

try
{
	  // Get the file and save it somewhere
    byte[] bytes = file.getBytes();
	//String rootDir= request.getSession().getServletContext().getRealPath("/");
	//System.out.print("rootDir " + rootDir);
    //String UPLOADED_FOLDER ="F://AIPL//spring-eshop-master//src//main//resources//static//image//";
    String UPLOADED_FOLDER = new ReadSettingsFile().getValueFromParam("prodFilePath");
    Path path = Paths.get( UPLOADED_FOLDER + file.getOriginalFilename());
    Files.write(path, bytes);
    imgPath = "/image/" + file.getOriginalFilename();
    System.out.print("You successfully uploaded '" + file.getOriginalFilename() + "'" +  " on the path " + path + " and image path " + imgPath);
}
catch(Exception ex)
{
	 System.out.print("Error in formatImages " + ex.getMessage());
}
return imgPath;
}
	
	
	@RequestMapping("/article-list")
	public String articleList(Model model) {
		List<Article> articles = articleService.findAllArticles();
		model.addAttribute("articles", articles);
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "articleList";
	}
	
	@RequestMapping("/edit")
	public String editArticle(@RequestParam("id") Long id, Model model) {
		Article article = articleService.findArticleById(id);
		String preselectedSizes = "";
		for (Size size : article.getSizes()) {
			preselectedSizes += (size.getValue() + ",");
		}
		String preselectedBrands = "";
		for (Brand brand : article.getBrands()) {
			preselectedBrands += (brand.getName() + ",");
		}
		String preselectedCategories = "";
		for (Category category : article.getCategories()) {
			preselectedCategories += (category.getName() + ",");
		}		
		model.addAttribute("article", article);
		model.addAttribute("preselectedSizes", preselectedSizes);
		model.addAttribute("preselectedBrands", preselectedBrands);
		model.addAttribute("preselectedCategories", preselectedCategories);
		model.addAttribute("allSizes", articleService.getAllSizes());
		model.addAttribute("allBrands", articleService.getAllBrands());
		model.addAttribute("allCategories", articleService.getAllCategories());
		return "editArticle";
	}
	
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	public String editArticlePost(@ModelAttribute("article") Article article, HttpServletRequest request) {		
		Article newArticle = new ArticleBuilder()
				.withTitle(article.getTitle())
				.withDescription(article.getDescription())
				.stockAvailable(article.getStock())
				.withPrice(article.getPrice())
				.withMRPPrice(article.getMrpPrice())
				.withTransferPrice(article.getTransferPrice())
				.imageLink(article.getPicture())
				.sizesAvailable(Arrays.asList(request.getParameter("size").split("\\s*,\\s*")))
				.ofCategories(Arrays.asList(request.getParameter("category").split("\\s*,\\s*")))
				.ofBrand(Arrays.asList(request.getParameter("brand").split("\\s*,\\s*")))
				.withVolume(article.getVolume())
				.withWeight(article.getWeight())
				.withGstOf(article.getGst())
				.withUnitwidth(article.getUnitwidth())
				.withUnitlength(article.getUnitlength())
				.withUnitdepth(article.getUnitdepth())
				.build();
		newArticle.setId(article.getId());
		articleService.saveArticle(newArticle);	
		return "redirect:article-list";
	}
	
	@RequestMapping("/delete")
	public String deleteArticle(@RequestParam("id") Long id) {
		articleService.deleteArticleById(id);
		return "redirect:article-list";
	}
	
}
