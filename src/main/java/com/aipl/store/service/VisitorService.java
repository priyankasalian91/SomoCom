package com.aipl.store.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aipl.store.domain.Url;
import com.aipl.store.domain.Visitor;
import com.aipl.store.exception.LinkParameterException;
import com.aipl.store.exception.VisitorParameterException;
import com.aipl.store.repository.LinkRepository;

import utility.Couple;

@Service
public class VisitorService {

    private JdbcTemplate jdbcTemplate;
    private LinkRepository linkRepository;

    public VisitorService(JdbcTemplate jdbcTemplate, LinkRepository linkRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.linkRepository = linkRepository;
    }

    @Transactional(rollbackFor = Throwable.class)
    public void insertVisitor(String link, Visitor visitor) throws Throwable {

        Integer urlIdByLink = linkRepository.getUrlIdByLink(link);
        if (urlIdByLink == null) {
            throw new LinkParameterException("link is not exist");
        }
        visitor.setUrlId(urlIdByLink);

        int affect = jdbcTemplate.update("insert into `visitors`(url_id,ip,date)values(?,?,?)",
                visitor.getUrlId(),
                visitor.getIp(),
                visitor.getDate());
        if (affect < 0) {
            throw new VisitorParameterException("Visitor Parameter is not valid and can not to be insert");
        }
    }

    @Transactional
    public Couple<Url, Integer> getLinkStatistics(String link) {
        Url url = linkRepository.findFirstByLink(link);
        if (url != null) {
            Integer statistics = jdbcTemplate.queryForObject("select count(*) from visitors where url_id=?",
                    Integer.class,
                    url.getUrlId());

            return new Couple<>(url, statistics);
        }
        return null;
    }
    
    @Transactional
    public int getAffiliateUserID(String username) {
        int affiUserId = jdbcTemplate.queryForObject("select id from affiliate_user where username=?",
                Integer.class,
                username);
    	return affiUserId;
    }
    
    @Transactional
    public String getInviteCode(String username) {
        String affiUserId = jdbcTemplate.queryForObject("select invite_code from affiliate_user where username=?",
        		String.class,
                username);
    	return affiUserId;
    }
    @Transactional
    public String getAffiliateUserEmail(int affiID) {
        String affiEmail = jdbcTemplate.queryForObject("select email from affiliate_user where id=?",
                String.class,
                affiID);
    	return affiEmail;
    }
    
    @Transactional
    public int getUrlCount(int affiurlId) {
        int count = jdbcTemplate.queryForObject("select count(*) from affilate_links where affilate_user=?",
                Integer.class,
                affiurlId);
    	return count;
    }
    
    @Transactional
    public List<Map<String, Object>> getList(int affUserId) {
        List<Map<String, Object>> getListDetails = jdbcTemplate.queryForList("select urlid, link ,url from  affilate_links where affilate_user=?",
        						affUserId);
        return getListDetails;
    }
    
    
    @Transactional
    public List<Map<String, Object>> salesReport(int affiliateID) {
        List<Map<String, Object>> getSalesReport = jdbcTemplate.queryForList("SELECT prod_id, product_commission, product_total, product_order_date FROM affiliate_user_details where refauid=?",
        		affiliateID);
        return getSalesReport;
    }
    
    
    @Transactional
    public int getTotalClick(int id) {
        int count = jdbcTemplate.queryForObject("select count(*) from visitors where url_id=?",
                Integer.class,
                id);
    	return count;
    }
    
    //session code 1-true , 0- false
    @Transactional
    public int userProdExist(String prod_id, int user_id) {
        int exist = jdbcTemplate.queryForObject(" SELECT EXISTS(SELECT * from affiliate_user_details WHERE prod_id=? and user_id=? and buy_promo_code=\"N\")",
                Integer.class,
                prod_id,user_id);
    	return exist;
    }
    @Transactional
    public void sessionWithRegisterProductDetails(int affiliate_id, int refauid, int user_id, String date, String prodID, String buyPromoCodeFlag) {
        jdbcTemplate.update("insert into affiliate_user_details(affiliate_id, refauid, user_id, user_register_date, prod_id, buy_promo_code)values(?,?,?,?,?,?)",
        		affiliate_id,refauid,
        		user_id,
                date,
                prodID,buyPromoCodeFlag);
    }
    @Transactional
    public void sessionWithProductPurchaseDetails(String product_order_date,BigDecimal product_total, int user_id) {
    	String product_commission = "10%";
    	String product_status = "buy";
    	String query = "UPDATE affiliate_user_details SET product_commission=?, product_order_date=?, product_status=?, product_total=?  WHERE user_id=?";
    	 Object[] params = { product_commission ,product_order_date,product_status, product_total, user_id};
    	 jdbcTemplate.update(query,params);
    }
    
