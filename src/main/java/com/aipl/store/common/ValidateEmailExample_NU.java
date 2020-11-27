package com.aipl.store.common;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
 
public class ValidateEmailExample_NU {
 
    public static void main(String[] args) {
    	 ValidateEmailExample_NU example = new ValidateEmailExample_NU();
    	 String email = "";
    	 String testData[] = {
     		  	"test@gmail.com","test1@gmail.com","shiba@gmail.com","priya@gmail.com",
     		  	"shsabat1@yahoo.com",
     		  	"shibashankar.sabat92@gmail.com","suvi@gmail.com","test@test.in",
     		  	"pranks.salian91@gmail.com","priyanka.salian2604@yahoo.com","priyanka.salian2604@gmail.com","priyanka2604@yahoo.com"
           };

       for ( int ctr = 0 ; ctr < testData.length ; ctr++ ) {
          //System.out.println( testData[ ctr ] + " is valid? ");
          email =  testData[ ctr ]; 
          boolean isValid = example.validateEmail(email); 
          example.printStatus(email, isValid);
          
       }
  
    }
 
 private boolean validateEmail(String email) {

	 boolean isValid = false;
  try { 
//Create InternetAddress object and validated the email address.
 InternetAddress internetAddress = new InternetAddress(email);
 internetAddress.validate();
 isValid = true;
   } catch (AddressException e) {
       e.printStackTrace();
  }
  
  
  
  
   return isValid;
  }
 
    private void printStatus(String email, boolean valid) {
    	System.out.println(email + " is " + (valid ? "valid" : "not valid"));
    }
}

