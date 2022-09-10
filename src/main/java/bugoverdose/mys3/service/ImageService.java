package bugoverdose.mys3.service;

import bugoverdose.mys3.common.StringFormatUtils;
import bugoverdose.mys3.common.WebpConstants;
import bugoverdose.mys3.repository.FileRepository;
import bugoverdose.mys3.service.dto.UploadImageRequest;
import java.io.File;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final FileRepository fileRepository;

    public ImageService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File find(String filePath) {
        if (filePath.endsWith(WebpConstants.FILE_EXTENSION_FORMAT)) {
            filePath = StringFormatUtils.removeFileExtension(filePath);
        }
        return fileRepository.find(filePath);
    }

    public String saveOrUpdate(UploadImageRequest request) {
        String filePath = request.getFilePath();
        fileRepository.save(filePath, request.getImageFile());
        return filePath;
    }

    public void delete(String filePath) {
        File targetFile = fileRepository.find(filePath);
        fileRepository.delete(targetFile);
    }
}
