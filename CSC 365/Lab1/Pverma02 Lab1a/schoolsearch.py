class Student: #each student is an object
    def __init__(self, stLastName, stFirstName, grade, classroom, bus, gpa, tLastName, tFirstName):
        self.st_last_name = stLastName
        self.st_first_name = stFirstName
        self.grade = grade
        self.classroom = classroom
        self.bus = bus
        self.gpa = gpa
        self.t_last_name = tLastName
        self.t_first_name = tFirstName

def getStudentsByLastName(stu_dict, stu_ln):
    if stu_ln in stu_dict.keys():
        return stu_dict[stu_ln]
    else:
        return None

def getStudentsByBus(stu_dict, route_num):
    rv = [] #return value
    for students in stu_dict.values():
        for s in students:
            if s.bus == route_num:
                rv.append(s)
    return rv 

def getStudentsByTeacher(stu_dict, t_ln):
    rv = [] #return value
    for students in stu_dict.values():
        for s in students:
            if s.t_last_name == t_ln:
                rv.append(s)
    return rv

def getStudentsByGrade(stu_dict, g):
    rv = [] #return value
    for students in stu_dict.values():
        for s in students:
            if s.grade == g:
                rv.append(s)
    return rv

def getHighestGPAByGrade(stu_dict, g):
    highest = -1 
    top_stu = None #return value
    for students in stu_dict.values():
        for s in students:
            if s.grade == g:
                if float(s.gpa) > highest:
                    highest = float(s.gpa)
                    top_stu = s
    return top_stu

def getLowestGPAByGrade(stu_dict, g):
    lowest = float('inf')
    low_stu = None #return value
    for students in stu_dict.values():
        for s in students:
            if s.grade == g:
                if float(s.gpa) < lowest:
                    lowest = float(s.gpa)
                    low_stu = s
    return low_stu

def avgGPAByGrade(stu_dict, grade):
    totalGPA = 0 #return value
    stu_count = 0;
    for students in stu_dict.values():
        for s in students:
            if int(s.grade) == int(grade):
                totalGPA += float(s.gpa)
                stu_count = stu_count + 1
    if stu_count == 0:
        return "No Students in this grade"
    return totalGPA / stu_count

def getStudentInfo(stu_dict):
    info_list = [0, 0, 0, 0, 0, 0, 0] #each index is each grade
    for students in stu_dict.values():
        for s in students:
            info_list[int(s.grade)] = info_list[int(s.grade)] + 1

    for i in range(len(info_list)):
        print(f"Grade {i}: {info_list[i]} Students") 
    
def storeStudents(file_path):
    st_dict = {} #key: last name value: list of students with this last name
    with open(file_path, 'r') as f: #read in file
        for line in f:
            student_data = line.strip().split(",") #strip in case of any errors in txt file itself
            curr_student = Student(*student_data) #unpack data directly into a new student object
        
            if curr_student.st_last_name not in st_dict:
                st_dict[curr_student.st_last_name] = [] #create new entry by new last name        
            st_dict[curr_student.st_last_name].append(curr_student) #students with same last name will be in the same list (no repeats in this lab)
    return st_dict

def main():
    file_path = "students.txt"

    students = storeStudents(file_path)
    #TESTING
    #for s in students:
    #    print(f"{s.st_first_name} {s.st_last_name}, Grade: {s.grade}, Classroom: {s.classroom}, Bus: {s.bus}, GPA: {s.gpa}, Teacher: {s.t_first_name} {s.t_last_name}")
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
           # _, last_name = curr_command.split(" ", 1) #take only one split, at the first space given
            commands = curr_command.split(" ") #take list of all commands
            last_name = commands[1]
            curr_student = getStudentsByLastName(students, last_name) #gets a list of students with <lastname>
            if curr_student: #found any student(s) under this lastname
                if len(commands) == 2: #bus not requested, just provide student information for last name
                    for s in curr_student: #grade, classroom, teacher
                        print(f"{s.st_first_name} {s.st_last_name}, Grade: {s.grade}, Classroom: {s.classroom}, Teacher: {s.t_first_name} {s.t_last_name}")
                
                elif len(commands) == 3:
                    if commands[2].startswith("B") or commands[2].startswith("Bus"): #get bus of student
                        for s in curr_student: #grade, classroom, teacher
                            print(f"{s.st_first_name} {s.st_last_name}, Bus Route: {s.bus}")
                    else:
                        print("Invalid request")
            else:
                print(f"Student last name: '{last_name}' not found")
                    
            
        #B[us] <route#>
        #----------------
        elif curr_command.startswith("B:") or curr_command.startswith("Bus:"):
            _, bus = curr_command.split(" ", 1) #take only one split, at the first space given
            bus_students = getStudentsByBus(students, bus)
            if bus_students:
                print(f"Students taking bus {bus}: ")
                for s in bus_students:
                    print(f"{s.st_first_name} {s.st_last_name}")
            else:
                print("No students found that take this bus")
        
        #T[eacher] <lastname>
        #----------------
        elif curr_command.startswith("T:") or curr_command.startswith("Teacher:"):
            _, teacher_ln = curr_command.split(" ", 1)
            teachers_students = getStudentsByTeacher(students, teacher_ln)
            if teachers_students:
                print(f"Students that have {teacher_ln} as their teacher: ")
                for s in teachers_students:
                    print(f"{s.st_first_name} {s.st_last_name}")
            else:
                print("No students found that have this teacher")

        #G[rade] <grade> [H[igh] || L[ow]]
        #----------------
        elif curr_command.startswith("G:") or curr_command.startswith("Grade:"):
            commands = curr_command.split(" ") #0: "G" 1: grade # 2: H or L
            grade = commands[1]
            if len(commands) == 3:
                if commands[2].startswith("H") or commands[2].startswith("Hight"): #asking for highest gpa in grade
                    s = getHighestGPAByGrade(students, grade)
                    if s:
                        print(f"Student with the highest GPA in grade {grade}:")
                        print(f"{s.st_first_name} {s.st_last_name}, GPA: {s.gpa}, Teacher: {s.t_first_name} {s.t_last_name}, Bus: {s.bus}")
                    else:
                        print(f"No students found in grade: {grade} ")
                elif commands[2].startswith("L") or commands[2].startswith("Low"): #asking for highest gpa in grade
                    s = getLowestGPAByGrade(students, grade)
                    if s:
                        print(f"Student with the lowest GPA in grade {grade}:")
                        print(f"{s.st_first_name} {s.st_last_name}, GPA: {s.gpa}, Teacher: {s.t_first_name} {s.t_last_name}, Bus: {s.bus}")
                    else:
                        print(f"No students found in grade: {grade} ")
                else:
                    print("High or Low not specified")
            elif len(commands) == 2:
                grade_students = getStudentsByGrade(students, grade)
                if grade_students: #got all students in grade -
                    print(f"Students in grade {grade}: ")
                    for s in grade_students:
                        print(f"{s.st_first_name} {s.st_last_name}")
                else:
                    print("No students found in this grade")
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

        #I[nfo]
        #----------------
        elif curr_command.startswith("I") or curr_command.startswith("Info"):
            getStudentInfo(students)

        else:
            print("Invalid command: please try again")



        

if __name__ == "__main__":
    main()