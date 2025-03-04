/*
 * Created by JFormDesigner on Mon Mar 03 12:10:28 CST 2025
 */

package com.github.ifuyo.apps.login.ui;

import org.json.JSONObject;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.swing.*;
import com.github.ifuyo.apps.profile.ui.Profile;

/**
 * @author astar
 */
public class Login extends JFrame {
    public Login() {
        initComponents();
        addLoginButtonListener();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        label1 = new JLabel();
        button1 = new JButton();
        label2 = new JLabel();
        label3 = new JLabel();
        textField1 = new JTextField();
        label4 = new JLabel();
        passwordField1 = new JPasswordField();
        label5 = new JLabel();
        label7 = new JLabel();
        label8 = new JLabel();
        label9 = new JLabel();
        separator1 = new JSeparator();

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/Logo.png")).getImage());
        setTitle("iFuyo");
        setBackground(Color.orange);
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //---- label1 ----
        label1.setText("\u8f85\u4f51");
        label1.setFont(new Font("\u6c49\u4eea\u53f6\u53f6\u76f8\u601d\u4f53\u7b80", Font.PLAIN, 80));
        label1.setForeground(Color.darkGray);
        contentPane.add(label1);
        label1.setBounds(170, 15, label1.getPreferredSize().width, 90);

        //---- button1 ----
        button1.setText("\u767b  \u5f55");
        button1.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 20));
        button1.setBackground(new Color(0xfbaf5e));
        button1.setForeground(Color.white);
        contentPane.add(button1);
        button1.setBounds(355, 285, 120, 43);

        //---- label2 ----
        label2.setIcon(new ImageIcon(getClass().getResource("/Logo.png")));
        contentPane.add(label2);
        label2.setBounds(new Rectangle(new Point(365, 130), label2.getPreferredSize()));

        //---- label3 ----
        label3.setText("\u7528\u6237\u540d\uff1a");
        label3.setFont(label3.getFont().deriveFont(label3.getFont().getStyle() | Font.BOLD, label3.getFont().getSize() + 10f));
        label3.setForeground(Color.darkGray);
        contentPane.add(label3);
        label3.setBounds(80, 160, 90, 40);

        //---- textField1 ----
        textField1.setFont(textField1.getFont().deriveFont(textField1.getFont().getStyle() & ~Font.BOLD, textField1.getFont().getSize() + 5f));
        textField1.setHorizontalAlignment(SwingConstants.LEFT);
        contentPane.add(textField1);
        textField1.setBounds(160, 160, 240, 45);

        //---- label4 ----
        label4.setText("\u5bc6\u7801\uff1a");
        label4.setFont(label4.getFont().deriveFont(label4.getFont().getStyle() | Font.BOLD, label4.getFont().getSize() + 10f));
        label4.setForeground(Color.darkGray);
        contentPane.add(label4);
        label4.setBounds(90, 225, label4.getPreferredSize().width, 40);

        //---- passwordField1 ----
        passwordField1.setFont(passwordField1.getFont().deriveFont(passwordField1.getFont().getStyle() & ~Font.BOLD, passwordField1.getFont().getSize() + 5f));
        contentPane.add(passwordField1);
        passwordField1.setBounds(160, 225, 250, 45);
        contentPane.add(label5);
        label5.setBounds(new Rectangle(new Point(60, 60), label5.getPreferredSize()));

        //---- label7 ----
        label7.setText("Fuyo");
        label7.setFont(new Font("Yu Gothic UI", Font.PLAIN, 40));
        label7.setForeground(new Color(0x666666));
        label7.setBackground(new Color(0xcccccc));
        contentPane.add(label7);
        label7.setBounds(new Rectangle(new Point(325, 35), label7.getPreferredSize()));

        //---- label8 ----
        label8.setFont(label8.getFont().deriveFont(label8.getFont().getSize() + 2f));
        contentPane.add(label8);
        label8.setBounds(new Rectangle(new Point(110, 105), label8.getPreferredSize()));

        //---- label9 ----
        label9.setText("\u968f\u673a\u8bd7\u53e5");
        label9.setFont(new Font("\u6c49\u4eea\u53f6\u53f6\u76f8\u601d\u4f53\u7b80", Font.PLAIN, 16));
        contentPane.add(label9);
        label9.setBounds(new Rectangle(new Point(100, 105), label9.getPreferredSize()));

        //---- separator1 ----
        separator1.setBackground(new Color(0xcccccc));
        separator1.setForeground(new Color(0x333333));
        contentPane.add(separator1);
        separator1.setBounds(130, 80, 300, 10);

        contentPane.setPreferredSize(new Dimension(550, 345));
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    private void addLoginButtonListener() {
        button1.addActionListener(e -> {
            // 保存按钮的原始文本
            String originalButtonText = button1.getText();

            // 设置按钮为加载状态
            button1.setText("登录中...");
            button1.setEnabled(false); // 禁用按钮，防止重复点击

            // 启动一个新线程执行耗时操作，避免阻塞 UI 线程
            new Thread(() -> {
                try {
                    String username = textField1.getText();
                    String password = new String(passwordField1.getPassword());

                    // 调用 Python 脚本验证密码
                    String pythonInterpreter = ".venv\\Scripts\\python.exe";
                    String scriptPath = "src/main/resources/python/login_bitzh.py";

                    // 启动 Python 进程
                    Process process = Runtime.getRuntime().exec(new String[]{pythonInterpreter, scriptPath, username, password});

                    // 读取 Python 脚本的标准输出
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
                    StringBuilder output = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line);
                    }
                    String result = output.toString();
                    System.out.println("Raw Python Output: " + result);

                    // 解析 JSON 响应
                    JSONObject jsonResponse = new JSONObject(result);
                    if (jsonResponse.has("message")) {
                        String message = jsonResponse.getString("message");
                        if ("登录成功".equals(message)) {
                            // 登录成功，显示消息并跳转到菜单界面
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(Login.this, "登录成功");
                                dispose();
                                new Profile(jsonResponse).setVisible(true);
                            });
                        } else {
                            // 登录失败，显示错误消息
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(Login.this, message, "登录失败", JOptionPane.ERROR_MESSAGE);
                            });
                        }
                    } else {
                        // 未知错误
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(Login.this, "未知错误", "错误", JOptionPane.ERROR_MESSAGE);
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(Login.this, "登录失败，请重试", "错误", JOptionPane.ERROR_MESSAGE);
                    });
                } finally {
                    // 恢复按钮的原始状态
                    SwingUtilities.invokeLater(() -> {
                        button1.setText(originalButtonText);
                        button1.setEnabled(true);
                    });

                    // 清空输入框
                    textField1.setText("");
                    passwordField1.setText("");
                }
            }).start(); // 启动线程
        });
    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel label1;
    private JButton button1;
    private JLabel label2;
    private JLabel label3;
    private JTextField textField1;
    private JLabel label4;
    private JPasswordField passwordField1;
    private JLabel label5;
    private JLabel label7;
    private JLabel label8;
    private JLabel label9;
    private JSeparator separator1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
