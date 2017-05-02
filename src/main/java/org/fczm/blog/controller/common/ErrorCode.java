package org.fczm.blog.controller.common;

public enum ErrorCode {
    // Admin
    ErrorAdminSession(801, "Admin's session is timeout."),
    ErrorObjecId(802, "Object cannot be found by this cid."),

    // Basic
    ErrorToken(901, "Token is wrong."),

    // User
    ErrorEmailExist(1011, "This email has been registered.");

    public int code;
    public String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
