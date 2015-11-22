package assignment.test.raghu.peppersqaure.models;

/**
 * Created by Raghunandan on 22-11-2015.
 */
public class Actors {

    //http://microblogging.wingnity.com/JSONParsingTutorial/jsonActors
    private String name,description,dob,country,height,spouse,children,image;
    private int id,fav;

    public int getFav() {
        return fav;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDob() {
        return dob;
    }

    public String getCountry() {
        return country;
    }

    public String getHeight() {
        return height;
    }

    public String getSpouse() {
        return spouse;
    }

    public String getChildren() {
        return children;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFav(int fav) {
        this.fav = fav;
    }
}

