package com.ocyd.appfactory.controller;

import com.ocyd.appfactory.service.SystemService;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


/**
 */
@Scope("prototype")
@Controller
@RequestMapping("/fileUploadController")
public class FileUploadController {
    private static HashMap<String, String> extMap = new HashMap<String, String>();

    static {
        //定义允许上传的文件扩展名
        extMap.put("image", "jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
    }

    /**
     * Logger for this class
     */
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(FileUploadController.class);

    private SystemService systemService;
    private String message = null;

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    /**
     * 对商家进行保存
     */

    @RequestMapping(params = "saveFiles")
    @ResponseBody
    public JSONObject saveFiles(MultipartHttpServletRequest request, HttpServletResponse response) {
        JSONObject j = new JSONObject();

        Iterator<String> itr = request.getFileNames();

        //文件保存目录路径
        String savePath = request.getSession().getServletContext().getRealPath("/") + "upload/image";

        //文件保存目录URL
        String saveUrl = request.getContextPath() + "/upload/image";


        //最大文件大小
        long maxSize = 1000000;

        if (!ServletFileUpload.isMultipartContent(request)) {
            j.put("message", "文件格式不对，请选择文件。");
            return j;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
        savePath += ymd + "/";
        saveUrl += ymd + "/";
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        MultipartFile mpf = null;

        //2. get each file
        while (itr.hasNext()) {

            //2.1 get next MultipartFile
            mpf = request.getFile(itr.next());
            logger.info(mpf.getOriginalFilename() + " uploaded! ");

            try {
                String fileName = mpf.getOriginalFilename();
                //扩展名
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmSS");
                String newFileName = df.format(new Date()) + "." + fileExt;

                // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)
                FileCopyUtils.copy(mpf.getBytes(), new File(savePath, newFileName));

                j.put("error", 0);
                j.put("url", saveUrl + newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return j;
    }
}