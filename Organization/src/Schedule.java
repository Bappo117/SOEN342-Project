import java.util.ArrayList;

public class Schedule {
    ArrayList<TimeSlot> timeSlots;

    
    public Schedule(ArrayList<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }


    public ArrayList<TimeSlot> getTimeSlots() {
        return timeSlots;
    }
    public void setTimeSlots(ArrayList<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }

    
}
