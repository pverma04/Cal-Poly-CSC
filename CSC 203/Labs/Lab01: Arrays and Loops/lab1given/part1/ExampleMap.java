import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ExampleMap
{
   public static List<String> highEnrollmentStudents(
           Map<String, List<Course>> courseListsByStudentName, int unitThreshold)
   {
      List<String> overEnrolledStudents = new LinkedList<>();

      /*
         Build a list of the names of students currently enrolled
         in a number of units strictly greater than the unitThreshold.
      */
      int units = 0;
      for (String student : courseListsByStudentName.keySet()) {
         for (int i = 0; i < courseListsByStudentName.get(student).size(); i++) {
            units = courseListsByStudentName.get(student).get(i).getNumUnits() + units;
         }
         if (units > unitThreshold) {
            overEnrolledStudents.add(student);
         }
         units = 0;
      }
      return overEnrolledStudents;      
   }
}
