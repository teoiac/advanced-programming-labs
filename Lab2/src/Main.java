import java.util.List;

public class Main {
    public static void main(String[] args) {

        Project project1 = new Project("12", "Mobile app", ProjectType.Practical,"Mobile development using Kotlin");
        Project project2 = new Project("13", "AI in Medical Field", ProjectType.Theoretical,"How AI can detect tumors in early stages");
        Student s1 = new Student("Teodora", "Iacob", "08-04-2004", "604044020234", "3109Rsl2323", List.of(project1, project2));
        System.out.println(s1);
        }
    }
