package bugoverdose.mys3.service.dto;

import bugoverdose.mys3.exception.InvalidRequestException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
public class UploadImageCommand {

    private String uploadPath;
    private String fileName;
    private MultipartFile uploadedImageFile;

    public UploadImageCommand(String uploadPath, String fileName, MultipartFile uploadedImageFile) {
        validateImageFile(uploadedImageFile);
        if (fileName.isBlank()) {
            fileName = uploadedImageFile.getOriginalFilename();
        }
        this.uploadPath = uploadPath.strip().toLowerCase();
        this.fileName = fileName.strip().toLowerCase();
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
        if (contentType.equals(MediaType.IMAGE_GIF_VALUE)) {
            throw new InvalidRequestException("GIF 파일은 업로드할 수 없습니다.");
        }
    }
}
