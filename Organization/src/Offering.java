//import java.util.ArrayList;

public class Offering {

    String type;
    Boolean Private;
    Instructor instructor;
    //ArrayList<Bookings> bookings;

    
    public Offering(String type, Boolean private1, Instructor instructor) {
        this.type = type;
        Private = private1;
        this.instructor = instructor;
    }


    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }


    public Boolean getPrivate() {
        return Private;
    }
    public void setPrivate(Boolean private1) {
        Private = private1;
    }


    public Instructor getInstructor() {
        return instructor;
    }
    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }


    
}
