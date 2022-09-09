package bugoverdose.mys3.presentation;

import static bugoverdose.mys3.common.StringFormatUtils.toCombinedPath;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import bugoverdose.mys3.common.WebpConstants;
import bugoverdose.mys3.presentation.dto.UploadImageResponse;
import bugoverdose.mys3.service.AuthService;
import bugoverdose.mys3.service.ImageService;
import bugoverdose.mys3.service.dto.UploadImageRequestDto;
import java.io.InputStream;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ImageController {

    private static final String AUTHORIZATION = "Authorization";

    private final ImageService imageService;
    private final AuthService authService;

    public ImageController(ImageService imageService,
                           AuthService authService) {
        this.imageService = imageService;
        this.authService = authService;
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
                                                           @ModelAttribute MultipartFile imageFile,
                                                           HttpServletRequest request) {
        authService.validate(request.getHeader(AUTHORIZATION));
        String imagePath = imageService.saveOrUpdate(new UploadImageRequestDto(uploadPath, fileName, imageFile));
        return ResponseEntity.created(URI.create("/images/" + imagePath))
                .body(new UploadImageResponse(imagePath));
    }
}
