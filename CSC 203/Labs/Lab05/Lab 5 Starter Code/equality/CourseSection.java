import java.time.LocalTime;

class CourseSection
{
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
         return this.prefix.equals(cs.prefix) && this.number.equals(cs.number) && this.enrollment == cs.enrollment
                 && this.startTime.equals(cs.startTime) && this.endTime.equals(cs.endTime);
      }
   }
   @Override
   public int hashCode() {
      int rv = 17;
      if (this.prefix != null) { rv = 31 * rv + this.prefix.hashCode(); }
      if (this.number != null) { rv = 31 * rv + this.number.hashCode(); }
      if (this.number != null) { rv = 31 * rv + this.number.hashCode(); }
      if (this.startTime != null) { rv = 31 * rv + this.startTime.hashCode(); }
      if (this.endTime != null) { rv = 31 * rv + this.endTime.hashCode(); }
      return rv + enrollment;
   }
   // additional likely methods not defined since they are not needed for testing
}
