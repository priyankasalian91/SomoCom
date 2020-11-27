package com.aipl.store.domain;

import java.util.Set;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Article {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	private int stock;	
	private double price;// offer proce
	private double mrpPrice; //MRP
	private double transferPrice; //transfer Price
	private String picture;
	
	@Column(length=100000000)
	private String description;
	
	private double volume;
	private double gst; // in %
	private double weight; // in kgs
	// Product Dimension (unit) in cms
	private double unitlength;
	private double unitwidth;
	private double unitdepth;
	
	
	
	@Transient
	private MultipartFile productImage; // new
	
	@Transient
	 private List<MultipartFile> moreImages;
	
	@Transient
	private List<String> moreImageNames;
	
	@Column(length = 100000000)
	private String propdetails;
	
	@Transient
	private List<String> techdetails;
	
	
	@OneToMany(mappedBy="article", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Size> sizes;
	
	@OneToMany(mappedBy="article", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Brand> brands;
	
	@OneToMany(mappedBy="article", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Category> categories;

	@OneToMany(mappedBy="article", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<ArticleImage> articleimages;
	
	public Article() {
	}
	
	public boolean hasStock(int amount) {
		return (this.getStock() > 0) && (amount <= this.getStock());
	}
	
	public void decreaseStock(int amount) {
		this.stock -= amount;
	}
	

	public void addSize(Size size) {
        sizes.add(size);
        size.setArticle(this);
    }
 
    public void removeSize(Size size) {
        sizes.remove(size);
        size.setArticle(null);
    }
    
	public void addCategory(Category category) {
        categories.add(category);
        category.setArticle(this);
    }
 
    public void removeCategory(Category category) {
    	categories.remove(category);
        category.setArticle(null);
    }
    
	public void addSize(Brand brand) {
        brands.add(brand);
        brand.setArticle(this);
    }
 
    public void removeSize(Brand brand) {
    	brands.remove(brand);
        brand.setArticle(null);
    }
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Set<Size> getSizes() {
		return sizes;
	}
	public void setSizes(Set<Size> sizes) {
		this.sizes = sizes;
	}
	public Set<Brand> getBrands() {
		return brands;
	}
	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}
	public Set<Category> getCategories() {
		return categories;
	}
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

	public MultipartFile getProductImage() {
		return productImage;
	}

	public void setProductImage(MultipartFile productImage) {
		this.productImage = productImage;
	}

	public List<MultipartFile> getMoreImages() {
		return moreImages;
	}

	public void setMoreImages(List<MultipartFile> moreImages) {
		this.moreImages = moreImages;
	}

	public Set<ArticleImage> getArticleimages() {
		return articleimages;
	}

	public void setArticleimages(Set<ArticleImage> articleimages) {
		this.articleimages = articleimages;
	}
	
	public List<String> getMoreImageNames() {
		return moreImageNames;
	}
	public void setMoreImageNames(List<String> moreImageNames) {
		this.moreImageNames = moreImageNames;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPropdetails() {
		return propdetails;
	}

	public void setPropdetails(String propdetails) {
		this.propdetails = propdetails;
	}

	public List<String> getTechdetails() {
		return techdetails;
	}
	public void setTechdetails(List<String> techdetails) {
		this.techdetails = techdetails;
	}

	public double getMrpPrice() {
		return mrpPrice;
	}

	public void setMrpPrice(double mrpPrice) {
		this.mrpPrice = mrpPrice;
	}

	public double getTransferPrice() {
		return transferPrice;
	}

	public void setTransferPrice(double transferPrice) {
		this.transferPrice = transferPrice;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getGst() {
		return gst;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getUnitlength() {
		return unitlength;
	}

	public void setUnitlength(double unitlength) {
		this.unitlength = unitlength;
	}

	public double getUnitwidth() {
		return unitwidth;
	}

	public void setUnitwidth(double unitwidth) {
		this.unitwidth = unitwidth;
	}

	public double getUnitdepth() {
		return unitdepth;
	}

	public void setUnitdepth(double unitdepth) {
		this.unitdepth = unitdepth;
	}
	
	
}
