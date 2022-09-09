package bugoverdose.mys3.exception;

public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }

    public static InvalidRequestException ofFileMissing() {
        return new InvalidRequestException("업로드할 파일이 존재하지 않습니다.");
    }

    public static InvalidRequestException ofNonImageType() {
        return new InvalidRequestException("이미지 파일만 업로드 가능합니다.");
    }
}
