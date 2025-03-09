package com.github.ifuyo.entity;

import org.json.JSONObject;

public class StudentEntity {
    private StudentEntity() {}
    // 单例实例
    private static StudentEntity studentInfo;

    public static StudentEntity getStudentInfo() {
        if (studentInfo == null) {
            studentInfo = new StudentEntity();
        }
        return studentInfo;
    }


    // 学生信息属性
    private String id; // 学号
    private String name; // 姓名
    private String department; // 部门
    private String phone; // 手机
    private String email; // 邮箱
    private String lastLoginTime; // 上次登录时间
    private String lastLoginIP; // 上次登录 IP
//    private List<CourseInfo> courseSchedule; // 课表

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

    // 解析 JSON 数据并赋值给实例变量
    public void parseAndSetStudentInfo(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        if (jsonObject.has("data") && !jsonObject.isNull("data")) {
            JSONObject data = jsonObject.getJSONObject("data");

            // 设置学生信息
            this.setId(data.getString("工号"));
            this.setName(data.getString("姓名"));
            this.setDepartment(data.getString("部门"));
            this.setPhone(data.getString("手机"));
            this.setEmail(data.getString("邮箱"));
            this.setLastLoginTime(data.getString("上次登录"));
            this.setLastLoginIP(data.getString("上次登录IP"));
        }
    }

//    public List<CourseInfo> getCourseSchedule() {
//        return courseSchedule;
//    }
//
//    public void setCourseSchedule(List<CourseInfo> courseSchedule) {
//        this.courseSchedule = courseSchedule;
//    }

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
                '}';
    }

//    // 课程信息类（内部类）
//    public static class CourseInfo {
//        // 课程信息属性
//        private String timeSlot; // 时间段
//        private String day; // 星期几
//        private String courseInfo; // 课程详情
//
//        // 构造方法
//        public CourseInfo() {}
//
//        public CourseInfo(String timeSlot, String day, String courseInfo) {
//            this.timeSlot = timeSlot;
//            this.day = day;
//            this.courseInfo = courseInfo;
//        }
//
//        // Getter 和 Setter 方法
//        public String getTimeSlot() {
//            return timeSlot;
//        }
//
//        public void setTimeSlot(String timeSlot) {
//            this.timeSlot = timeSlot;
//        }
//
//        public String getDay() {
//            return day;
//        }
//
//        public void setDay(String day) {
//            this.day = day;
//        }
//
//        public String getCourseInfo() {
//            return courseInfo;
//        }
//
//        public void setCourseInfo(String courseInfo) {
//            this.courseInfo = courseInfo;
//        }
//
//        @Override
//        public String toString() {
//            return "CourseInfo{" +
//                    "timeSlot='" + timeSlot + '\'' +
//                    ", day='" + day + '\'' +
//                    ", courseInfo='" + courseInfo + '\'' +
//                    '}';
//        }
//    }
}
