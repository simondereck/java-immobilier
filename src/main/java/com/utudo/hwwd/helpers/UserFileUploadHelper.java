package com.utudo.hwwd.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class UserFileUploadHelper {

    @Value(HwDatas.USER_FILE_PATH)
    public String uploadDir;

    public void uploadFile(MultipartFile file) throws Exception {
        final String localPath = uploadDir + HwTools.getCurrentUser().getId();

        try {
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String fileNameNew = HwTools.hashPassword(HwTools.getTimeMillis() + fileName) + suffixName;

//            Path copyLocation = Paths
//                    .get(localPath + File.separator + StringUtils.cleanPath(fileNameNew));
            FileUtils.upload(file, localPath, fileNameNew);
//            Files.upload(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Could not store file " + file.getOriginalFilename()
                    + ". Please try again!");
        }

    }
}