    @Transactional
    public void sessionWithoutRegisterProductDetails(int af_id, int new_id,String date, int prodID) {
        jdbcTemplate.update("insert into sessionwithoutproduct(affiliate_id,user_id,user_register_date,prod_id)values(?,?,?,?)",
                af_id,
                new_id,
                date,
                prodID);
    }
    
    @Transactional
    public void sessionWithoutProductPurchaseDetails(String product_order_date,BigDecimal product_total, int user_id) {
    	String product_commission = "10%";
    	String product_status = "buy";
    	String query = "UPDATE sessionwithoutproduct SET product_commission=?, product_order_date=?, product_status=?, product_total=? WHERE user_id=?";
    	 Object[] params = { product_commission ,product_order_date,product_status, product_total, user_id};
    	 jdbcTemplate.update(query,params);
    }
    @Transactional
    public int affUserID(int userID) {
        int newUserId = jdbcTemplate.queryForObject("select refauid from affiliate_user_details where user_id=?",
                Integer.class,
                userID);
    	return newUserId;
    }
    
    //session code complete
    
    @Transactional
    public int newUserID(String username) {
        int newUserId = jdbcTemplate.queryForObject("select id from user where username=?",
                Integer.class,
                username);
    	return newUserId;
    }
    
    @Transactional
    public BigDecimal getOrderAmount(int userID, Long order_id) {
    	BigDecimal order_total = jdbcTemplate.queryForObject("select order_total from user_order where user_id=? and id=?",
    			BigDecimal.class,
                userID,order_id);
    	return order_total;
    }
    //Product Part
    @Transactional
    public int getArticleId(int userID, Long order_id) {
    	int affiUserId = jdbcTemplate.queryForObject("select article_id from cart_item where user_id=? and order_id=?",
    			Integer.class,
                userID,
                order_id);
    	return affiUserId;
    }
    
    
    @Transactional
    public String getProductName(int userID) {
    	String prodName = jdbcTemplate.queryForObject("select title from article where id=?",
    			String.class,
                userID);
    	return prodName;
    }
    //finish
    
    //######################## Invite Code Query######################// 
    @Transactional
    public int getAffiliateUserId(String inviteCode) {
        int newUserId = jdbcTemplate.queryForObject("select id from affiliate_user where invite_code=?",
                Integer.class,
                inviteCode);
    	return newUserId;
    }
    
    @Transactional
    public boolean getAffiliateId(String username) {
        int newUserId = jdbcTemplate.queryForObject("select id from affiliate_user where username=?",
                Integer.class,
                username);
        if(String.valueOf(newUserId) == null) {
        	return false;
        }
        
    	return true;
    }
    //session code 1-true , 0- false
    @Transactional
    public int inviteUserExit(int invite_id) {
        int exist = jdbcTemplate.queryForObject("SELECT EXISTS(SELECT * from invitedetails WHERE invite_id=?)",
                Integer.class,
                invite_id);
    	return exist;
    }
    @Transactional
	public int getPromoAuId(int invite_id) {
        int promoAuId = jdbcTemplate.queryForObject("select affiliate_id from invitedetails where invite_id=?",
                Integer.class,
                invite_id);
		return promoAuId;
	}
    @Transactional
	public void orderBuyPromoCode(int userID, int affiliateId, String productID, int promoauId, BigDecimal orderAmount,
			String purchaseDate, String buyPromoCodeFlag) {
    	String product_commission = "7%";
    	String product_status = "buy";
        jdbcTemplate.update("insert into affiliate_user_details(user_id,affiliate_id,prod_id,buy_promo_code,"
        		+ "pomoauid,product_commission,product_order_date,"
        		+ "product_status,product_total)values(?,?,?,?,?,?,?,?,?)",
        		userID,
        		affiliateId,
        		productID,buyPromoCodeFlag,promoauId,
        		product_commission,purchaseDate,product_status,orderAmount);
		
	}
    
