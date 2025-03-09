
/**
 * The {@code Student} class extends the {@code Person} class.
 * In addition to the attributes from the {@code Person} class, it has an array of {@code prefferedProjects}
 * and an immutable {@code registrationNumber}.
 * This class allows managing a student's preferred projects and provides methods for retrieving and updating them.
 * @author TeoIacob
 */
public class Student extends Person {
    private final String registrationNumber;
    private int numOfProjects;
    private int currentProject = 0;
    public Project[] prefferedProjects;

    /**
     * Constructs a new {@code Student} object with the given name, date of birth, and registration number.
     * This constructor initializes the student's name and date of birth through the {@code Person} class
     * and sets the registration number.
     *
     * @param name The name of the student
     * @param dateOfBirth The date of birth of the student
     * @param registrationNumber The unique registration number of the student
     */
    
    public Student(String name, String dateOfBirth, String registrationNumber) {
        super(name, dateOfBirth);
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Returns a string representation of the {@code Student} object, including the student's
     * registration number and the string representation of the superclass {@code Person}.
     *
     * @return A string representation of the student
     */
    @Override
    public String toString() {
        return "Student{" +
                "registrationNumber='" + registrationNumber + '\'' +
                "} " + super.toString();
    }

    public void setNumOfProjects(int numOfProjects) {
        this.numOfProjects = numOfProjects;
        prefferedProjects = new Project[numOfProjects];
    }
    public int getNumOfProjects() {
        return numOfProjects;
    }

    /**
     * Compares this student to another object for equality.
     * The comparison is based on the student's unique registration number.
     *
     * @param o The object to compare with this student
     * @return {@code true} if the given object is a student and has the same registration number,
     *         {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Student))
            return false;
        Student other = (Student) o;
        return registrationNumber.equals(other.registrationNumber);
    }

    public void addProject(Project project) {
        prefferedProjects[currentProject] = project;
        currentProject++;
    }

    public Project getPreferredProject(int j) {
        return prefferedProjects[j];
    }
}
