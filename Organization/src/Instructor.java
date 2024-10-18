import java.util.ArrayList;

public class Instructor {

    String name;
    String phone;
    String specialization;
    ArrayList<String> cities;


    public Instructor(String name, String phone, String specialization, ArrayList<String> cities){
        this.name = name;
        this.phone = phone;
        this.specialization = specialization;
        this.cities = cities;
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


    public String getSpecialization() {
        return specialization;
    }
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }


    public ArrayList<String> getCities() {
        return cities;
    }
    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }


    
}
