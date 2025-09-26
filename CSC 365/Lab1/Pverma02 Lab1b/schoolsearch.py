class Student: #each student is an object
    def __init__(self, stLastName, stFirstName, grade, classroom, bus, gpa): 
        self.st_last_name = stLastName
        self.st_first_name = stFirstName
        self.grade = grade
        self.classroom = classroom
        self.bus = bus
        self.gpa = gpa
        
class Teacher:
    def __init__(self, tLastName, tFirstName, classroom):
        self.t_last_name = tLastName
        self.t_first_name = tFirstName
        self.classroom = classroom

def getStudentsByLastName(stu_dict, t_dict, stu_ln):
    students = []
    if stu_ln in stu_dict.keys():
        students = stu_dict[stu_ln]
        for s in students:
            t = t_dict.get(s.classroom)[0] #gets teacher object from student's classroom 
            if t:
                print(f"{s.st_first_name} {s.st_last_name}, Grade: {s.grade}, Classroom: {s.classroom}, Teacher: {t.t_first_name} {t.t_last_name}")
            else:
                print(f"{s.st_first_name} {s.st_last_name}, Grade: {s.grade}, Classroom: {s.classroom}, Teacher: No teacher found")
    else:
        print(f"No students found by this last name: {stu_ln}")
    return students

def getStudentsByBus(stu_dict, t_dict, route_num):
    rv = [] #return value
    for students in stu_dict.values():
        for s in students:
            if s.bus == route_num:
                t = t_dict.get(s.classroom)[0]
                if t:
                    t_info = f", Teacher: {t.t_first_name} {t.t_last_name}"
                    print(f"{s.st_first_name} {s.st_last_name}, Classroom: {s.classroom}, Teacher: {t.t_first_name} {t.t_last_name}")
                else:
                    print(f"{s.st_first_name} {s.st_last_name}, Classroom: {s.classroom}, Teacher: No teacher found")
                
                rv.append(s)
    if len(rv) == 0:
        print("No students found taking this bus")
    return rv 

def getStudentsByTeacher(stu_dict, t_dict, t_ln):
    rv = [] #return value
    for students in stu_dict.values():
        for s in students:
            t = t_dict.get(s.classroom)[0]
            if t and t.t_last_name == t_ln:
                print(f"{s.st_first_name} {s.st_last_name}, Grade: {s.grade}, Classroom: {s.classroom}")
                rv.append(s)
    if len(rv) == 0:
        print("No students found that have this teacher")
    return rv

def getStudentsByGrade(stu_dict, t_dict, g):
    rv = [] #return value
    print(f"Students in grade {g}: ")
    for students in stu_dict.values():
        for s in students:
            if s.grade == g:
                t = t_dict.get(s.classroom)[0]
                if t:
                    print(f"{s.st_first_name} {s.st_last_name}, Classroom: {s.classroom}, Teacher: {t.t_first_name} {t.t_last_name}")
                else:
                    print(f"{s.st_first_name} {s.st_last_name}, Classroom: {s.classroom}, Teacher: No teacher found")
                rv.append(s)
    if len(rv) == 0:
        print("No students found in this grade")
    return rv
def getTeachersByGrade(stu_dict, t_dict, g):
    rv = [] #return value
    curr_teacher = None
    for students in stu_dict.values():
        for s in students:
            if s.grade == g: #get teacher
                if t_dict.get(s.classroom) not in rv: #classroom not already passed for this grade
                    rv.append(t_dict.get(s.classroom))
    return rv
def getHighestGPAByGrade(stu_dict, t_dict, g):
    highest = -1 
    top_stu = None #return value
    for students in stu_dict.values():
        for s in students:
            if s.grade == g and float(s.gpa) > highest:
                    highest = float(s.gpa)
                    top_stu = s
    if top_stu:
        t = t_dict.get(top_stu.classroom)[0]
        print(f"Student with the highest GPA in grade {g}:")
        if t:
            print(f"{top_stu.st_first_name} {top_stu.st_last_name}, GPA: {top_stu.gpa}, Bus: {top_stu.bus}, Teacher: {t.t_first_name} {t.t_last_name}")
        else:
            print(f"{top_stu.st_first_name} {top_stu.st_last_name}, GPA: {top_stu.gpa}, Bus: {top_stu.bus}, Teacher: No teacher found")
    else:
        print(f"No students found in grade: {g} ")
    return top_stu

