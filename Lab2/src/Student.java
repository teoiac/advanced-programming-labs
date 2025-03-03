import java.util.ArrayList;
import java.util.List;

public class Student {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String cnp;
    private String registrationNumber;
    private List<Project>projectsPreferences = new ArrayList<>();
    public  Student(String firstName, String lastName, String dateOfBirth, String cnp, String registrationNumber,  List<Project> projectsPreferences) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.cnp = cnp;
        this.registrationNumber = registrationNumber;
        this.projectsPreferences = projectsPreferences;
    }
    public Student(String firstName, String lastName, String dateOfBirth, String cnp, String registrationNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.cnp = cnp;
        this.registrationNumber = registrationNumber;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void addProject(Project project){
        this.projectsPreferences.add(project);
    }

    public String getLastName() {
        return lastName;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public String getCnp() {
        return cnp;
    }
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + dateOfBirth + " " + cnp + " " + registrationNumber + " " + projectsPreferences;
    }

}
