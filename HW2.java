
//TODO: student id & name
//TODO: This assignment aims to write a function simulate to allocate the course process by student’s preference.
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.text.Style;
import java.util.*;

public class HW2 {
    
  private HW2() {}
  
  public static class Course{
    int id = 0; // course's id. ITR->1, MIS->2, DataBase->3, ResearchMethod->4
    String name; // course's name
    Student[] candidate; // The course selection result. 
    
    // Course should initial by course id and name.
    private Course(int id, String name, int limit_number) {
        this.id = id;
        this.name = name;
        this.candidate = new Student[limit_number];
    }
    
  }
  
  public static class Student{
    int year; // Student's year. freshman->1, sophomore->2, junior->3, senior ->4
    int id; // Unique student id
        //A set of student's preferences of courses id. e.g. [4,3,2,1]. The first priority of course is 4, which means ResearchMethod
    int[] preference;
    
    // Student should initial by year, id ,and candidate_courses.
    private Student(int year, int id, int[] preference) {
        this.year = year;
        this.preference = preference;
        this.id = id;
    }
    
    //overriding the toString() method
    @Override
    public String toString()
    {
        return "大"+this.year + " 學號" + this.id;
    }
  }
     
  // Test case 1
  private static void test1() {
    Course[] courses = new Course[4];
    courses[0] = new Course(1, "ITR", 3);
    courses[1] = new Course(2, "MIS", 3);
    courses[2] = new Course(3, "DataBase", 3);
    courses[3] = new Course(4, "ResearchMethod", 3);
    
    Student[] students = new Student[12];
    students[0] =  new Student(1, 11, new int[]{1, 2, 3, 4});
    students[1] =  new Student(1, 12, new int[]{1, 2, 3, 4});
    students[2] =  new Student(1, 13, new int[]{1, 2, 3, 4});
    students[3] =  new Student(2, 21, new int[]{1, 2, 3, 4});
    students[4] =  new Student(2, 22, new int[]{1, 2, 3, 4});
    students[5] =  new Student(2, 23, new int[]{1, 2, 3, 4});
    students[6] =  new Student(3, 31, new int[]{1, 2, 3, 4});
    students[7] =  new Student(3, 32, new int[]{1, 2, 3, 4});
    students[8] =  new Student(3, 33, new int[]{1, 2, 3, 4});
    students[9] =  new Student(4, 41, new int[]{1, 2, 3, 4});
    students[10] =  new Student(4, 42, new int[]{1, 2, 3, 4});
    students[11] =  new Student(4, 43, new int[]{1, 2, 3, 4});
    
    System.out.println("Test case1:");
    long startTime = System.nanoTime();
    Course[] result = simulate(students, courses);
    long estimatedTime = System.nanoTime() - startTime;
    print_course(result);
    print_first_priority(result);
    System.out.println("Performance(time): "+estimatedTime);
  }
  
  // Test case 2
  private static void test2() {
    Course[] courses = new Course[3];
    courses[0] = new Course(1, "ITR", 6);
    courses[1] = new Course(2, "MIS", 2);
    courses[2] = new Course(3, "DataBase", 4);
    
    Student[] students = new Student[12];
    students[0] =  new Student(1, 11, new int[]{1, 2, 3});
    students[1] =  new Student(1, 12, new int[]{1, 2, 3});
    students[2] =  new Student(1, 13, new int[]{1, 2, 3});
    students[3] =  new Student(2, 21, new int[]{1, 2, 3});
    students[4] =  new Student(2, 22, new int[]{1, 2});
    students[5] =  new Student(2, 23, new int[]{2, 1, 3});
    students[6] =  new Student(3, 31, new int[]{1, 2, 3});
    students[7] =  new Student(3, 32, new int[]{1, 2, 3});
    students[8] =  new Student(3, 33, new int[]{1, 2, 3});
    students[9] =  new Student(4, 41, new int[]{});
    students[10] =  new Student(4, 42, new int[]{1, 2, 3});
    students[11] =  new Student(4, 43, new int[]{1, 2, 3});
    
    System.out.println("Test case2:");
    long startTime = System.nanoTime();
    Course[] result = simulate(students, courses);
    long estimatedTime = System.nanoTime() - startTime;
    print_course(result);
    print_first_priority(result);
    System.out.println("Performance(time): "+estimatedTime);
  }
  
