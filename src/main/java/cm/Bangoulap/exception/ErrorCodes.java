package cm.Bangoulap.exception;

public enum ErrorCodes {

    ENTITY_ALREADY_EXIST(1000),
    INVALID_ENTITY(1001),
    ENTITY_NOT_EXIST(1002);

    private int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
