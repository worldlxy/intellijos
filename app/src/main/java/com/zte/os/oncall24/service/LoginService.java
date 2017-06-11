package com.zte.os.oncall24.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginService {

    public void login(final LoginStateListener loginStateListener, final String userName, final String password) {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        try {
                            String path = "http://intellijos.applinzi.com/login";
                            path = path + "?" + "username=" + userName + "&" + "password=" + password;
                            URL url = new URL(path);
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setRequestProperty("Charset", "UTF-8");
                            connection.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");

                            if (connection.getResponseCode() == 200) {
                                loginStateListener.loginSuccess(userName);
                            } else {
                                loginStateListener.loginFailed();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            loginStateListener.loginFailed();
                        } finally {
                            if (connection != null) {
                                connection.disconnect();
                            }
                        }
                    }
                }
        ).start();
    }
}