public class Room {

    String name;
    Schedule schedule;

    
    public Room(String name, Schedule schedule) {
        this.name = name;
        this.schedule = schedule;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public Schedule getSchedule() {
        return schedule;
    }
    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }


    @Override
    public String toString() {
        return "Room [name=" + name + "]";
    }


}
