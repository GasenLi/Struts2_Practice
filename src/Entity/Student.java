package Entity;

public class Student {
    private String classID;
    private String studentName;
    private int studentAge;
    private String studentID;
    private Class theClass;

    public Class getTheClass() {
        return theClass;
    }

    public void setTheClass(Class theClass) {
        this.theClass = theClass;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    @Override
    public String toString() {
        return classID + " " + studentName + " " + studentAge + " " + studentID;
    }
}
