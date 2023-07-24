package exception;

public class UserServiceException extends RuntimeException {
    public static final String DUPLICATED_ID = "이미 등록된 userId 입니다";

    public UserServiceException(String errorMessage) {
        super(errorMessage);
    }

    public static UserServiceException duplicatedId() {
        return new UserServiceException(DUPLICATED_ID);
    }
}
