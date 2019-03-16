package ua.tour.api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageService {

    @Value(("${images.upload.path}"))
    private String uploadPath;

    public String saveImage(MultipartFile file) throws IOException {
        if(file != null) {
            File uploadFolder = new File(uploadPath);

            if (!uploadFolder.exists()) {
                uploadFolder.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + '/' + resultFileName));
            return resultFileName;
        }
        return "";
    }

    public boolean deleteFile(String fileName) throws FileNotFoundException {
        File file = new File(uploadPath + "/" + fileName);
        if (file.delete()) {
            return true;
        } else throw new FileNotFoundException("file with name " + fileName + " not found");
    }
}
