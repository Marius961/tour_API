package ua.tour.api.services;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Service
public class ImageService {

    private final String[] allowedExtensions = {".jpg",".png"};

    @Value(("${images.upload.path}"))
    private String uploadPath;

    public String saveImage(MultipartFile file) throws IOException {
        if(file != null) {
            String name = file.getOriginalFilename();
            if (name != null) {
                File uploadFolder = new File(uploadPath);
                boolean isUploadFolderExists = uploadFolder.exists();

                if (!isUploadFolderExists) {
                    isUploadFolderExists = uploadFolder.mkdir();
                }

                boolean isExstensionValid = false;
                String extension = name.substring(name.lastIndexOf("."));
                isExstensionValid = isImageExtensionValid(extension);

                if (isExstensionValid && isUploadFolderExists) {
                    String uuidFile = UUID.randomUUID().toString();
                    String resultFileName = uuidFile + "." + file.getOriginalFilename();
                    file.transferTo(new File(uploadPath + '/' + resultFileName));
                    return resultFileName;
                } else throw new IOException("File extension is not valid. Valid file extensions: " + Arrays.toString(allowedExtensions));
            } else throw new InvalidFileNameException(null, "Invalid file name");
        } else throw new FileNotFoundException("Uploaded file not found");
    }

    public void deleteImage(String fileName) throws FileNotFoundException {
        File file = new File(uploadPath + "/" + fileName);
        if (!file.delete()) throw new FileNotFoundException("file with name " + fileName + " not found");
    }

    private boolean isImageExtensionValid(String extension) {
        for (String e : allowedExtensions) {
            if (e.equals(extension)) return true;
        }
        return false;
    }
}
