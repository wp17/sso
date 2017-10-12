package com.github.wp17.sso.server.util.captcha;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;

public class JCaptcha {
    public static final MyManageableImageCaptchaService captchaService  
            = new MyManageableImageCaptchaService(new FastHashMapCaptchaStore(),new GMailEngine(), 180, 100000, 75000);
    
    public static boolean validateResponse(String id, String userCaptchaResponse) {  
        boolean validated = false;  
        try {  
            validated = captchaService.validateResponseForID(id, userCaptchaResponse).booleanValue();  
        } catch (CaptchaServiceException e) {  
            e.printStackTrace();  
        }  
        return validated;  
    }  
    
    public static boolean hasCaptcha(String id, String userCaptchaResponse) {  
        boolean validated = false;
        try {  
            validated = captchaService.hasCapcha(id, userCaptchaResponse);  
        } catch (CaptchaServiceException e) {  
            e.printStackTrace();  
        }  
        return validated;  
    }  
}  