def getLowestGPAByGrade(stu_dict, t_dict, g):
    lowest = float('inf')
    low_stu = None #return value
    for students in stu_dict.values():
        for s in students:
            if s.grade == g and float(s.gpa) < lowest:
                    lowest = float(s.gpa)
                    low_stu = s
    if low_stu:
        t = t_dict.get(low_stu.classroom)[0]
        print(f"Student with the lowest GPA in grade {g}:")
        if t:
            print(f"{low_stu.st_first_name} {low_stu.st_last_name}, GPA: {low_stu.gpa}, Bus: {low_stu.bus}, Teacher: {t.t_first_name} {t.t_last_name}")
        else:
            print(f"{low_stu.st_first_name} {low_stu.st_last_name}, GPA: {low_stu.gpa}, Bus: {low_stu.bus}, Teacher: No teacher found")
    else:
        print(f"No students found in grade: {g} ")
    return low_stu

def avgGPAByGrade(stu_dict, grade):
    totalGPA = 0 #return value
    stu_count = 0;
    for students in stu_dict.values():
        for s in students:
            if s.grade == grade:
                totalGPA += float(s.gpa)
                stu_count = stu_count + 1
    if stu_count == 0:
        return "No Students in this grade"
    return totalGPA / stu_count

def getStudentsByClassroom(stu_dict, c):
    rv = []
    for students in stu_dict.values():
        for s in students:
            if s.classroom == c:
                rv.append(s)
    return rv

def getTeacherByClassroom(t_dict, c):
    rv = t_dict.get(c)
    if rv:
        return rv
    else:
        return None
    
def getClassroomInfo(stu_dict):
    class_enrollment = {} #class# to number students
    for students in stu_dict.values():
        for s in students:
            if s.classroom not in class_enrollment.keys():
                class_enrollment[s.classroom] = 0
            class_enrollment[s.classroom] += 1
    return dict(sorted(class_enrollment.items(), key=lambda item: item[0]))

def getStudentInfo(stu_dict):
    info_list = [0, 0, 0, 0, 0, 0, 0] #each index is each grade
    for students in stu_dict.values():
        for s in students:
            info_list[int(s.grade)] = info_list[int(s.grade)] + 1

    for i in range(len(info_list)):
        print(f"Grade {i}: {info_list[i]} Students") 

def analyzeGPAByGrade(stu_dict, g):
    total_gpa = 0
    low_gpa = float('inf')
    high_gpa = -1

    curr_gpa = 0
    count = 0
    for students in stu_dict.values():
        for s in students:
            if s.grade == g:
                curr_gpa = float(s.gpa)
                total_gpa += curr_gpa
                if curr_gpa > high_gpa:
                    high_gpa = curr_gpa
                if curr_gpa < low_gpa:
                    low_gpa = curr_gpa
                count += 1
    
    if count == 0:
        print("No students found in this grade")
        return
    avg_gpa = round(total_gpa / count, 2)
    
    print(f"GPA Analytics for Grade {g}: ")
    print(f"Average GPA: {avg_gpa}")
    print(f"Highest GPA: {low_gpa}")
    print(f"Highest GPA: {high_gpa}")
    return


def analyzeGPAByTeacher(stu_dict, t_dict, t):
    total_gpa = 0
    low_gpa = float('inf')
    high_gpa = -1

    #1st step get classroom for teacher (by last lame), check students by classroom
    curr_room = None
    for rooms in t_dict.values():
        for teacher in rooms:
            if teacher.t_last_name == t:
                curr_room = teacher.classroom
    
    if curr_room == None:
        print("This teacher was not found")
        return
    
    curr_gpa = 0
    count = 0

    for students in stu_dict.values():
        for s in students:
            if s.classroom == curr_room:
                curr_gpa = float(s.gpa)
                total_gpa += curr_gpa
                if curr_gpa > high_gpa:
                    high_gpa = curr_gpa
                if curr_gpa < low_gpa:
                    low_gpa = curr_gpa
                count += 1
    
    if count == 0:
        print("No students found for this teacher")
        return
    avg_gpa = round(total_gpa / count, 2)
    
    print(f"GPA Analytics for Teacher {t}: ")
    print(f"Average GPA: {avg_gpa}")
    print(f"Highest GPA: {low_gpa}")
    print(f"Highest GPA: {high_gpa}")
    return

