import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

class Student {
  public double GPA;
  public int age;
  public String name;
  public String school;

  public Student( double GPA, String name, int age, String school) {
    this.GPA = GPA;
    this.name = name;
    this.age = age;
    this.school = school;
  }

  @Override
  public String toString() {
    return name + "," + GPA + "," + age + "," + school;
  }

  public static Student [] createTestArray() {
    Student s0 = new Student( 3.8d, "Adnan Aziz", 21, "UT Austin");
    Student s1 = new Student( 3.6d, "Imran Aziz", 20, "MIT");
    Student s2 = new Student( 3.5d, "Aardvark Smith", 18, "Berkeley");
    Student s3 = new Student( 2.9d, "Thomas Jefferson", 17, "UT Austin");
    Student s4 = new Student( 3.3d, "Matt Biondi", 22, "Berkeley");
    Student [] testarray = {s0, s1, s2, s3, s4};
    return testarray;
  }

}

public class CustomIterator {

  public static class StudentIteratorBySchool implements Iterator<Student> {
    //TODO(DP): implement this iterator as per the lab specification
    // the remove method should  throw an UnsupportedOperationException
      Student [] studentArray;
      private int m_position = 0;

      public Iterator<Student> StudentIteratorBySchool(Student [] studentArray, String schoolFilter) {
          List<Student> studentList = Arrays.asList(studentArray);
          for (Student student : studentList) {
              if (!((student.school).equals(schoolFilter)))
                  studentList.remove(student);
          }
          this.studentArray = (Student [])studentList.toArray();

          return studentList.iterator();
      }

      @Override
      public boolean hasNext() {
          if (m_position < studentArray.length)
              return true;
          else
              return false;
      }

      @Override
      public Student next() {
          if (this.hasNext())
              return studentArray[m_position];
          else
              return null;
      }

      @Override
      public void remove() {
          throw new UnsupportedOperationException();
      }
  }

  interface StudentPredicate {
    boolean check( Student s );
  }


  public static class StudentIteratorPredicated implements Iterator<Student> {
    //TODO(DP): implement this iterator as per the lab specification
    // the remove method should  throw an UnsupportedOperationException

      @Override
      public boolean hasNext() {
          return false;
      }

      @Override
      public Student next() {
          return null;
      }

      @Override
      public void remove() {

      }
  }

}

