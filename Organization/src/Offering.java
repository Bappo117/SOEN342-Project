//import java.util.ArrayList;

public class Offering {

    String type;
    Boolean Private;
    Instructor instructor;
    Room room;
    //ArrayList<Bookings> bookings;

    
    public Offering(String type, Boolean private1, Room room) {
        this.type = type;
        this.Private = private1;
        this.room = room;
        this.instructor = null;
    }
    public Offering(String type, Boolean private1) {
        this.type = type;
        this.Private = private1;
        this.room = null;
        this.instructor = null;
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



    @Override
    public String toString() {
        if(this.instructor == null){
            return "Offering [type=" + type + ", Private=" + Private + ", instructor= None ]";
        }
        else{
            return "Offering [type=" + type + ", Private=" + Private + ", instructor=" + instructor.name +"]";
        }
    }


    
}
