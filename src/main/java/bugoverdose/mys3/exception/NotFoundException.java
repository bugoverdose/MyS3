package bugoverdose.mys3.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException ofImage() {
       return new NotFoundException("존재하지 않는 이미지입니다.");
    }
}
