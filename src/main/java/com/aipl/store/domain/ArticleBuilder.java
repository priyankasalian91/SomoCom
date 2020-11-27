package com.aipl.store.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArticleBuilder {
		
	private String title;
	private int stock;	
	private double price;
	private double mrpPrice; //MRP
	private double transferPrice; //transfer Price
	private String picture;
	private String description;
	private String propdetails;
	private List<String> sizes;
	private List<String> categories;
	private List<String> brands;
	private List<String> articleimages;
	
	private double volume;
	private double gst; // in %
	private double weight; // in kgs
	// Product Dimension (unit) in cms
	private double unitlength;
	private double unitwidth;
	private double unitdepth;
	
	public ArticleBuilder() {
	}
	
	public ArticleBuilder withTitle(String title) {
		this.title = title;
		return this;
	}
	
	public ArticleBuilder stockAvailable(int stock) {
		this.stock = stock;
		return this;
	}
	
	public ArticleBuilder withPrice(double price) {
		this.price = price;
		return this;
	}
	
	public ArticleBuilder withMRPPrice(double mrpPrice) {
		this.mrpPrice = mrpPrice;
		return this;
	}
	
	public ArticleBuilder withTransferPrice(double transferPrice) {
		this.transferPrice = transferPrice;
		return this;
	}
	
	public ArticleBuilder imageLink(String picture) {
		this.picture = picture;
		return this;
	}
	
	public ArticleBuilder sizesAvailable(List<String> sizes) {
		this.sizes = sizes;
		return this;
	}
	
	public ArticleBuilder ofCategories(List<String> categories) {
		this.categories = categories;
		return this;
	}
	
	public ArticleBuilder ofBrand(List<String> brands) {
		this.brands = brands;
		return this;
	}
	
	public ArticleBuilder withMoreImages(List<String> articleimages) {
		this.articleimages = articleimages;
		return this;
	}
	
	public ArticleBuilder withDescription(String description) {
		this.description = description;
		return this;
	}
	
	public ArticleBuilder withPropdetails(String propdetails) {
		this.propdetails = propdetails;
		return this;
	}
	
	
	public ArticleBuilder withVolume(Double volume) {
		this.volume = volume;
		return this;
	}
 	
	public ArticleBuilder withGstOf(Double gst) {
		this.gst = gst;
		return this;
	}
 
	public ArticleBuilder withWeight(Double weight) {
		this.weight = weight;
		return this;
	}
 
 public ArticleBuilder withUnitlength(Double unitlength) {
		this.unitlength = unitlength;
		return this;
	}
	
	public ArticleBuilder withUnitwidth(Double unitwidth) {
		this.unitwidth = unitwidth;
		return this;
	}
	public ArticleBuilder withUnitdepth(Double unitdepth) {
		this.unitdepth = unitdepth;
		return this;
	}
	
	
	public Article build() {
		Article article = new Article();
		article.setTitle(this.title);
		article.setPrice(this.price);
		article.setMrpPrice(this.mrpPrice);
		article.setTransferPrice(this.transferPrice);
		article.setStock(this.stock);
		article.setPicture(this.picture);		
		article.setDescription(this.description);
		article.setPropdetails(this.propdetails);
		/*article.setVolume(this.volume);
		article.setGst(this.gst);
		article.setWeight(this.weight);
		article.setUnitdepth(this.unitdepth);
		article.setUnitlength(this.unitlength);
		article.setUnitwidth(this.unitwidth);*/
		
		if (this.sizes != null && !this.sizes.isEmpty()) {
			Set<Size> sizeElements = new HashSet<>();
			for (String val : this.sizes) {
				sizeElements.add(new Size(val,article));
			}	
			article.setSizes(sizeElements);
		}
		
		if (this.categories != null && !this.categories.isEmpty() ) {
			Set<Category> catElements = new HashSet<>();
			for (String val : this.categories) {
				catElements.add(new Category(val,article));
			}
			article.setCategories(catElements);
		}		
		if (this.brands != null && !this.brands.isEmpty() ) {
			Set<Brand> brandlements = new HashSet<>();
			for (String val : this.brands) {
				brandlements.add(new Brand(val,article));
			}
			article.setBrands(brandlements);
		}		
		if (this.articleimages != null && !this.articleimages.isEmpty() ) {
			Set<ArticleImage> moreimgElements = new HashSet<>();
			for (String val : this.articleimages) {
				moreimgElements.add(new ArticleImage(val,article));
			}
			article.setArticleimages(moreimgElements);
		}
		
		return article;
	}
	
}