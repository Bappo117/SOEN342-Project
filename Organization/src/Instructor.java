import java.util.ArrayList;
import java.time.LocalDateTime;

public class Instructor {

    String name;
    String phone;
    ArrayList<String> specialization;
    ArrayList<String> cities;
    ArrayList<LocalDateTime> availabilities;


    public Instructor(String name, String phone, ArrayList<String> specialization, ArrayList<String> cities, ArrayList<LocalDateTime> availabilities){
        this.name = name;
        this.phone = phone;
        this.specialization = specialization;
        this.cities = cities;
        this.availabilities = availabilities;
    }


    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }


    public ArrayList<String> getSpecialization() {
        return specialization;
    }
    public void setSpecialization(ArrayList<String> specialization) {
        this.specialization = specialization;
    }


    public ArrayList<String> getCities() {
        return cities;
    }
    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }


    public ArrayList<LocalDateTime> getAvailabilities() {
        return availabilities;
    }
    public void setAvailabilities(ArrayList<LocalDateTime> availabilities) {
        this.availabilities = availabilities;
    }


    
}
