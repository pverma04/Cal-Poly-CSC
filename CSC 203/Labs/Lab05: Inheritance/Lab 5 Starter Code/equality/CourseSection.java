import java.time.LocalTime;
import java.util.Objects;

class CourseSection {
   private final String prefix;
   private final String number;
   private final int enrollment;
   private final LocalTime startTime;
   private final LocalTime endTime;

   public CourseSection(final String prefix, final String number,
                        final int enrollment, final LocalTime startTime, final LocalTime endTime) {
      this.prefix = prefix;
      this.number = number;
      this.enrollment = enrollment;
      this.startTime = startTime;
      this.endTime = endTime;
   }

   @Override
   public boolean equals(Object o) {
      if (o == null) { return false; }
      if (!(o instanceof CourseSection)) { return false; }
      if (o == this) { return true; }
      else {
         CourseSection cs = (CourseSection) o;
         return this.equalsHelper(this.prefix, cs.prefix) && this.equalsHelper(this.number, cs.number) &&
                 this.equalsHelper(this.enrollment, cs.enrollment)&&
                 this.equalsHelper(this.startTime, cs.startTime) && this.equalsHelper(this.endTime, cs.endTime);
      }
   }
   @Override
   public int hashCode() {
      int rv = 1;
      if (this.prefix != null) { rv = 31 * rv + this.prefix.hashCode(); }
      if (this.number != null) { rv = 31 * rv + this.number.hashCode(); }
      if (this.number != null) { rv = 31 * rv + this.number.hashCode(); }
      if (this.startTime != null) { rv = 31 * rv + this.startTime.hashCode(); }
      if (this.endTime != null) { rv = 31 * rv + this.endTime.hashCode(); }
      return rv + enrollment;
   }
   private boolean equalsHelper(Object thisInstance, Object otherInstance) {
      if (thisInstance == null && otherInstance == null) { return true; }
      else if ((thisInstance == null && otherInstance != null) ||
              (thisInstance != null && otherInstance == null)){ return false; }
      else if (!thisInstance.getClass().equals(otherInstance.getClass())) { return false; }
      else if (thisInstance.equals(otherInstance)) { return true; }
      return false;
   }
   // additional likely methods not defined since they are not needed for testing
}
