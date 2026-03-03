package org.student;

import jakarta.persistence.*;
import org.hibernate.service.spi.InjectService;

@Entity
@Table(name = "student")
public class Student {

    @Id
    private int roll;
    private String name;
    private String email;
    @ManyToOne
    @JoinColumn(name = "CId")
    private Course course;
    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "roll=" + roll +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", course=" + course +
                '}';
    }
}
