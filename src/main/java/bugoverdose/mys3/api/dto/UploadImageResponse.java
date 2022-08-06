package bugoverdose.mys3.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UploadImageResponse {

    private final Boolean ok = true;
    private final String error = null;
    private String imagePath;

    public UploadImageResponse(String imagePath) {
        this.imagePath = imagePath;
    }
}