  // Abstract test case 3. Prepare for hidden test case.
  // It is normal that the function is no code.
  private static void test3() {
  }
  
  // TODO: write your code in this function
  // Simulate courses allocating process
  private static Course[] simulate(Student[] students, Course[] courses) {
    ArrayList<Student> sortList = new ArrayList<>(); //ArrayList for students with preference to sort
    ArrayList<Student> blankList = new ArrayList<>();//ArrayList for no preference
    HashMap<Integer, Integer> available = new HashMap<>();
    //int[] courseFull = new int[courses.length]; //Array to record how much available for each class

    for(Student student : students){  //for loop go through sutdents' data
      
      if(student.preference.length==0) {  //if student doesn't have any preference
        blankList.add(student);           //add into blank list
        continue;                         //continue the loop
      }else{
        sortList.add(student);            //otherwise we add students with preference
      }
      
    }

    for(Course course : courses){
      available.put(course.id, course.candidate.length);
    }

    Collections.sort(sortList, new Comparator<Student>(){
    public int compare(Student s1, Student s2)
      { // We sort the sortlist depends on two conditions, if the years are the same then we compare student id (DESC order)
        if(Integer.compare(s2.year, s1.year) == 0) return Integer.compare(s1.id, s2.id);
        else return Integer.compare(s2.year, s1.year); //else we compare thier years
      }
    });
    Collections.sort(blankList, new Comparator<Student>(){
      public int compare(Student s1, Student s2)
        { // We sort the blankList depends on two conditions, if the years are the same then we compare student id (DESC order)
          if(Integer.compare(s2.year, s1.year) == 0) return Integer.compare(s1.id, s2.id);
          else return Integer.compare(s2.year, s1.year); //else we compare thier years
        }
      });

    sortList.addAll(blankList); //We coonncet to sorted arraylists 
                                //which no preferences students' with stay at the end of the arrayList
   
    
    /*for(int index = 0; index<courseFull.length;index++){
      courseFull[index] = courses[index].candidate.length;//available spots for each courses to record
                                                          //depends on their candidate length
    }*/
    System.out.println("availabe for Each Course: "+available);
    System.out.println("Priority for Blank after Sorted: "+blankList);
    System.out.println("Priority after Sorted with Blank: "+sortList);
    System.out.println("No preference chosen: "+blankList);
    
    for(int i = 0;i<sortList.size();i++){
      
    }
    
  
    
    return courses;
  }
    
  
  // helper function
  // print result of allocating the student to course
  private static void print_course(Course[] courses) {
    for(Course course:courses) {
        System.out.print(course.name+" : ");
        System.out.println(Arrays.toString(course.candidate));
    }
  }
  
  // helper function
  // Calculate the number of students who meet his first priority.
  private static void print_first_priority(Course[] courses) {
    int count = 0;
    for(Course course:courses) {
        for(Student one:course.candidate) {
            if (one != null &&  one.preference.length>0 && one.preference[0] == course.id) {
                count++;
            }
        }
    }
    System.out.println("Students satisfaction: "+ count);
  }


  public static void main(String[] args) {    
      test1();
//    test1 expected output: 
//    ITR : [大4 學號41, 大4 學號42, 大4 學號43]
//      MIS : [大3 學號31, 大3 學號32, 大3 學號33]
//      DataBase : [大2 學號21, 大2 學號22, 大2 學號23]
//      ResearchMethod : [大1 學號11, 大1 學號12, 大1 學號13]
//      Students satisfaction: 3
//      Performance(time): XXXX
      test2();
//    test2 expected output:
//    ITR : [大4 學號42, 大4 學號43, 大3 學號31, 大3 學號32, 大3 學號33, 大2 學號21]
//      MIS : [大2 學號22, 大2 學號23]
//      DataBase : [大1 學號11, 大1 學號12, 大1 學號13, 大4 學號41]
//      Students satisfaction: 7
//      Performance(time): XXXX
      test3(); // hidden test case 3
  }
  
}
