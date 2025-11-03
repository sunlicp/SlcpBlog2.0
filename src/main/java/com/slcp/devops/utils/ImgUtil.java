package com.slcp.devops.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.slcp.devops.constant.DevOpsConstant;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: SunXiaoWei
 * @description: 获取随机图片
 * @create: 2022-03-29 14:20:29
 **/
public class ImgUtil {

    /*public static byte[] getImg(String u) {
        URL url;
        try {
            url = new URL(u);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            InputStream in = conn.getInputStream();
            return readInputStream(in);
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return null;
    }*/

    public static byte[] getImg(String u) throws IOException {
        URL url;
        HttpsURLConnection conn;
        try {
            // 对 URL 中的参数部分进行编码
            url = new URL(u);

            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);

            // 处理重定向
            int status = conn.getResponseCode();
            if (status == HttpsURLConnection.HTTP_MOVED_TEMP || status == HttpsURLConnection.HTTP_MOVED_PERM) {
                String newUrl = conn.getHeaderField("Location");
                url = new URL(newUrl);
                conn = (HttpsURLConnection) url.openConnection();
            }

            InputStream in = conn.getInputStream();
            return readInputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
            url = new URL(encodeUrl(e.getMessage().substring(49).trim()));
            conn = (HttpsURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            return readInputStream(in);
        }
    }

    private static String encodeUrl(String url) throws IOException {
        int index = url.lastIndexOf("/");
        if (index != -1) {
            String baseUrl = url.substring(0, index + 1);
            String fileName = url.substring(index + 1);
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString()).replace("+", "%20");
            return baseUrl + encodedFileName;
        }
        return url;
    }

    private static byte[] readInputStream(InputStream ins) throws IOException {
        // TODO 自动生成的方法存根
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = ins.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        ins.close();

        return out.toByteArray();
    }

    public static JSONObject getIp() {
        try {
            HttpRequest httpRequest = HttpUtil.createGet(DevOpsConstant.IP_PATH);
            HttpResponse resp = httpRequest.execute();
            if (DevOpsConstant.STATUS_200 == resp.getStatus()) {
                return JSONObject.parseObject(resp.body());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.parseObject(DevOpsConstant.IP_JSON);
    }

    public static String upload(MultipartFile file) throws IOException {
        //获取图片原始文件名
        String originalFilename = file.getOriginalFilename();
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //进行拼接 时间+UUID+原始文件名+文件后缀名
        String fileName = "message/" + format + "-" + originalFilename;
        return QiniuUtils.upload2Qiniu(file.getBytes(), fileName);
    }
}
