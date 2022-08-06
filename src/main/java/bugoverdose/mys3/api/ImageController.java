package bugoverdose.mys3.api;

import bugoverdose.mys3.service.ImageService;
import bugoverdose.mys3.service.dto.UploadImageCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/images/{uploadPath}")
    public ResponseEntity<Void> saveImage(@PathVariable String uploadPath,
                                          @RequestParam(defaultValue = "") String fileName,
                                          @ModelAttribute MultipartFile image) {
        imageService.upload(new UploadImageCommand(uploadPath, fileName, image));
        return ResponseEntity.ok().build();
    }
}
