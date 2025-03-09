import java.time.LocalDate;

/**
 * Represents a person with a name and date of birth.
 * It provides methods to get and set the person's name
 * The date of birth is immutable once set.
 *
 * @author TeoIacob
 */

public class Person {
    public String name;
    public final LocalDate dateOfBirth;

    public Person(String name, String dateOfBirth) {
        this.name = name;
        this.dateOfBirth = LocalDate.parse(dateOfBirth);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    @Override
    public String toString() {
        return "Person : " +
                "name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'';
    }
}
