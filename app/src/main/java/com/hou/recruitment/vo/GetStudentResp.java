package com.hou.recruitment.vo;

import java.io.Serializable;

/**
 * @author Fred Liu(liuxiaokun0410@gmail.com)
 * @version v1.0.0
 * @since 2016-04-16 16:16
 */
public class GetStudentResp implements Serializable{

    private StudentInfo student;


    public StudentInfo getStudent() {
        return student;
    }

    public void setStudent(StudentInfo student) {
        this.student = student;
    }
}
