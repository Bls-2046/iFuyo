/*
 * Created by JFormDesigner on Mon Mar 03 21:03:50 CST 2025
 */

package com.github.ifuyo.apps.profile.ui;

import org.json.JSONObject;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author astar
 */
public class Profile extends JFrame {
    public Profile() {
        initComponents();
    }

    public Profile(JSONObject jsonResponse) {
        initComponents();
        initHomeContent();
        addCardeners();
    }

    private BorderLayout borderLayout;

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        Header = new JPanel();
        label1 = new JLabel();
        HeaderContent = new JPanel();
        label2 = new JLabel();
        label3 = new JLabel();
        label6 = new JLabel();
        Sidebar = new JPanel();
        AppName = new JPanel();
        label4 = new JLabel();
        panel6 = new JPanel();
        separator2 = new JSeparator();
        label7 = new JLabel();
        label8 = new JLabel();
        LittleTitle = new JLabel();
        NavBar = new JPanel();
        NavBarOptions = new JPanel();
        ToHomeButton = new JButton();
        ToProfileButton = new JButton();
        ToTimetableButton = new JButton();
        ToScheduleButton = new JButton();
        ToDeepSeekButton = new JButton();
        ToWebButton = new JButton();
        ToConnectButton = new JButton();
        Footer = new JPanel();
        separator3 = new JSeparator();
        FooterContent = new JLabel();
        HomeContent = new JPanel();
        GoOthorWeb = new JPanel();
        contactMethod = new JPanel();

        //======== this ========
        setIconImage(new ImageIcon(getClass().getResource("/Logo.png")).getImage());
        setTitle("iFuyo");
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== Header ========
        {
            Header.setBorder(LineBorder.createBlackLineBorder());
            Header.setLayout(new BorderLayout());

            //---- label1 ----
            label1.setText("                  ");
            Header.add(label1, BorderLayout.NORTH);

            //======== HeaderContent ========
            {
                HeaderContent.setLayout(new BorderLayout());

                //---- label2 ----
                label2.setText("        ");
                HeaderContent.add(label2, BorderLayout.WEST);

                //---- label3 ----
                label3.setText("\u4f60\u597d\uff0c~");
                label3.setFont(new Font("\u6c49\u4eea\u53f6\u53f6\u76f8\u601d\u4f53\u7b80", Font.PLAIN, 20));
                HeaderContent.add(label3, BorderLayout.CENTER);

                //---- label6 ----
                label6.setText("     ");
                HeaderContent.add(label6, BorderLayout.SOUTH);
            }
            Header.add(HeaderContent, BorderLayout.SOUTH);
        }
        contentPane.add(Header, BorderLayout.NORTH);

        //======== Sidebar ========
        {
            Sidebar.setBorder(LineBorder.createBlackLineBorder());
            Sidebar.setLayout(new BorderLayout());

            //======== AppName ========
            {
                AppName.setLayout(new BorderLayout());

                //---- label4 ----
                label4.setText("      ");
                AppName.add(label4, BorderLayout.NORTH);

                //======== panel6 ========
                {
                    panel6.setLayout(new BorderLayout());

                    //---- separator2 ----
                    separator2.setForeground(Color.black);
                    panel6.add(separator2, BorderLayout.CENTER);

                    //---- label7 ----
                    label7.setText("       ");
                    panel6.add(label7, BorderLayout.NORTH);

                    //---- label8 ----
                    label8.setText("            ");
                    panel6.add(label8, BorderLayout.SOUTH);
                }
                AppName.add(panel6, BorderLayout.SOUTH);

                //---- LittleTitle ----
                LittleTitle.setText("     \u8f85 \u4f51     ");
                LittleTitle.setForeground(Color.darkGray);
                LittleTitle.setFont(new Font("\u6c49\u4eea\u53f6\u53f6\u76f8\u601d\u4f53\u7b80", Font.BOLD, 22));
                AppName.add(LittleTitle, BorderLayout.CENTER);
            }
            Sidebar.add(AppName, BorderLayout.NORTH);

            //======== NavBar ========
            {
                NavBar.setLayout(new BorderLayout());

                //======== NavBarOptions ========
                {
                    NavBarOptions.setLayout(new GridLayout(7, 1, 20, 20));

                    //---- ToHomeButton ----
                    ToHomeButton.setText("\ud83c\udfe0 \u9996\u9875");
                    ToHomeButton.setFont(ToHomeButton.getFont().deriveFont(ToHomeButton.getFont().getSize() + 5f));
                    ToHomeButton.setForeground(Color.darkGray);
                    NavBarOptions.add(ToHomeButton);

                    //---- ToProfileButton ----
                    ToProfileButton.setText(" \ud83c\udf1f \u4e2a\u4eba\u8d44\u6599");
                    ToProfileButton.setForeground(Color.darkGray);
                    ToProfileButton.setFont(ToProfileButton.getFont().deriveFont(ToProfileButton.getFont().getSize() + 5f));
                    NavBarOptions.add(ToProfileButton);

                    //---- ToTimetableButton ----
                    ToTimetableButton.setText("\ud83d\udcc5  \u8bfe\u8868");
                    ToTimetableButton.setForeground(Color.darkGray);
                    ToTimetableButton.setFont(ToTimetableButton.getFont().deriveFont(ToTimetableButton.getFont().getSize() + 5f));
                    NavBarOptions.add(ToTimetableButton);

                    //---- ToScheduleButton ----
                    ToScheduleButton.setText("\u2600 \u65e5\u7a0b\u5b89\u6392");
                    ToScheduleButton.setForeground(Color.darkGray);
                    ToScheduleButton.setFont(ToScheduleButton.getFont().deriveFont(ToScheduleButton.getFont().getSize() + 5f));
                    NavBarOptions.add(ToScheduleButton);

                    //---- ToDeepSeekButton ----
                    ToDeepSeekButton.setText(" \ud83d\udc0b DeepSeek");
                    ToDeepSeekButton.setFont(ToDeepSeekButton.getFont().deriveFont(ToDeepSeekButton.getFont().getSize() + 5f));
                    ToDeepSeekButton.setForeground(Color.darkGray);
                    NavBarOptions.add(ToDeepSeekButton);

                    //---- ToWebButton ----
                    ToWebButton.setText("\ud83c\udf0f \u4fbf\u6377\u7f51\u7ad9");
                    ToWebButton.setFont(ToWebButton.getFont().deriveFont(ToWebButton.getFont().getSize() + 5f));
                    ToWebButton.setForeground(Color.darkGray);
                    NavBarOptions.add(ToWebButton);

                    //---- ToConnectButton ----
                    ToConnectButton.setText("\ud83d\udcde\u8054\u7cfb\u6211\u4eec");
                    ToConnectButton.setForeground(Color.darkGray);
                    ToConnectButton.setFont(ToConnectButton.getFont().deriveFont(ToConnectButton.getFont().getSize() + 5f));
                    NavBarOptions.add(ToConnectButton);
                }
                NavBar.add(NavBarOptions, BorderLayout.NORTH);

                //======== Footer ========
                {
                    Footer.setLayout(new BorderLayout());
                    Footer.add(separator3, BorderLayout.SOUTH);

                    //---- FooterContent ----
                    FooterContent.setText("               @ 2025 Bls-2046");
                    FooterContent.setForeground(Color.darkGray);
                    FooterContent.setFont(FooterContent.getFont().deriveFont(FooterContent.getFont().getSize() - 2f));
                    Footer.add(FooterContent, BorderLayout.CENTER);
                }
                NavBar.add(Footer, BorderLayout.SOUTH);
            }
            Sidebar.add(NavBar, BorderLayout.CENTER);
        }
        contentPane.add(Sidebar, BorderLayout.WEST);

