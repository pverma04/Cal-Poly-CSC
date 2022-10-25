import java.util.List;
import java.util.Objects;

class Student
{
   private final String surname;
   private final String givenName;
   private final int age;
   private final List<CourseSection> currentCourses;

   public Student(final String surname, final String givenName, final int age,
                  final List<CourseSection> currentCourses) {
      this.surname = surname;
      this.givenName = givenName;
      this.age = age;
      this.currentCourses = currentCourses;
   }
   @Override
   public boolean equals(Object o) {
      if (o == null) { return false; }
      if (!(o instanceof CourseSection)) { return false; }
      if (o == this) { return true; }
      else {
         Student s = (Student) o;
         return Objects.equals(this.surname, s.surname) && Objects.equals(this.givenName, s.givenName) &&
                 Objects.equals(this.age, s.age) && Objects.equals(this.currentCourses, s.currentCourses);
      }
   }
   @Override
   public int hashCode() {
      return Objects.hash(surname, givenName, age, currentCourses);
   }
}
