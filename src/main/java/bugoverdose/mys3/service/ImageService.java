package bugoverdose.mys3.service;

import bugoverdose.mys3.exception.InternalServerError;
import bugoverdose.mys3.exception.NotFoundException;
import bugoverdose.mys3.service.dto.UploadImageCommand;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private final String filePathFormat;

    public ImageService(@Value("${image.storage.root-directory}") String storageDirectory) {
        String rootDirectory = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
        this.filePathFormat = rootDirectory + "/" + storageDirectory + "/%s/%s.png";
    }

    public InputStream find(String uploadPath, String fileName) {
        try {
            return new FileInputStream(String.format(filePathFormat, uploadPath, fileName));
        } catch (FileNotFoundException e) {
            throw new NotFoundException("존재하지 않는 이미지입니다.");
        }
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
            throw new InternalServerError("이미지 업로드에 실패히였습니다.");
        }
    }

    private void createParentDirectoryIfNew(File file) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
    }
}
