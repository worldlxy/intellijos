package com.zte.os.oncall24.service;

public interface LoginStateListener {
    void loginSuccess(String userName);
    void loginFailed();
}
