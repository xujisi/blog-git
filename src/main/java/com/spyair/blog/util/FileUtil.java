package com.spyair.blog.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class FileUtil {
    public static String uploadFile(String path, MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件上传后的路径
        fileName = UUID.randomUUID() + suffixName;
        String uppath = path + fileName;
        File targetFile = new File(uppath);
        file.transferTo(targetFile);
        return fileName;
    }

    public static String getUrl(String ip, String wpost, String staticAccessPath) {
        String url = "http://" + ip + ":" + wpost + staticAccessPath;
        return url;
    }


    public static InputStream getResourcesFileInputStream(String fileName) {
        System.out.println(Thread.currentThread().getContextClassLoader());
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }

    /**
     * 下载模板文件
     * @param response
     * @param inFileName
     * @param outFileNam
     */
    public void downloadExcel(HttpServletResponse response, String inFileName, String outFileNam) {
        InputStream inputStream = null;
        try {
            response.reset();
            //设置输出文件格式
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(outFileNam.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
            ServletOutputStream outputStream = response.getOutputStream();
            inputStream = this.getClass().getResourceAsStream("/static/excelModel/"+inFileName);
            byte[] buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, length);
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {

                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
