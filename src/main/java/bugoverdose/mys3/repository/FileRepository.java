package bugoverdose.mys3.repository;

import static bugoverdose.mys3.common.StringFormatUtils.toCombinedPath;

import bugoverdose.mys3.common.WebpConstants;
import bugoverdose.mys3.exception.InternalServerError;
import bugoverdose.mys3.exception.NotFoundException;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileRepository {

    private final String filePathFormat;

    public FileRepository(@Value("${image.storage.root-directory}") String storageDirectory) {
        this.filePathFormat = toCombinedPath(getRootDirectory(), storageDirectory, WebpConstants.FILE_FORMAT);
    }

    private String getRootDirectory() {
        final var fileSystemView = FileSystemView.getFileSystemView();
        final var homeDirectory = fileSystemView.getHomeDirectory();
        return homeDirectory.getAbsolutePath();
    }

    public File find(String filePath) {
        final var file = new File(String.format(filePathFormat, filePath));
        if (!file.exists() || file.isDirectory()) {
            throw NotFoundException.ofImage();
        }
        return file;
    }

    public void save(String filePath, MultipartFile imageFile) {
        try (final var inputStream = imageFile.getInputStream()) {
            final var bufferedImage = ImageIO.read(inputStream);
            final var outputFile = new File(String.format(filePathFormat, filePath));
            createParentDirectoryIfNew(outputFile);
            ImageIO.write(bufferedImage, WebpConstants.EXTENSION, outputFile);
        } catch (IOException e) {
            throw InternalServerError.ofFileSaveFailure();
        }
    }

    private void createParentDirectoryIfNew(File file) {
        final var parentFile = file.getParentFile();
        if (parentFile.exists()) {
            return;
        }
        final var isCreated = parentFile.mkdirs();
        if (!isCreated) {
            throw InternalServerError.ofFileSaveFailure();
        }
    }

    public void delete(File file) {
        final var isDeleted = file.delete();
        if (!isDeleted) {
            throw InternalServerError.ofFileDeleteFailure();
        }
    }
}
