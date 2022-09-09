package bugoverdose.mys3.service.dto;

import static bugoverdose.mys3.common.StringFormatUtils.toCombinedPath;

import bugoverdose.mys3.exception.InvalidRequestException;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UploadImageRequestDto {

    private final String filePath;
    private final MultipartFile imageFile;

    public UploadImageRequestDto(String uploadPath, String fileName, MultipartFile imageFile) {
        validateImageFile(imageFile);
        fileName = toValidFileName(fileName, imageFile);
        this.filePath = toCombinedPath(uploadPath, fileName);
        this.imageFile = imageFile;
    }

    private void validateImageFile(MultipartFile uploadedImageFile) {
        if (uploadedImageFile == null || uploadedImageFile.isEmpty()) {
            throw new InvalidRequestException("업로드할 파일이 존재하지 않습니다.");
        }
        String contentType = uploadedImageFile.getContentType();
        if (contentType == null || !contentType.startsWith("image")) {
            throw new InvalidRequestException("이미지 파일만 업로드 가능합니다.");
        }
    }

    private String toValidFileName(String fileName, MultipartFile uploadedImageFile) {
        if (fileName.isBlank()) {
            fileName = uploadedImageFile.getOriginalFilename();
        }
        return fileName;
    }
}
