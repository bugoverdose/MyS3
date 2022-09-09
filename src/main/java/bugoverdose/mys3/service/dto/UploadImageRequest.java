package bugoverdose.mys3.service.dto;

import bugoverdose.mys3.common.StringFormatUtils;
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
        this.filePath = StringFormatUtils.toCombinedPath(uploadPath, fileName);
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

    private String toValidFileName(String fileName, MultipartFile imageFile) {
        if (fileName.isBlank()) {
            fileName = imageFile.getOriginalFilename();
        }
        return StringFormatUtils.removeFileExtension(fileName);
    }
}
