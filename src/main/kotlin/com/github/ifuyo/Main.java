package com.github.ifuyo;

import com.github.ifuyo.apps.login.LoginController;
import com.github.ifuyo.apps.login.LoginView;
import com.github.ifuyo.apps.login.LoginModel;
import static com.github.ifuyo.config.Settings.initApplication;

public class Main {
    public static void main(String[] args) {
        // 创建并显示界面
        try {
            initApplication();
            new LoginController(new LoginView(), new LoginModel()).getView().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
