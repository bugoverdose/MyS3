package bugoverdose.mys3.api;

import bugoverdose.mys3.service.AuthService;
import bugoverdose.mys3.service.dto.UploadImageCommand;
import bugoverdose.mys3.api.dto.UploadImageResponse;
import bugoverdose.mys3.service.ImageService;
import java.io.InputStream;
import java.net.URI;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
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

    @PostMapping("/images/{uploadPath}")
    public ResponseEntity<UploadImageResponse> uploadImage(@PathVariable String uploadPath,
                                                           @RequestParam(defaultValue = "") String fileName,
                                                           @ModelAttribute MultipartFile image,
                                                           HttpServletRequest request) {
        authService.validate(request.getHeader(AUTHORIZATION));
        String imagePath = imageService.saveOrUpdate(new UploadImageCommand(uploadPath, fileName, image));
        return ResponseEntity.created(URI.create("/api/images/" + imagePath))
                .body(new UploadImageResponse(imagePath));
    }

    @GetMapping("/images/{uploadPath}/{fileName}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String uploadPath,
                                                        @PathVariable String fileName) {
        InputStream imageInputStream = imageService.find(uploadPath, fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(imageInputStream));
    }
}
