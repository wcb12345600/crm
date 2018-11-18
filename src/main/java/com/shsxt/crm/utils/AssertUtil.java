package com.shsxt.crm.utils;


import com.shsxt.crm.exceptions.LoginException;
import com.shsxt.crm.exceptions.ParamsException;

public class AssertUtil {
    
    /**
     * 表达式结果真时判断
     * @param msg
     */
    public static void isTrue(Boolean expression,String msg){
        if(expression){
            throw new ParamsException(msg);
        }   
    }
    public static void isTrue(Boolean expression){
        if(expression){
            throw new ParamsException("参数异常");
        }
    }   
    /**
     * 参数为空时
     * @param object
     * @param msg
     */
    public static void isNull(Object object,String msg){
        if(object==null){
            throw new ParamsException(msg);
        }
    }
    /**
     * 参数不空时
     * @param object
     * @param msg
     */
    public static void notNull(Object object,String msg){
        if(object!=null){
            throw new ParamsException(msg);
        }
    }

    public static void isNotLogin(Boolean flag,String msg){
        if (flag){
            throw new LoginException(msg);
        }
    }
}