package bugoverdose.mys3.service.dto;

import bugoverdose.mys3.common.StringFormatUtils;
import bugoverdose.mys3.exception.InvalidRequestException;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UploadImageRequest {

    private final String filePath;
    private final MultipartFile imageFile;

    public UploadImageRequest(String uploadPath, String fileName, String version, MultipartFile imageFile) {
        validateImageFile(imageFile);
        fileName = toValidFileName(fileName, version, imageFile);
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

    private String toValidFileName(String fileName, String version, MultipartFile imageFile) {
        fileName = toDefaultFileName(fileName, imageFile);
        fileName = StringFormatUtils.removeFileExtension(fileName);
        return addVersionSuffix(fileName, version);
    }

    private String toDefaultFileName(String fileName, MultipartFile imageFile) {
        if (fileName.isBlank()) {
            fileName = imageFile.getOriginalFilename();
        }
        return fileName;
    }

    private String addVersionSuffix(String fileName, String version) {
        if (version.isBlank()) {
            return fileName;
        }
        return String.format("%s-%s", fileName, version);
    }
}
