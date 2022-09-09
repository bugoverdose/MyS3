package bugoverdose.mys3.presentation;

import static bugoverdose.mys3.common.StringFormatUtils.toCombinedPath;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import bugoverdose.mys3.common.WebpConstants;
import bugoverdose.mys3.presentation.dto.UploadImageResponse;
import bugoverdose.mys3.service.ImageService;
import bugoverdose.mys3.service.dto.UploadImageRequest;
import java.io.InputStream;
import java.net.URI;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/images/{uploadPath}/{fileName}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String uploadPath,
                                                        @PathVariable String fileName) {
        InputStream imageInputStream = imageService.find(toCombinedPath(uploadPath, fileName));
        return ResponseEntity.ok()
                .header(CONTENT_TYPE, WebpConstants.MEDIA_TYPE)
                .body(new InputStreamResource(imageInputStream));
    }

    @PostMapping("/api/images/{uploadPath}")
    public ResponseEntity<UploadImageResponse> uploadImage(@PathVariable String uploadPath,
                                                           @RequestParam(defaultValue = "") String fileName,
                                                           @ModelAttribute MultipartFile image) {
        String imagePath = imageService.saveOrUpdate(new UploadImageRequest(uploadPath, fileName, image));
        return ResponseEntity.created(URI.create("/images/" + imagePath))
                .body(new UploadImageResponse(imagePath));
    }

    @DeleteMapping("/api/images/{uploadPath}/{fileName}")
    public ResponseEntity<UploadImageResponse> deleteImage(@PathVariable String uploadPath,
                                                           @PathVariable String fileName) {
        imageService.delete(toCombinedPath(uploadPath, fileName));
        return ResponseEntity.noContent().build();
    }
}
