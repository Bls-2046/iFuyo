package com.github.ifuyo.apps.navigation.schedule;

/*
  使用功能前可以选择是否使用微信订阅号提醒
  在页面上分别有日期、事件标题和时间具体名称
  输入的日程安排会存放到数据库 ( 数据库定期检查日程安排并清理过期时间事务 )
  多线程：界面操作与时间监听并行
  时间对应事件在桌面右下角弹出提示框, 提示框内容包括: 日期、标题、具体安排, 并在微信订阅号向用户发送提示信息
 */

public class ScheduleModel {
    public ScheduleModel() {}
}
