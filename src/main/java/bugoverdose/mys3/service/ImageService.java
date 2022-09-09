package bugoverdose.mys3.service;

import bugoverdose.mys3.exception.NotFoundException;
import bugoverdose.mys3.repository.FileRepository;
import bugoverdose.mys3.service.dto.UploadImageRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final FileRepository fileRepository;

    public ImageService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public InputStream find(String filePath) {
        try {
            return new FileInputStream(fileRepository.find(filePath));
        } catch (FileNotFoundException e) {
            throw NotFoundException.ofImage();
        }
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
