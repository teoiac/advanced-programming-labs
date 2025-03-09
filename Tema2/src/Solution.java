/**
 * The {@code Solution} class represents a solution to the problem of allocating projects to students.
 * It uses a greedy and backtracking approach to assign projects to students based on their preferences and the availability
 * of projects proposed by teachers.
 */


public class Solution {
    private Problem problem;

    /** Array to track which projects have been allocated */

    private boolean[] check;
    private int[] allocation;

    /**
     * Constructs a new {@code Solution} object for the specified problem.
     * Initializes the arrays for tracking project allocations and project availability.
     *
     * @param problem The problem instance containing students, teachers, and projects
     */

    public Solution(Problem problem) {
        this.problem = problem;
        check = new boolean[problem.getNumOfTeachers() * 2]; // we assume each teacher proposed 2 projects
        allocation = new int[problem.getNumOfStudents()];
        for (int i = 0; i < problem.getNumOfStudents(); i++) {
            allocation[i] = -1;
        }
    }

    /**
     * Initializes the solution by populating the available projects and resetting the check array.
     */


    public void initialize() {
        problem.populateAvailableProjects();
        for (int i = 0; i < check.length; i++) {
            check[i] = false;
        }
    }

    /**
     * Attempts to allocate projects to students starting from the given student index using backtracking.
     * If all students are successfully allocated a project, it returns true.
     *
     * @param studentIndex The index of the student to allocate a project to
     * @return {@code true} if all students have been allocated a project, {@code false} otherwise
     */

    public boolean allocateProjects(int studentIndex) {
        if (studentIndex == problem.getNumOfStudents()) {
            return true;
        }
        Student student = problem.students[studentIndex];
        for (int j = 0; j < student.getNumOfProjects(); j++) {
            Project preferredProject = student.getPreferredProject(j);
            for (int k = 0; k < problem.availableProjects.length; k++) {
                if (problem.availableProjects[k] != null && problem.availableProjects[k].equals(preferredProject) && !check[k]) {
                    check[k] = true;
                    allocation[studentIndex] = k;
                    if (allocateProjects(studentIndex + 1)) {
                        return true;
                    }
                    check[k] = false;
                    allocation[studentIndex] = -1;
                }
            }
        }
        return false;
    }

    /**
     * Solves the problem of allocating projects to all students by calling the {@code allocateProjects} method.
     * If no allocation is found, it prints "No allocation found".
     */

    public void solve() {
        if (!allocateProjects(0)) {
            System.out.println("No allocation found");
        }
    }

    public void printAllocations() {
        System.out.println("Student Project Allocations:");
        for (int i = 0; i < problem.getNumOfStudents(); i++) {
            System.out.print("Student: " + problem.students[i].getName() + " -> ");
            if (allocation[i] != -1) {
                System.out.println("Project : " + problem.availableProjects[allocation[i]].getTitle());
            } else {
                System.out.println("No project assined");
            }
        }
    }
}
