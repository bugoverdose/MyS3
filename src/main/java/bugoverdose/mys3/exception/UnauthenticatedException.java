package bugoverdose.mys3.exception;

public class UnauthenticatedException extends RuntimeException {

    public UnauthenticatedException(String message) {
        super(message);
    }

    public static UnauthenticatedException ofMissingAuthCode() {
      return new UnauthenticatedException("인증 코드 정보가 누락되었습니다.");
    }

    public static UnauthenticatedException ofInvalidAuthCode() {
        return new UnauthenticatedException("잘못된 인증 정보입니다.");
    }
}
