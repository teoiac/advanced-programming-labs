public class Project {
    private String id;
    private String title;
    private ProjectType type;
    private String description;

    public Project(String id, String title, ProjectType type, String description){
        this.id = id;
        this.title = title;
        this.type = type;
        this.description = description;
    }
    public String getId(){
        return this.id;
    }
    public String getTitle(){
        return this.title;
    }
    public ProjectType getType(){
        return this.type;
    }
    public String getDescription(){
        return this.description;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setType(ProjectType type){
        this.type = type;
    }
    public void setDescription(String description){
        this.description = description;
    }
    @Override
    public String toString(){
        return this.id + " " + this.title + " " + this.type + " " + this.description;
    }

}
