package Entity;

import java.util.Set;

public class Class {
    private String classID;
    private int classStuNum;
    private Set<Student> students;

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public String getClassID() {
        return classID;
    }

    public int getClassStuNum() {
        return classStuNum;
    }

    public void setClassStuNum(int classStuNum) {
        this.classStuNum = classStuNum;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }
}
