package com.github.ifuyo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginEntity {
    private JTextField usernameObject;
    private JPasswordField passwordObject;
    private JButton loginButtonObject;
    private JButton exitProgramButtonObject;
    private JLabel[] errorFrameDisp;
}
