package bugoverdose.mys3.service;

import static bugoverdose.mys3.common.StringFormatUtils.toCombinedPath;

import bugoverdose.mys3.common.WebpConstants;
import bugoverdose.mys3.exception.InternalServerError;
import bugoverdose.mys3.exception.NotFoundException;
import bugoverdose.mys3.service.dto.UploadImageRequest;
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

    private final String filePathFullFormat;

    public ImageService(@Value("${image.storage.root-directory}") String storageDirectory) {
        String rootDirectory = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
        this.filePathFullFormat = toCombinedPath(rootDirectory, storageDirectory, WebpConstants.FILE_FORMAT);
    }

    public InputStream find(String filePath) {
        try {
            return new FileInputStream(String.format(filePathFullFormat, filePath));
        } catch (FileNotFoundException e) {
            throw NotFoundException.ofImage();
        }
    }

    public String saveOrUpdate(UploadImageRequest request) {
        MultipartFile imageFile = request.getImageFile();
        String filePath = request.getFilePath();
        try {
            BufferedImage uploadedImage = ImageIO.read(imageFile.getInputStream());
            File outputFile = new File(String.format(filePathFullFormat, filePath));
            createParentDirectoryIfNew(outputFile);
            ImageIO.write(uploadedImage, WebpConstants.EXTENSION, outputFile);
            return filePath;
        } catch (IOException e) {
            throw InternalServerError.ofFileSaveFailure();
        }
    }

    private void createParentDirectoryIfNew(File file) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
    }
}
