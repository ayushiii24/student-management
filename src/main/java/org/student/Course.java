package org.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Course {

    @Id
    private int CId;
    private String CourseName;

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + CourseName + '\'' +
                '}';
    }

    public int getCId() {
        return CId;
    }

    public void setCId(int CId) {
        this.CId = CId;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        this.CourseName = courseName;
    }
}
