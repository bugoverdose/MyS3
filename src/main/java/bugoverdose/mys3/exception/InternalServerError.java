package bugoverdose.mys3.exception;

public class InternalServerError extends RuntimeException {

    public InternalServerError(String message) {
        super(message);
    }

    public static InternalServerError ofFileSaveFailure() {
        return new InternalServerError("이미지 업로드에 실패히였습니다.");
    }

    public static InternalServerError ofFileDeleteFailure() {
        return new InternalServerError("이미지 삭제에 실패히였습니다.");
    }
}
