package bugoverdose.mys3.service.dto;

import static bugoverdose.mys3.common.StringFormatUtils.lowerCaseAndStrip;

import bugoverdose.mys3.exception.InvalidRequestException;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UploadImageRequestDto {

    private final String uploadPath;
    private final String fileName;
    private final MultipartFile uploadedImageFile;

    public UploadImageRequestDto(String uploadPath, String fileName, MultipartFile uploadedImageFile) {
        validateImageFile(uploadedImageFile);
        this.uploadPath = lowerCaseAndStrip(uploadPath);
        this.fileName = toValidFileName(fileName, uploadedImageFile);
        this.uploadedImageFile = uploadedImageFile;
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
        return lowerCaseAndStrip(fileName);
    }
}
