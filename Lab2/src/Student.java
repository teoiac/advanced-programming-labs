import java.util.ArrayList;
import java.util.List;

public class Student {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String cnp;
    private String registrationNumber;
    private List<Project>projectsPrefereces = new ArrayList<Project>();
    public  Student(String firstName, String lastName, String dateOfBirth, String cnp, String registrationNumber,  List<Project> projectsPrefereces) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.cnp = cnp;
        this.registrationNumber = registrationNumber;
        this.projectsPrefereces = projectsPrefereces;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
    public String toString() {
        return firstName + " " + lastName + " " + dateOfBirth + " " + cnp + " " + registrationNumber + " " + projectsPrefereces;
    }

}
