package org.jeecgframework.web.system.service.impl;

import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.web.system.service.UploadFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;

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
}
