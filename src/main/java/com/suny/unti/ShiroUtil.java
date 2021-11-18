package com.suny.unti;


import com.suny.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {
    /**
     *
     * @return
     */
    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

}
