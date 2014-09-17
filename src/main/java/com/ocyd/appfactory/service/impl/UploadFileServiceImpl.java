package com.ocyd.appfactory.service.impl;

import com.ocyd.jeecgframework.core.util.ApplicationContextUtil;
import com.ocyd.appfactory.service.UploadFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 上传文件管理实现类
 * Created by Rox on 2014/9/14.
 */
@Service("uploadFileService")
public class UploadFileServiceImpl implements UploadFileService{

    @Override
    public boolean deleteFile(String file) {
        if(file != null && !file.equals("")) {
            String rootPath = ((WebApplicationContext)ApplicationContextUtil.getContext()).getServletContext().getRealPath("/");
            File f = new File(rootPath + file);

            if(f.exists()) {
                return f.delete();
            }
        }

        return true;
    }


    private String composeHtmlFileRelativePath() {
        return composeFileRelativePath("html");
    }

    private String composeFileRelativePath(String category) {
        String contextRoot = ((WebApplicationContext)ApplicationContextUtil.getContext()).getServletContext().getContextPath();
        StringBuilder sb = new StringBuilder(contextRoot);
        sb.append("upload/").append(category).append("/");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        sb.append(ymd).append("/");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmSS");
        sb.append(df.format(new Date())).append(".html");

        return sb.toString();
    }

    /**
     * 从指定的文件中读取出所有的内容。
     */
    @Override
    public String readHtmlFile(String relative) {
        String savePath = ((WebApplicationContext)ApplicationContextUtil.getContext()).getServletContext().getRealPath(relative);
        File file = new File(savePath);
        StringBuilder sb = new StringBuilder(1024);
        char[] buffer = new char[512];

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            int len = 0;
            while((len = reader.read(buffer)) > 0) {
                sb.append(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(reader != null) {
                    reader.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 将内容保存为html文件。
     */
    @Override
    public String saveHtmlFile(String filePath, String content) {
        String relative = filePath;

        if(relative == null || relative.trim().equals("")) {
            relative = composeHtmlFileRelativePath();
        }

        String savePath = ((WebApplicationContext)ApplicationContextUtil.getContext()).getServletContext().getRealPath(relative.trim());

        File file = new File(savePath);
        File dir = file.getParentFile();
        if(!dir.exists()) {
            dir.mkdirs();
        }

        BufferedWriter writer = null;
        try {
            if(!file.exists()) {
                file.createNewFile();
            }

            writer = new BufferedWriter(new FileWriter(savePath));
            writer.write("<html><head><meta charset=\"gbk\"></head><body>");
            writer.write(content);
            writer.write("</body></html>");
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) {
                    writer.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return relative;
    }
}