    @Transactional
    public List<Map<String, Object>> fetchListData(int affiliateId){
    	String query = "SELECT * FROM affiliate_user WHERE id IN (SELECT invite_id FROM invitedetails where affiliate_id=?)";
    	 List<Map<String, Object>> strList = jdbcTemplate.queryForList(query,affiliateId);
    	return strList;
    }
    
    @Transactional
    public List<Map<String, Object>> fetchListArticleId(int userID, Long order_id){
    	String query = "select article_id from cart_item where user_id=? and order_id=?";
    	 List<Map<String, Object>> strList = jdbcTemplate.queryForList(query,userID,order_id);
    	return strList;
    }

    @Transactional
	public int userExistST1(int user_id) {
        int exist = jdbcTemplate.queryForObject(" SELECT EXISTS(SELECT ref_aid from somodesk_test1 WHERE user_id=?)",
                Integer.class,
                user_id);
    	return exist;
	}
    
    @Transactional
	public int userExistST2(int user_id) {
        int exist = jdbcTemplate.queryForObject(" SELECT EXISTS(SELECT invite_aid from somodesk_test2 WHERE user_id=?)",
                Integer.class,
                user_id);
    	return exist;
	}
    
    @Transactional
	public int getReferralId(int userID) {
        int refAuId = jdbcTemplate.queryForObject("select ref_aid from somodesk_test1 where user_id=?",
                Integer.class,
                userID);
        return refAuId;
	}
    @Transactional
	public int getInviteId(int userID) {
        int invAuId = jdbcTemplate.queryForObject("select invite_aid from somodesk_test2 where user_id=?",
                Integer.class,
                userID);
        return invAuId;
	}
    
    @Transactional
    public List<Map<String, Object>> listST1(int affiliateId){
    	String query = "SELECT * FROM affiliate_user WHERE id IN (SELECT affiliate_id FROM somodesk_test1 where ref_aid=?);";
    	 List<Map<String, Object>> st1 = jdbcTemplate.queryForList(query,affiliateId);
    	return st1;
    }
    @Transactional
    public List<Map<String, Object>> listST2(int affiliateId){
    	String query = "SELECT * FROM affiliate_user WHERE id IN (SELECT affiliate_id FROM somodesk_test2 where invite_aid=?)";
    	 List<Map<String, Object>> st2 = jdbcTemplate.queryForList(query,affiliateId);
    	return st2;
    }
    
    @Transactional
    public List<Map<String, Object>> commissionSalesReport(Long userId) {
    	String query = "SELECT prod_id, prod_commission, product_total, prod_orderdate FROM somodesk_checkoutprod where user_id=?";
        List<Map<String, Object>> getCommissionSalesReport = jdbcTemplate.queryForList(query, userId);
        return getCommissionSalesReport;
    }
    
    @Transactional
    public List<Map<String, Object>> getUserListIDSMT1(int id) {
    	String query = "select user_id from somodesk_test1 where ref_aid = ?";
        List<Map<String, Object>> getCommissionSalesReport = jdbcTemplate.queryForList(query, id);
        return getCommissionSalesReport;
    }
    
    @Transactional
    public List<Map<String, Object>> getUserListIDSMT2(int id) {
    	String query = "select user_id from somodesk_test2 where invite_aid = ?";
        List<Map<String, Object>> getCommissionSalesReport = jdbcTemplate.queryForList(query, id);
        return getCommissionSalesReport;
    }
    @Transactional
	public List<Map<String, Object>> getListProductId(int id) {
		String query = "select prod_id from somodesk_checkoutprod where affiliate_id=?";
		List<Map<String, Object>>  productLsit = jdbcTemplate.queryForList(query, id);
		return productLsit;
	}
    
    @Transactional
	public List<Map<String, Object>> getListProductList(int id) {
		String query = "select id, title, price from article where id=?";
		List<Map<String, Object>>  productLsit = jdbcTemplate.queryForList(query, id);
		return productLsit;
	}
    

    @Transactional
    public void somodeskTest1Update(Long affiliate_id,Long user_id) {
    	String query = "update somodesk_test1 set affiliate_id = ?  where user_id = ?";
    	 Object[] params = { affiliate_id,user_id };
    	 jdbcTemplate.update(query,params);
    }
    
    
    
    @Transactional
	public String checkNull(Long userId) {
        String result = jdbcTemplate.queryForObject("select affiliate_id from somodesk_test1 where user_id = ?",
                String.class,
                userId);
		return result;
	}

}
