package entities;

import java.util.ArrayList;

/**
 * Created by anandparmar on 04/10/17.
 */
public class DemoDataModelEntity {
    private String schoolName;
    private String yearOfEstablishment;
    private ArrayList<Students> students;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getYearOfEstablishment() {
        return yearOfEstablishment;
    }

    public void setYearOfEstablishment(String yearOfEstablishment) {
        this.yearOfEstablishment = yearOfEstablishment;
    }

    public ArrayList<Students> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Students> students) {
        this.students = students;
    }

    public class Students {
        private String name;
        private String rollNumber;
        private String age;
        private String favSubject;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRollNumber() {
            return rollNumber;
        }

        public void setRollNumber(String rollNumber) {
            this.rollNumber = rollNumber;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getFavSubject() {
            return favSubject;
        }

        public void setFavSubject(String favSubject) {
            this.favSubject = favSubject;
        }
    }
}
