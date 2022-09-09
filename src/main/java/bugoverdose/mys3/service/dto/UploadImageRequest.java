package bugoverdose.mys3.service.dto;

import static bugoverdose.mys3.common.StringFormatUtils.toCombinedPath;

import bugoverdose.mys3.exception.InvalidRequestException;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UploadImageRequest {

    private final String filePath;
    private final MultipartFile imageFile;

    public UploadImageRequest(String uploadPath, String fileName, MultipartFile imageFile) {
        validateImageFile(imageFile);
        fileName = toValidFileName(fileName, imageFile);
        this.filePath = toCombinedPath(uploadPath, fileName);
        this.imageFile = imageFile;
    }

    private void validateImageFile(MultipartFile uploadedImageFile) {
        if (uploadedImageFile == null || uploadedImageFile.isEmpty()) {
            throw InvalidRequestException.ofFileMissing();
        }
        String contentType = uploadedImageFile.getContentType();
        if (contentType == null || !contentType.startsWith("image")) {
            throw InvalidRequestException.ofNonImageType();
        }
    }

    private String toValidFileName(String fileName, MultipartFile uploadedImageFile) {
        if (fileName.isBlank()) {
            fileName = uploadedImageFile.getOriginalFilename();
        }
        return fileName;
    }
}
