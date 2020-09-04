package com.haoisou.common;

import com.haoisou.common.utils.BannerUtil;
import com.haoisou.common.utils.ReflectUtil;
import com.haoisou.common.utils.StringUtil;

/**
 * demo
 * @author qiandutianxia
 */
public class MainDemo {
    public static void main(String[] args){
        BannerUtil.stdOut();
        ReflectUtil.findTemplateClass(StringUtil.class,0);
    }
}
