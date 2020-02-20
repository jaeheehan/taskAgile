package com.taskagile.app.web.payload;

public class AddBoardMemberPayload {

    private String usernameOrEmailAddress;

    public String getUsernameOrEmailAddress() {
        return usernameOrEmailAddress;
    }

    public void setUsernameOrEmailAddress(String usernameOrEmailAddress) {
        this.usernameOrEmailAddress = usernameOrEmailAddress;
    }
}

