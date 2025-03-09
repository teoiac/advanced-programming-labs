/**
 * The {@code Problem} class represents a problem in which students and teachers are managed,
 * and projects are proposed by teachers and assigned to students.
 * It allows adding students and teachers to the problem, displaying the list of persons, and
 * populating available projects from teachers.
 */


public class Problem {
    private int numOfStudents;
    private int currentStudent = 0;
    private int numOfTeachers;
    private int currentTeacher = 0;
    private int currentProject = 0;
    protected Student[] students;
    protected Teacher[] teachers;
    protected String[] persons;
    protected Project[] availableProjects;
    public Problem(int numOfStudents, int numOfTeachers) {
        this.numOfStudents = numOfStudents;
        this.numOfTeachers = numOfTeachers;
        students = new Student[numOfStudents];
        teachers = new Teacher[numOfTeachers];
    }

    /**
     * Adds a student to the problem. The student is only added if they are not already present.
     *
     * @param student The student to be added
     * @return {@code true} if the student was successfully added, {@code false} if the student is already in the problem
     */

    public boolean addStudent(Student student) {
        for (int i = 0; i < currentStudent; i++) {
            if (students[i].equals(student)) {
                return false;
            }
        }
        students[currentStudent] = student;
        currentStudent++;
        return true;
    }
    public int getNumOfStudents(){
        return numOfStudents;
    }

    /**
     * Adds a teacher to the problem. The teacher is only added if they are not already present.
     *
     * @param teacher The teacher to be added
     * @return {@code true} if the teacher was successfully added, {@code false} if the teacher is already in the problem
     */

    public boolean addTeacher(Teacher teacher) {
        for (int i = 0; i < currentTeacher; i++) {
            if (teachers[i].equals(teacher)) {
                return false;
            }
        }
        teachers[currentTeacher] = teacher;
        currentTeacher++;
        return true;
    }

    public int getNumOfTeachers(){
        return numOfTeachers;
    }

    /**
     * Prints the names of all students and teachers in the problem.
     * The names are printed in the order of students followed by teachers.
     */

    public void printPersons() {
        persons = new String[numOfStudents + numOfTeachers];
        int index = 0;
        for (int i = 0; i < numOfStudents; i++) {
            persons[index] = students[i].getName();
            index++;
        }
        for (int i = 0; i < numOfTeachers; i++) {
            persons[index] = teachers[i].getName();
            index++;
        }
        for (int i = 0; i < numOfStudents + numOfTeachers; i++) {
            System.out.print(persons[i] + " ");
        }
        System.out.println();
    }


    /**
     * Populates the array of available projects by gathering projects from teachers.
     * Each teacher can propose a set of projects, and duplicates are removed.
     * The list of available projects is then printed to the console.
     */


    public void populateAvailableProjects() {
       availableProjects = new Project[numOfTeachers * 2]; // Assuming each teacher proposes at most 2 projects for demonstration
        int tempIndex = 0;
        for (int i = 0; i < numOfTeachers; i++) {
            Teacher teacher = teachers[i];
            Project[] proposedProjects = teacher.getProjects();
            for (int j = 0; j < proposedProjects.length; j++) {
                if (proposedProjects[j] != null) {
                    boolean alreadyExists = false;
                    for (int k = 0; k < tempIndex; k++) {
                        if (availableProjects[k].equals(proposedProjects[j])) {
                            alreadyExists = true;
                            break;
                        }
                    }
                    if (!alreadyExists) {
                        availableProjects[tempIndex++] = proposedProjects[j];
                    }
                }
            }
        }
        for(int i=0;i< availableProjects.length;i++){
            System.out.println(availableProjects[i]);
        }
    }


}