        //======== HomeContent ========
        {
            HomeContent.setBorder(LineBorder.createBlackLineBorder());
            HomeContent.setLayout(new BorderLayout());
        }
        contentPane.add(HomeContent, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

        //======== GoOthorWeb ========
        {
            GoOthorWeb.setBorder(LineBorder.createBlackLineBorder());
            GoOthorWeb.setLayout(new BorderLayout());
        }

        //======== contactMethod ========
        {
            contactMethod.setBorder(LineBorder.createBlackLineBorder());
            contactMethod.setLayout(new BorderLayout());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void initHomeContent() {
        // 默认显示Home面板
        showHomePanel();
    }

    private void showHomePanel() {
        // 移除当前内容
        if (HomeContent.getComponentCount() > 0) {
            HomeContent.removeAll();
        }

        // 添加新面板
        HomeContent.add(createHomePanel(), BorderLayout.CENTER);

        // 刷新界面
        HomeContent.revalidate();
        HomeContent.repaint();
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("<html><h1>欢迎来到首页</h1>这里是首页内容区域</html>"), BorderLayout.CENTER);
        return panel;
    }

    private void showProfilePanel() {
        // 移除当前内容
        if (HomeContent.getComponentCount() > 0) {
            HomeContent.removeAll();
        }

        // 添加新面板
        HomeContent.add(createProfilePanel(), BorderLayout.CENTER);

        // 刷新界面
        HomeContent.revalidate();
        HomeContent.repaint();
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("<html><h1>个人资料</h1>邮箱：student@ifuyo.edu</html>"), BorderLayout.CENTER);
        return panel;
    }

    private void addCardeners() {
        // 绑定Home按钮事件
        ToHomeButton.addActionListener(e -> showHomePanel());
        // 绑定Profile按钮事件
        ToProfileButton.addActionListener(e -> showProfilePanel());
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel Header;
    private JLabel label1;
    private JPanel HeaderContent;
    private JLabel label2;
    private JLabel label3;
    private JLabel label6;
    private JPanel Sidebar;
    private JPanel AppName;
    private JLabel label4;
    private JPanel panel6;
    private JSeparator separator2;
    private JLabel label7;
    private JLabel label8;
    private JLabel LittleTitle;
    private JPanel NavBar;
    private JPanel NavBarOptions;
    private JButton ToHomeButton;
    private JButton ToProfileButton;
    private JButton ToTimetableButton;
    private JButton ToScheduleButton;
    private JButton ToDeepSeekButton;
    private JButton ToWebButton;
    private JButton ToConnectButton;
    private JPanel Footer;
    private JSeparator separator3;
    private JLabel FooterContent;
    private JPanel HomeContent;
    private JPanel GoOthorWeb;
    private JPanel contactMethod;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
