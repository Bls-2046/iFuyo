package com.github.ifuyo.apps.login;

import com.github.ifuyo.apps.login.ui.LoginView;
import com.github.ifuyo.apps.navigation.profile.ui.Profile;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginController implements ActionListener {
    private LoginView view;
    private LoginModel model;

    public LoginController(LoginView view, LoginModel model) {
        this.view = view;
        this.model = model;
        this.view.setVerse(model.getVerse());
        this.view.setAuthor(model.getAuthor());

        // 绑定监听器
        view.LoginListener(this);
    }

    public LoginView getView() {
        return view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 保存按钮的原始文本
        String originalButtonText = view.getLoginButtonText();

        // 设置按钮为加载状态
        view.setLoginButtonText("登录中...");
        view.setLoginButtonEnabled(false);

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
                    view.setLoginButtonText(originalButtonText);
                    view.setLoginButtonEnabled(true);
                    view.clearInputs();
                });
            }
        }).start(); // 启动线程
    }
}
