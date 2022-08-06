package bugoverdose.mys3.service;

import bugoverdose.mys3.service.dto.UploadImageCommand;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private static final String FILENAME_FORMAT = "static/images/%s/%s";

    public void upload(UploadImageCommand command) {
        String uploadPath = command.getUploadPath();
        String fileName = command.getFileName();
        MultipartFile uploadedImageFile = command.getUploadedImageFile();
        try {
            BufferedImage uploadedImage = ImageIO.read(uploadedImageFile.getInputStream());
            File outputFile = new File(String.format(FILENAME_FORMAT, uploadPath, fileName));
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