def analyzeGPAByBus(stu_dict, b):
    total_gpa = 0
    low_gpa = float('inf')
    high_gpa = -1

    curr_gpa = 0
    count = 0
    for students in stu_dict.values():
        for s in students:
            if s.bus == b:
                curr_gpa = float(s.gpa)
                total_gpa += curr_gpa
                if curr_gpa > high_gpa:
                    high_gpa = curr_gpa
                if curr_gpa < low_gpa:
                    low_gpa = curr_gpa
                count += 1
    
    if count == 0:
        print("No students found in this bus")
        return
    avg_gpa = round(total_gpa / count, 2)
    
    print(f"GPA Analytics for Bus {b}: ")
    print(f"Average GPA: {avg_gpa}")
    print(f"Highest GPA: {low_gpa}")
    print(f"Highest GPA: {high_gpa}")
    return

def storeStudents(file_path):
    st_dict = {} #key: last name value: list of students with this last name
    with open(file_path, 'r') as f: #read in file
        for line in f:
            student_data = line.strip().split(",") #strip in case of any errors in txt file itself
            curr_student = Student(*student_data) #unpack data directly into a new Student object
        
            if curr_student.st_last_name not in st_dict:
                st_dict[curr_student.st_last_name] = [] #create new entry by new last name        
            st_dict[curr_student.st_last_name].append(curr_student) #students with same last name will be in the same list (no repeats in this lab)
    return st_dict

def storeTeachers(file_path):
    t_dict = {} #key: classroom value: array with teacher objects
    with open(file_path, 'r') as f: #read in file
        for line in f:
            teacher_data = line.strip().split(",") #strip in case of any errors in txt file itself
            curr_teacher = Teacher(*teacher_data) #unpack data directly into a new Teacher object
        
            if curr_teacher.classroom not in t_dict:
                t_dict[curr_teacher.classroom] = [] #create new entry by classroom - each should be unique 
            t_dict[curr_teacher.classroom].append(curr_teacher)       
    return t_dict

