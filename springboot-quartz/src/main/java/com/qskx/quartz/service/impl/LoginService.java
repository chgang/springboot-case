package com.qskx.quartz.service.impl;

import com.qskx.quartz.dao.SheduleUserDao;
import com.qskx.quartz.entity.SheduleUser;
import com.qskx.quartz.utils.CookieUtil;
import com.qskx.quartz.utils.JacksonUtil;
import com.qskx.quartz.utils.ResponseCode;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;


@Component
public class LoginService {

    public static final String LOGIN_IDENTITY = "SHEDULE_LOGIN_IDENTITY";

    @Resource
    private SheduleUserDao sheduleUserDao;

    private String makeToken(SheduleUser xxlConfUser){
        String tokenJson = JacksonUtil.writeValueAsString(xxlConfUser);
        String tokenHex = new BigInteger(tokenJson.getBytes()).toString(16);
        return tokenHex;
    }
    private SheduleUser parseToken(String tokenHex){
        SheduleUser xxlConfUser = null;
        if (tokenHex != null) {
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray());      // username_password(md5)
            xxlConfUser = JacksonUtil.readValue(tokenJson, SheduleUser.class);
        }
        return xxlConfUser;
    }

    /**
     * login
     *
     * @param response
     * @param usernameParam
     * @param passwordParam
     * @param ifRemember
     * @return
     */
    public ResponseCode<String> login(HttpServletResponse response, String usernameParam, String passwordParam, boolean ifRemember){

        SheduleUser xxlConfUser = sheduleUserDao.load(usernameParam);
        if (xxlConfUser == null) {
            return new ResponseCode<String>(10001, "账号或密码错误");
        }

        String passwordParamMd5 = DigestUtils.md5DigestAsHex(passwordParam.getBytes());
        if (!xxlConfUser.getPassword().equals(passwordParamMd5)) {
            return new ResponseCode<String>(10001, "账号或密码错误");
        }

        String loginToken = makeToken(xxlConfUser);

        // do login
        CookieUtil.set(response, LOGIN_IDENTITY, loginToken, ifRemember);
        return new ResponseCode<>("登录成功");
    }

    /**
     * logout
     *
     * @param request
     * @param response
     */
    public void logout(HttpServletRequest request, HttpServletResponse response){
        CookieUtil.remove(request, response, LOGIN_IDENTITY);
    }

    /**
     * logout
     *
     * @param request
     * @return
     */
    public SheduleUser ifLogin(HttpServletRequest request){
        String cookieToken = CookieUtil.getValue(request, LOGIN_IDENTITY);
        if (cookieToken != null) {
            SheduleUser cookieUser = parseToken(cookieToken);
            if (cookieUser != null) {
                SheduleUser dbUser = sheduleUserDao.load(cookieUser.getUsername());
                if (dbUser != null) {
                    if (cookieUser.getPassword().equals(dbUser.getPassword())) {
                        return cookieUser;
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String password = DigestUtils.md5DigestAsHex("admin!#".getBytes());
        System.out.println("password : " + password);
    }

}
