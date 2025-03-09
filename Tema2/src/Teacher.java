import java.util.Arrays;

/**
 * The {@code Teacher} class extends the {@code Person} class.
 * It represents a teacher who is associated with a specific number of projects and can add and manage these projects.
 */
public class Teacher extends Person {
    /**
     * Tracks the current index for adding projects
     */
    private int currentNumber = 0;
    int projectNumber;
    Project[] projects;

    /**
     * Constructs a new {@code Teacher} object with the given name, date of birth, and the number of projects.
     * This constructor initializes the teacher's name and date of birth through the {@code Person} class
     * and sets the number of projects this teacher will manage.
     *
     * @param name          The name of the teacher
     * @param dateOfBirth   The date of birth of the teacher
     * @param projectNumber The number of projects this teacher can manage
     */

    public Teacher(String name, String dateOfBirth, int projectNumber) {
        super(name, dateOfBirth);
        this.projectNumber = projectNumber;
        projects = new Project[projectNumber];
    }

    public int getProjectNumber() {
        return this.projectNumber;
    }

    /**
     * Adds a project to the teacher's list of managed projects.
     * If the project already exists in the list, it will not be added again.
     *
     * @param project The project to be added
     * @return {@code true} if the project was successfully added, {@code false} if the project already exists
     */

    public boolean addProject(Project project) {
        for (int i = 0; i < currentNumber; i++) {
            if (projects[i].equals(project)) {
                return false;
            }
        }
        projects[currentNumber] = project;
        currentNumber++;
        return true;
    }

    public Project[] getProjects() {
        return this.projects;
    }

    /**
     * Returns a string representation of the {@code Teacher} object, including the teacher's name, date of birth,
     * the number of projects, and the list of projects managed by the teacher.
     *
     * @return A string representation of the teacher
     */

    @Override
    public String toString() {
        return "Teacher{" + super.toString() +
                "projectNumber=" + projectNumber +
                ", projects=" + Arrays.toString(projects) +
                "} ";
    }

    /**
     * Compares this teacher to another object for equality.
     * The comparison is based on the teacher's name and date of birth.
     *
     * @param object The object to compare with this teacher
     * @return {@code true} if the given object is a teacher with the same name and date of birth, {@code false} otherwise
     */


    public boolean equals(Object object) {
        if (!(object instanceof Teacher teacher))
            return false;
        Teacher other = teacher;
        return this.name.equals(other.name) && this.dateOfBirth.equals(other.dateOfBirth);

    }
}
