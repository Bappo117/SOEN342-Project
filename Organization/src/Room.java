import java.util.ArrayList;

public class Room {

    String name;
    ArrayList<Schedule> schedule;

    
    public Room(String name, ArrayList<Schedule> schedule) {
        this.name = name;
        this.schedule = schedule;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public ArrayList<Schedule> getSchedule() {
        return schedule;
    }
    public void setSchedule(ArrayList<Schedule> schedule) {
        this.schedule = schedule;
    }


}
