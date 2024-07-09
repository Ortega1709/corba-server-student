package Server;


import StudentApp.Student;
import StudentApp.StudentManagementPOA;
import org.omg.CORBA.ORB;

import java.util.ArrayList;
import java.util.List;

public class StudentServer extends StudentManagementPOA {

    private ORB orb;
    private List<Student> studentsList = new ArrayList<>();

    public void setORB(ORB orb) {
        this.orb = orb;
    }

    @Override
    public List<Student> students() {
        return studentsList;
    }

    @Override
    public List<Student> getStudentsByPromotion(String promotion) {
        List<Student> studentsByPromotion = new ArrayList<>();

        for (Student student : studentsList) {
            if (student.promotion.equals(promotion)) {
                studentsByPromotion.add(student);
            }
        }

        return studentsByPromotion;
    }

    @Override
    public void add(String matricule, String promotion, String naissance, String nom) {
        studentsList.add(new Student(nom, matricule, naissance, promotion));
    }

    @Override
    public void changePromotion(String matricule, String promotion) {
        for (Student student : studentsList) {
            if (student.matricule.equals(matricule)) {
                student.promotion = promotion;
            }
        }
    }

    @Override
    public void changeDate(String matricule, String date) {

        for (Student student : studentsList) {
            if (student.matricule.equals(matricule)) {
                student.date = date;
            }
        }
    }

}
