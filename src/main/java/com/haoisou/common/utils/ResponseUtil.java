package com.haoisou.common.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

public class ResponseUtil {
    /**
     * 将object对象转为response对象
     *
     * @param object 响应对象
     * @return 返回response对象
     */
    public static Response toResponse(Object object) {
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(object);
        return JSONObject.parseObject(jsonObject.toJSONString(), Response.class);
    }

    /**
     * 直接调用response返回json对象信息
     *
     * @param response       响应对象
     * @param responseResult 响应结果
     */
    public static void toJSONResponse(HttpServletResponse response, Response responseResult) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.append(JSONObject.toJSONString(responseResult));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载
     *
     * @param response 响应对象
     * @param fileName 文件名字
     * @param bytes    文件流
     * @throws IOException 抛出IO异常
     */
    public static void fileDownload(HttpServletResponse response, String fileName, byte[] bytes) throws IOException {
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Expose-Headers", "*");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.close();
        outputStream.flush();
    }
}
