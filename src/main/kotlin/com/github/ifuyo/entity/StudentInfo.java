package com.github.ifuyo.entity;

import lombok.Data;
import lombok.Getter;

import java.util.List;

public class StudentInfo {
    // 单例实例
    private static StudentInfo instance;

    // 学生信息属性
    private String id; // 学号
    private String name; // 姓名
    private String department; // 部门
    private String phone; // 手机
    private String email; // 邮箱
    private String lastLoginTime; // 上次登录时间
    private String lastLoginIP; // 上次登录 IP
    private List<CourseInfo> courseSchedule; // 课表

    // 私有构造方法，防止外部实例化
    private StudentInfo() {}

    // 获取单例实例
    public static StudentInfo getStudentInfo() {
        if (instance == null) {
            instance = new StudentInfo();
        }
        return instance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public List<CourseInfo> getCourseSchedule() {
        return courseSchedule;
    }

    public void setCourseSchedule(List<CourseInfo> courseSchedule) {
        this.courseSchedule = courseSchedule;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", lastLoginTime='" + lastLoginTime + '\'' +
                ", lastLoginIP='" + lastLoginIP + '\'' +
                ", courseSchedule=" + courseSchedule +
                '}';
    }

    // 课程信息类（内部类）
    public static class CourseInfo {
        // 课程信息属性
        private String timeSlot; // 时间段
        private String day; // 星期几
        private String courseInfo; // 课程详情

        // 构造方法
        public CourseInfo() {}

        public CourseInfo(String timeSlot, String day, String courseInfo) {
            this.timeSlot = timeSlot;
            this.day = day;
            this.courseInfo = courseInfo;
        }

        // Getter 和 Setter 方法
        public String getTimeSlot() {
            return timeSlot;
        }

        public void setTimeSlot(String timeSlot) {
            this.timeSlot = timeSlot;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getCourseInfo() {
            return courseInfo;
        }

        public void setCourseInfo(String courseInfo) {
            this.courseInfo = courseInfo;
        }

        @Override
        public String toString() {
            return "CourseInfo{" +
                    "timeSlot='" + timeSlot + '\'' +
                    ", day='" + day + '\'' +
                    ", courseInfo='" + courseInfo + '\'' +
                    '}';
        }
    }
}
