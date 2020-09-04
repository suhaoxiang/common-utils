package com.haoisou.common.utils;

import com.haoisou.common.MainDemo;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * banner print util
 * 制作地址
 * http://patorjk.com/software/taag/#p=display&f=Impossible&t=HAOISOU
 * @author qiandutianxia
 */
public class BannerUtil {
    private final static String FILENAME = "/banner.txt";

    public static void stdOut(){
        String path = MainDemo.class.getResource(FILENAME).getPath();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
            }
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
