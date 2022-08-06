package bugoverdose.mys3.service;

import bugoverdose.mys3.service.dto.UploadImageCommand;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private final String filePathFormat;

    public ImageService(@Value("${image.storage.root-directory}") String rootDirectory) {
        this.filePathFormat = rootDirectory + "/%s/%s.png";
    }

    public void upload(UploadImageCommand command) {
        MultipartFile uploadedImageFile = command.getUploadedImageFile();
        String uploadPath = command.getUploadPath();
        String fileName = command.getFileName();
        try {
            BufferedImage uploadedImage = ImageIO.read(uploadedImageFile.getInputStream());
            File outputFile = new File(String.format(filePathFormat, uploadPath, fileName));
            createParentDirectoryIfNew(outputFile);
            ImageIO.write(uploadedImage, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createParentDirectoryIfNew(File file) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
    }
}
