package com.github.ifuyo.apps.login;

import com.github.ifuyo.apps.navigation.profile.ui.Profile;
import lombok.Getter;
import org.json.JSONObject;
import javax.swing.*;

public class LoginController {
    @Getter
    private final LoginView view;
    private final LoginModel model;

    public LoginController(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;

        // 绑定监听器
        view.getLoginButton().addActionListener(e -> handleLogin());
        view.getExitProgramButton().addActionListener(e -> handleExit());
    }

    private void handleLogin() {
        // 设置按钮为加载状态
//        view.setLoginButtonText("登录中...");
//        view.setLoginButtonEnabled(false);

        // 启动一个新线程执行耗时操作，避免阻塞 UI 线程
        new Thread(() -> {
            try {
                String username = view.getUsername();
                String password = view.getPassword();

                // 调用 Model 的验证逻辑
                JSONObject jsonResponse = model.validateLogin(username, password);
                if (jsonResponse != null && jsonResponse.has("message")) {
                    String message = jsonResponse.getString("message");
                    if ("登录成功".equals(message)) {
                        // 登录成功，显示消息并跳转到菜单界面
                        SwingUtilities.invokeLater(() -> {
                            view.showMessage("登录成功");
                            view.dispose();
                            new Profile(jsonResponse).setVisible(true);
                        });
                    } else {
                        // 登录失败，显示错误消息
                        SwingUtilities.invokeLater(() -> {
                            view.showError(message);
                        });
                    }
                } else {
                    // 未知错误
                    SwingUtilities.invokeLater(() -> {
                        view.showError("未知错误");
                    });
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    view.showError("登录失败，请重试");
                });
            } finally {
                // 恢复按钮的原始状态
                SwingUtilities.invokeLater(() -> {
//                    view.setLoginButtonText(originalButtonText);
//                    view.setLoginButtonEnabled(true);
                    view.clearInputs();
                });
            }
        }).start(); // 启动线程
    }

    private void handleExit() {
        System.exit(0);
    }
}
