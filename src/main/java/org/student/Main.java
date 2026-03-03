package org.student;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sf = new Configuration().configure().addAnnotatedClass(Student.class).addAnnotatedClass(Course.class).buildSessionFactory();

        Scanner sc = new Scanner(System.in);

        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        Course c1 = new Course();
        c1.setCId(101);
        c1.setCourseName("BTECH");

        Course c2 = new Course();
        c2.setCId(102);
        c2.setCourseName("LLB");

        Course c3 = new Course();
        c3.setCId(103);
        c3.setCourseName("MBA");

        if (session.find(Course.class, 101) == null) {
            session.persist(c1);
        }
        if (session.find(Course.class, 102) == null) {
            session.persist(c2);
        }
        if (session.find(Course.class, 103) == null) {
            session.persist(c3);
        }

        tx.commit();
        session.close();


        while (true) {
            System.out.println("\n1. Add a student");
            System.out.println("2. Update a student");
            System.out.println("3. Delete a student");
            System.out.println("4. View students");
            System.out.println("5. EXIT");

            System.out.println("CHOOSE : ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addStudent(sf, sc);
                case 2 -> updateStudent(sf, sc);
                case 3 -> deleteStudent(sf, sc);
                case 4 -> allStudent(sf);
                case 5 -> {
                    System.out.println("Code exited.");
                    return;
                }
                default -> System.out.println("Invalid choice! ");

            }
        }

    }
            private static Student addStudent(SessionFactory sf, Scanner sc){
                System.out.println("Enter roll : ");
                int roll = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter name : ");
                String name = sc.nextLine();
                System.out.println("Enter email : ");
                String email = sc.nextLine();

                Session session = sf.openSession();
                Transaction t = session.beginTransaction();

                System.out.println("Available courses are : ");
                Query query = session.createQuery("from Course",Course.class);
                List <Course> courseList = query.getResultList();
                for (Course c : courseList){
                    System.out.println(c.getCId()+ " " +c.getCourseName());
                }
                System.out.println("Select course (type course-id) : ");
                int courseId = sc.nextInt();
                Course selectedC = session.find(Course.class,courseId);
                if (selectedC == null) {
                    System.out.println("Invalid course id!");
                    System.out.println("Failed to add student :(");
                    session.close();
                    return null;
                }
                Student s = new Student();
                s.setRoll(roll);
                s.setName(name);
                s.setEmail(email);
                s.setCourse(selectedC);

                Student existing = session.find(Student.class, roll);
                if (existing != null) {
                    System.out.println("Roll already exists! ");
                    System.out.println("Failed to add student :(");
                    session.close();
                    return null;
                }
                session.persist(s);
                t.commit();
                session.close();

                System.out.println("Student added successfully ⭐");
                System.out.println("Course selected ⭐");

                return s;

            }

            private static void updateStudent(SessionFactory sf,Scanner sc){
                System.out.print("Enter the roll to be edited : ");
                int roll = sc.nextInt();
                sc.nextLine();

                Session session = sf.openSession();
                Transaction t = session.beginTransaction();

                Student s = session.find(Student.class,roll);
                if (s==null) System.out.println("Data doesnt exist ");
                else {
                    System.out.print("Enter new name : ");
                    s.setName(sc.nextLine());
                    System.out.print("Enter new email : ");
                    s.setEmail(sc.nextLine());
                }
                System.out.println("Update successful ⭐");
                t.commit();
                session.close();
            }

            private static void deleteStudent(SessionFactory sf,Scanner sc){
                System.out.println("Enter data to be deleted : ");
                int roll = sc.nextInt();
                sc.nextLine();

                Session session = sf.openSession();
                Transaction t = session.beginTransaction();

                Student s = session.find(Student.class,roll);
                if (s==null) System.out.println("Data doesnt exist.");
                else {
                    session.remove(s);
                    System.out.println("Deleted successfully ⭐");
                }
                t.commit();
                session.close();

            }
            private static void allStudent(SessionFactory sf){

                Session session = sf.openSession();

                List<Student> students  = session.createQuery("from Student order by roll asc", Student.class).getResultList();
                for (Student  s : students){
                    System.out.println("Roll : "+s.getRoll() );
                    System.out.println("Name : "+s.getName() );
                    System.out.println("Email : "+s.getEmail() );
                    System.out.println("Course : "+s.getCourse().getCourseName());
                }
                session.close();
            }
}
