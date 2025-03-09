/**
 * The {@code Project} class represents a project with a title and a type.
 * It provides methods for accessing and modifying the title and type of the project,
 * as well as overriding {@code toString()} and {@code equals()} for better usability.
 */


public class Project {
    private String title;
    private ProjectType type;

    public Project(String title, ProjectType type) {
        this.title = title;
        this.type = type;
    }


    public String getTitle() {
        return this.title;
    }

    public ProjectType getType() {
        return this.type;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return this.title + " " + this.type;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || !(object instanceof Project))
            return false;
        Project project = (Project) object;
        if (this.title.equals(project.title) && this.type.equals(project.type))
            return true;
        return false;
    }

}
