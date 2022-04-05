package com.utudo.hwwd.helpers;

import com.sun.xml.bind.v2.TODO;
import com.utudo.hwwd.controllers.MainController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtils extends MainController {
    /*
     *
     * @param file 文件
     * @param path 文件存放路径
     * @param fileName 保存的文件
     * @return
     */
    public static boolean upload(MultipartFile file, String path, String fileName){
        String realPath = path + "/" + fileName;
        System.out.println("上传文件"+realPath);

        File dest = new File(realPath);

        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }

        try{
            file.transferTo(dest);
            return true;
        }catch (IllegalStateException e){
            //TODO Auto- generated catch block
            e.printStackTrace();
            return false;
        }catch(IOException ie){
            ie.printStackTrace();
            return false;
        }


    }
}
