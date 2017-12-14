package com.app.swishd.retrofit;

public enum ResponseCode {

    Success(200),
    NotFound(404),
    AlreadyExist(409),
    Validation(422),
    SomethingWrong(405),
    Unauthorised(401),
    PreconditionFailed(412);

    private int responseCode;

    ResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int value() {
        return responseCode;
    }
}
