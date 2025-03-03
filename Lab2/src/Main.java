import java.util.List;

public class Main {
    public static void main(String[] args) {

        Project project1 = new Project("12", "Mobile app", ProjectType.Practical,"Mobile development using Kotlin");
        Project project2 = new Project("13", "AI in Medical Field", ProjectType.Theoretical,"How AI can detect tumors in early stages");
        Project project3 = new Project("15", "Game engine", ProjectType.Practical, "Build a simple game engine using Rust");
        Project project4 = new Project("16", "Time complexity analysis", ProjectType.Theoretical, "Complexity of well-known Algorithms");
        Student s1 = new Student("Teodora", "Iacob", "08-04-2004", "604044020234", "3109Rsl2323", List.of(project1, project2));
        Student s2 = new Student("Andrei", "Popescu", "12-09-2002", "5638793857", "3109383RSK343");
        s2.addProject(project3);
        s2.addProject(project4);
        System.out.println(s1);
        System.out.println(s2);
        }
    }