def main():
    stu_file_path = "list.txt"
    t_file_path = "teachers.txt"

    students = storeStudents(stu_file_path)
    teachers = storeTeachers(t_file_path)
    
    while True: #for user interaction
        curr_command = input("Please enter a command. Q[uit] to exit: ").strip()
        
        #Q[UIT]
        #----------------
        if curr_command.startswith("Q") or curr_command.startswith("Quit"):
            print("Exiting program now. Thank you!")
            break
        
        #S[tudent] <lastname> or S[tudent] <lastname> B[us]
        #----------------
        elif curr_command.startswith("S:") or curr_command.startswith("Student:"):
            commands = curr_command.split(" ") #take list of all commands
            last_name = commands[1]
            if len(commands) == 2: #bus not requested, just provide student information for last name
                getStudentsByLastName(students, teachers, last_name)
                
            elif len(commands) == 3:
                if commands[2].startswith("B") or commands[2].startswith("Bus"): #get bus of student
                    curr_student = students.get(last_name)
                    if curr_student:
                        for s in curr_student: #grade, classroom, teacher
                            print(f"{s.st_first_name} {s.st_last_name}, Bus Route: {s.bus}")
                    else:
                        print(f"No students found by this last name: {last_name}")
                else:
                    print("Invalid request")
            else:
                print("Invalid request")
            
                    
            
        #B[us] <route#>
        #----------------
        elif curr_command.startswith("B:") or curr_command.startswith("Bus:"):
            _, bus = curr_command.split(" ", 1) #take only one split, at the first space given
            print(f"Students taking bus {bus}: ")
            getStudentsByBus(students, teachers, bus)
            

        #T[eacher] <lastname> or <grade>
        #----------------
        elif curr_command.startswith("T:") or curr_command.startswith("Teacher:"):
            _, teacher_ln = curr_command.split(" ", 1)
            print(f"Students with teacher: {teacher_ln}")
            getStudentsByTeacher(students, teachers, teacher_ln)
           

        #G[rade] <grade> [[H[igh] || L[ow]] || T[eacher]]
        #----------------
        elif curr_command.startswith("G:") or curr_command.startswith("Grade:"):
            commands = curr_command.split(" ") #0: "G" 1: grade # 2: H or L
            grade = commands[1]
            if len(commands) == 3:
                if commands[2].startswith("H") or commands[2].startswith("High"): #asking for highest gpa in grade
                    getHighestGPAByGrade(students, teachers, grade)
                elif commands[2].startswith("L") or commands[2].startswith("Low"): #asking for highest gpa in grade
                    getLowestGPAByGrade(students, teachers, grade)
                elif commands[2].startswith("T") or commands[2].startswith("Teacher"): #asking for all teachers who teach grade
                    g_teachers = getTeachersByGrade(students, teachers, grade)
                    if len(g_teachers) > 0:
                        print(f"Teachers teaching grade {grade}: ")
                        for room in g_teachers: #g_teachers is list of lists of teachers, by classroom
                            for t in room:
                                print(f"{t.t_last_name} {t.t_last_name}")
                    else:
                        print("No teachers found teaching this grade")
                else:
                    print("Command not specified")
            elif len(commands) == 2:
                getStudentsByGrade(students, teachers, grade)
            else:
                print("No students found in this grade")
            

        #A[verage] <grade>
        #----------------
        elif curr_command.startswith("A:") or curr_command.startswith("Average:"):
            _, grade = curr_command.split(" ", 1)
            avg_gpa = avgGPAByGrade(students, grade)
            if isinstance(avg_gpa, float):
                avg_gpa = round(avg_gpa, 2)
                print(f"The average GPA of students in grade {grade}: {avg_gpa}")
            else:
                print("No students found in this grade")

        #C[lassroom] <number> S/T (list students or teacher(s))
        #----------------
        elif curr_command.startswith("C:") or curr_command.startswith("Classroom:"):
            commands = curr_command.split(" ")
            room = commands[1]
            if len(commands) != 3:
                print("Teachers or students not specified")
            elif commands[2].startswith("S"): #requesting students list
                classroom_students = getStudentsByClassroom(students, room)
                if len(classroom_students) > 0:
                    print(f"Students in classroom: {room}")
                    for s in classroom_students:
                        print(f"{s.st_first_name} {s.st_last_name}, Grade: {s.grade}, GPA: {s.gpa}, Bus: {bus}")
                else:
                    print("No students found in this classroom")
            elif commands[2].startswith("T"): #requesting teacher(s)
                classroom_teachers = getTeacherByClassroom(teachers, room)
                if classroom_teachers != None and len(classroom_teachers) > 0:
                    print(f"Teachers in classroom: {room}")
                    for t in classroom_teachers:
                        print(f"{t.t_first_name} {t.t_last_name}")
                else:
                    print("No teachers found in this classroom")
            else: 
                print("Teachers or students not specified")


        #I[nfo] [C[lassroom]]
        #----------------
        elif curr_command.startswith("I:") or curr_command.startswith("Info:"):
            commands = curr_command.split(" ")
            if len(commands) == 1:
                getStudentInfo(students)
            elif len(commands) == 2:
                if commands[1].startswith("C") or commands[1].startswith("Classroom"):
                    class_students = getClassroomInfo(students)
                    num_students = 0;
                    if class_students: #if any keys (classrooms) exist in this list
                        for room in class_students:
                            num_students = class_students[room]
                            print(f"Students in classroom {room}: {num_students}")
                    else:
                        print("No students found")          
            else:
                print("Invalid command")
        
        #AG (Analyze Grade)
        #----------------
        elif curr_command.startswith("AG: ") or curr_command.startswith("Analyze Grade: "):
            _, grade = curr_command.split(" ", 1)
            analyzeGPAByGrade(students, grade)

        #AT (Analyze Teacher)
        #----------------
        elif curr_command.startswith("AT: ") or curr_command.startswith("Analyze Teacher: "):
            _, teacher = curr_command.split(" ", 1)
            analyzeGPAByTeacher(students, teachers, teacher)

        #AB (Analyze Bus)
        #----------------
        elif curr_command.startswith("AB:") or curr_command.startswith("Analyze Bus: "):
            _, bus = curr_command.split(" ", 1)
            analyzeGPAByBus(students, bus)
        
        else:
            print("Invalid command: please try again")
        

if __name__ == "__main__":
    main()