public class Main {
    public static void main(String[] args) {
        Person person1 = new Person("Ivan", "2004-04-08");
        Student student1 = new Student("Iacob Teodora", "2004-08-08", "3109RSL221");
        Student student2 = new Student("Martinas Maria", "2004-11-02", "3109rsl2321");
        Student student3 = new Student("Sacarescu Rebecca", "2003-12-04", "310982323");
        Student student4 = new Student("Asaftei Rares", "2003-04-29", "321AC232");
        Student student5 = new Student("Dummy", "2025-03-10", "32323131");
        student1.setNumOfProjects(2);
        student2.setNumOfProjects(2);
        student3.setNumOfProjects(2);
        student4.setNumOfProjects(2);
       // student5.setNumOfProjects(2);
        Teacher teacher1 = new Teacher("Moruz Alex", "1985-09-23", 2);
        Teacher teacher2 = new Teacher("Zaharia Mihai", "1970-08-17", 2);
        Project project1 = new Project("Intro to Unreal", ProjectType.THEORETICAL);
        Project project2 = new Project("Create Game Engine using Rust", ProjectType.PRACTICAL);
        Project project3 = new Project("JEE & Spring Components", ProjectType.THEORETICAL);
        Project project4 = new Project("Reactive Programming with Kotlin Project",  ProjectType.PRACTICAL);
        teacher1.addProject(project1);
        teacher1.addProject(project2);
        teacher2.addProject(project3);
        teacher2.addProject(project4);
        Problem problem1 = new Problem(4, 2);
        problem1.addStudent(student1);
        problem1.addStudent(student2);
        problem1.addStudent(student3);
        problem1.addStudent(student4);
       // problem1.addStudent(student5);
        problem1.addTeacher(teacher1);
        problem1.addTeacher(teacher2);
        problem1.printPersons();
        problem1.populateAvailableProjects();
        student1.addProject(project1);
        student1.addProject(project2);
        student2.addProject(project1);
        student2.addProject(project3);
        student3.addProject(project3);
        student3.addProject(project4);
        student4.addProject(project1);
        student4.addProject(project4);
       // student5.addProject(project2);
        //student5.addProject(project4);
        Solution solution1 = new Solution(problem1);
        solution1.initialize();
        solution1.solve();
        solution1.printAllocations();


    }
}