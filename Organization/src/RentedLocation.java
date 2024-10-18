import java.util.ArrayList;

public class RentedLocation {
    
    String name;
    String city;
    ArrayList<Room> rooms;
    
    public RentedLocation(String name, String city, ArrayList<Room> rooms) {
        this.name = name;
        this.city = city;
        this.rooms = rooms;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    
    public ArrayList<Room> getRooms() {
        return rooms;
    }
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    
}
