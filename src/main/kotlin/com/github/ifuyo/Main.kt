package com.github.ifuyo;// 主入口

import javax.swing.SwingUtilities
import com.github.ifuyo.apps.login.ui.Login
import com.github.ifuyo.config.Settings

fun main(args: Array<String>) {
    Settings.initialize()

    SwingUtilities.invokeLater {
        val loginGUI = Login()
        loginGUI.isVisible = true
    }
}