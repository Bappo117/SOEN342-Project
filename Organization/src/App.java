import java.util.ArrayList;

import javax.tools.JavaFileManager.Location;

public class App {
    public static void main(String[] args) throws Exception {

        ArrayList<Instructor> instructors = new ArrayList<Instructor>();
        ArrayList<RentedLocation> Locations = new ArrayList<RentedLocation>();
        ArrayList<Offering> offerings = new ArrayList<Offering>();
        ArrayList<Offering> publicOfferings = new ArrayList<Offering>();

        Schedule s1 = new Schedule(null);
        Schedule s2 = new Schedule(null);
        Schedule s3 = new Schedule(null);

        ArrayList<Room> rooms = new ArrayList();
        rooms.add(new Room("EV203",s1));
        rooms.add(new Room("EV204",s2));
        rooms.add(new Room("EV205",s3));

        Locations.add(new RentedLocation("EV Building", "Montreal", rooms));


        Organization org = new Organization(instructors, Locations, offerings, publicOfferings);


    }
}
