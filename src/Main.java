import java.util.*;
//This is an example of a Strategy Pattern
//For CSC 3250
class Course{
    private String _cNum;
    private int _credits;
    public Course(){}
    public Course(String num, int cred){
        _cNum = num;  _credits = cred;
    }
    public void setNumber(String num){_cNum = num;}
    public void setCredits(int cred){_credits = cred;}
    public String getNumber(){return _cNum;}
    public int getCredits(){return _credits;}
    public String toString(){return _cNum + " " + _credits;}
}
class Student{
    private String _sid;
    public Student(){}
    public Student(String d){_sid = d;}
    public void setID(String id){_sid = id;}
    public String getID(){return _sid;}
    public String toString(){return _sid;}
}
interface SearchBehavior<T, S>{ // Strategy Interface - Important for strategy class.
    // T is the object, and S is the value (usually primitive)
    boolean search(T obj, S v);
}
class StudentSearch implements SearchBehavior<Student, String>{ //Behavior Class - Important for strategy class
    // Limits duplicate code
    @Override
    public boolean search(Student obj, String v) { //Uses the SearchBehavior interface for class Student
        return obj.getID().equals(v);
    }
}
class CourseSearch implements SearchBehavior<Course, String>{ //Uses the SearchBehavior interface for class Course
    @Override
    public boolean search(Course obj, String v) {
        return obj.getNumber().equals(v);
    }
}
class AllItems<T>{ // Concrete Class - important for strategy pattern
    // Generic - templated class, not required for the strategy pattern.
    private ArrayList<T> _items;

    public AllItems(){_items = new ArrayList<T>();}
    public AllItems(int size){_items = new ArrayList<T>(size);}
    public void addItem(T t){_items.add(t);}

    public <S> int findItem(S v, SearchBehavior<T, S> sb){
        for (int i =0; i< _items.size(); i++){
            if (sb.search(_items.get(i), v)){
                return i;
            }
        }
        return -1;
    }
    public void removeItem(int i){
        if (i >= 0 && i < _items.size()){
            _items.remove(i);
        }
    }
    public int size(){return _items.size();}
    public T getItem(int i){return _items.get(i);}
}
class AllStudents{
    private AllItems<Student> _students;
    public AllStudents(){_students = new AllItems<Student>();}
    public AllStudents(int size){_students = new AllItems<Student>(size);}
    public void addStudent(String id){_students.addItem(new Student(id));}
    public boolean isStudent(String id){
        if (_students.findItem(id, new StudentSearch()) != -1)
            return true;
        else{
            return false;
        }
    }
    public int findStudent(String id){
        return _students.findItem(id, new StudentSearch());
    }
    public void removeStudent(String id){
        int i = _students.findItem(id, new StudentSearch());
        _students.removeItem(i);

    }
    public boolean modifyStudentID(String oldID, String newID){
        int i = findStudent(oldID);
        if (i < 0) return false;
        else {
            _students.getItem(i).setID(newID);
            return true;
        }
    }
    public int size(){return _students.size();}
    public String toString(){
        String s = "Students:\n";
        for (int i=0; i<_students.size(); i++)
            s += (_students.getItem(i).toString() + "\n");
        return s;
    }
}
class AllCourses{
    private AllItems<Course> _courses;
    public AllCourses(){
        _courses = new AllItems<Course>();
    }
    public AllCourses(int size){_courses = new AllItems<Course>(size);}
    public void addCourse(String cNum, int c){
        _courses.addItem(new Course(cNum, c));
    }
    public boolean isCourse(String cNum){
        if (_courses.findItem(cNum, new CourseSearch()) != -1){
            return true;
        }
        else {
            return false;
        }
    }
    public int findCourse(String cNum){
        return _courses.findItem(cNum, new CourseSearch());
    }
    public void removeCourse(String cNum){
        int i = _courses.findItem(cNum, new CourseSearch());
        _courses.removeItem(i);
    }
    public boolean modifyCourseNumber(String oldNum, String newNum){
        int i = findCourse(oldNum);
        if (i < 0) return false;
        else {
            _courses.getItem(i).setNumber(newNum);
            return true;
        }
    }
    public int size(){return _courses.size();}
    public String toString(){
        String s = "Courses:\n";
        for (int i=0; i<_courses.size(); i++)
            s += (_courses.getItem(i).toString() + "\n");
        return s;
    }
}

public class Main {
    public static void main(String[] args) {
        AllStudents as = new AllStudents();
        AllCourses ac = new AllCourses();
        as.addStudent("100");
        as.addStudent("200");
        as.addStudent("300");
        ac.addCourse("CSC3250", 4);
        ac.addCourse("CSC1700", 4);
        ac.addCourse("MTH3270", 4);
        System.out.println(as);
        System.out.println(ac);
        System.out.println("Is Student 300: " + as.isStudent("300"));
        System.out.println("Find Student 300: " + as.findStudent("300"));
        System.out.println("Is Course CSC3250: " + ac.isCourse("CSC3250") );
        System.out.println("Find Course CSC3250: " + ac.findCourse("CSC3250"));
        as.removeStudent("300");
        System.out.println("\nAfter removing student 300");
        as.removeStudent("300");
        System.out.println(as);
        System.out.println("Is Student 300: " + as.isStudent("300"));
        System.out.println("Find Student 300: " + as.findStudent("300"));
        System.out.println("\nAfter removing course 3250");
        ac.removeCourse("CSC3250");
        System.out.println(ac);
        System.out.println("Is Course CSC3250: " + ac.isCourse("CSC3250") );
        System.out.println("Find Course CSC3250: " + ac.findCourse("CSC3250"));
    }
}
