package bugoverdose.mys3.image.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
public class UploadImageCommand {

    private String uploadPath;
    private String fileName;
    private MultipartFile uploadedImageFile;

    public UploadImageCommand(String uploadPath, String fileName, MultipartFile uploadedImageFile) {
        if (fileName.isBlank()) {
            fileName = uploadedImageFile.getOriginalFilename();
        }
        this.uploadPath = uploadPath.strip().toLowerCase();
        this.fileName = fileName.strip().toLowerCase();
        this.uploadedImageFile = uploadedImageFile;
    }
}
