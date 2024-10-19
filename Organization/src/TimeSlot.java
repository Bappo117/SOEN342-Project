import java.time.LocalDateTime;

public class TimeSlot {

    LocalDateTime start;
    LocalDateTime end;
    Offering offering;

    
    public TimeSlot(LocalDateTime start, LocalDateTime end, Offering offering) {
        this.start = start;
        this.end = end;
        this.offering = offering;
    }


    public LocalDateTime getStart() {
        return start;
    }
    public void setStart(LocalDateTime start) {
        this.start = start;
    }


    public LocalDateTime getEnd() {
        return end;
    }
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }


    public Offering getOffering() {
        return offering;
    }
    public void setOffering(Offering offering) {
        this.offering = offering;
    }


    @Override
    public String toString() {
        return "TimeSlot [start=" + start + ", end=" + end + "]";
    }

    
}
