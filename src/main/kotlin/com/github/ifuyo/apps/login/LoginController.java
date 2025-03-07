package com.github.ifuyo.apps.login;

import com.github.ifuyo.apps.navigation.profile.ui.Profile;
import lombok.Getter;
import org.json.JSONObject;
import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LoginController {
    @Getter
    private final LoginView view;
    private final LoginModel model;

    private static final String FILE_PATH =System.getenv("LOCALAPPDATA") + "\\iFuyo\\" + "credentials.txt";

    public LoginController(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;

        // 绑定监听器
        view.getLoginButton().addActionListener(e -> handleLogin());
        view.getExitProgramButton().addActionListener(e -> handleExit());

        // 判断是否自动登录
        Path path = Paths.get(FILE_PATH);
        if (Files.exists(path)) {
            autoLogin();
        }
    }

    // 自动登录
    private void autoLogin() {
        view.setLoginButtonEnabled(false);
        new Thread(() -> {
            try {
                String[] FileContent =  model.readCredentials();
                String username = FileContent[0];
                String password = FileContent[1];
                view.getUsernameInput().setText(username);
                view.getPasswordInput().setText(password);

                if (username.isEmpty() || password.isEmpty()) {
                    view.showErrorFrame("登录已过期, 请重试");
                    model.deleteCredentials();
                    return;
                }
                // 调用 Model 的验证逻辑
                isValidateLogin(username, password);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // 手动登录
    private void handleLogin() {
        view.setLoginButtonEnabled(false);
        new Thread(() -> {
            try {
                String username = view.getUsername();
                String password = view.getPassword();

                 // 判断用户名密码是否为空
                if (username.isEmpty() || password.isEmpty()) {
                    view.showErrorFrame("用户名或密码不为空");
                    model.deleteCredentials();
                    return;
                }

                // 调用 Model 的验证逻辑
                isValidateLogin(username, password);
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    view.showErrorFrame("登录失败，请重试");
                    try {
                        model.deleteCredentials();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            } finally {
                SwingUtilities.invokeLater(() -> {
                    view.setLoginButtonEnabled(true); // 解锁登录按钮
                    view.clearInputs();
                });
            }
        }).start(); // 启动线程
    }

    // 退出应用
    private void handleExit() {
        System.exit(0);
    }

    // 密码验证并返回状态
    private void isValidateLogin(String username, String password) {
        JSONObject jsonResponse = model.validateLogin(username, password);
        if (jsonResponse != null && jsonResponse.has("message")) {
            String message = jsonResponse.getString("message");
            if ("登录成功".equals(message)) {
                // 登录成功，显示消息并跳转到菜单界面
                try {
                    model.saveCredentials(username, password);
                    System.out.println("已创建文件");
                    SwingUtilities.invokeLater(() -> {
                        view.dispose();
                        new Profile(jsonResponse).setVisible(true);
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // 登录失败，显示错误消息
                SwingUtilities.invokeLater(() -> {
                    try {
                        model.deleteCredentials();
                        view.showErrorFrame(message);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }
}
