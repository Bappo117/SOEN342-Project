import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Arrays;
import java.time.format.DateTimeFormatter;

public class Organization {

        String UserType;
        
        ArrayList<Instructor> instructors = new ArrayList<Instructor>();;
        ArrayList<RentedLocation> Locations = new ArrayList<RentedLocation>();;
        ArrayList<Offering> offerings = new ArrayList<Offering>();
        ArrayList<Offering> publicOfferings = new ArrayList<Offering>();

        Administrator admin = new Administrator();

        public Organization(){
            this.UserType = "public";
        }


        public Organization(ArrayList<Instructor> instructors, ArrayList<RentedLocation> locations,
            ArrayList<Offering> offerings, ArrayList<Offering> publicOfferings) {
            this.UserType = "public";
            this.instructors = instructors;
            this.Locations = locations;
            this.offerings = offerings;
            this.publicOfferings = publicOfferings;
        }


        public ArrayList<Instructor> getInstructors() {
            return instructors;
        }
        public void setInstructors(ArrayList<Instructor> instructors) {
            this.instructors = instructors;
        }


        public ArrayList<RentedLocation> getLocations() {
            return Locations;
        }
        public void setLocations(ArrayList<RentedLocation> locations) {
            Locations = locations;
        }


        public ArrayList<Offering> getOfferings() {
            return offerings;
        }
        public void setOfferings(ArrayList<Offering> offerings) {
            this.offerings = offerings;
        }


        public ArrayList<Offering> getPublicOfferings() {
            return publicOfferings;
        }
        public void setPublicOfferings(ArrayList<Offering> publicOfferings) {
            this.publicOfferings = publicOfferings;
        }

        
        //adds an instructor to the list of instructors.
        public void registerInstructor(){
            Scanner scan = new Scanner(System.in);
            System.out.print("Please enter your name.");
            String name = scan.nextLine();
            
            System.out.print("Please enter your phone number.");
            String phone = scan.nextLine();
            
            System.out.print("Please enter your specializations. (seperate with commas)");
            String[] specs = scan.nextLine().split(", ");
            ArrayList<String> specialization = new ArrayList(Arrays.asList(specs));

            System.out.print("Please enter the cities you are available in. (seperate with commas)");
            String[] City = scan.nextLine().split(", ");
            ArrayList<String> cities = new ArrayList(Arrays.asList(City));

            ArrayList<LocalDateTime> availabilities = this.enterAvailability();

            Instructor instructor = new Instructor(name, phone, specialization, cities, availabilities);
            instructors.add(instructor);
        }

        //this is meant to help a user log in as instructor or login.
        public void login(){
            Scanner scan = new Scanner(System.in);
            String input1;
            String input2;
            
            System.out.println("select which account to log into:");
            System.out.println("[1] Administrator");
            System.out.println("[2] Instructor");
            System.out.println("enter anything else to exit login.");

            if(scan.nextLine()== "1"){
                System.out.print("Username: ");
                input1 = scan.nextLine();
                System.out.print("Password");
                input2 = scan.nextLine();
                for(Instructor i : instructors){
                    if(input1 == i.getName() && input2 == i.getPhone()){
                        this.UserType = "Instructor";
                    }
                    else{
                        System.out.println("invalid credentials");
                    }
                    scan.close();
                }

            }
            else if(scan.nextLine() == "2"){
                System.out.print("Name: ");
                input1 = scan.nextLine();
                System.out.print("Phone number");
                input2 = scan.nextLine();

                if(input1 == admin.getUsername() && input2 == admin.getPassword()){
                    this.UserType = "Administrator";
                }
                else{
                    System.out.println("invalid credentials");
                }
                scan.close();
            }

            else{
                scan.close();
            }
        }
        
        //this is meant to print all offerings depending on whether you are public user, admin, or instructor.
        public void ViewOfferings(){
            if(UserType == "Instructor"){
                System.out.println("Here is all the current offerings with no instructors.");
                // for(RentedLocation loc: Locations){
                //     loc.toString();
                //     for(Room r: loc.getRooms()){
                //         System.out.println(r.getName());
                //         for(TimeSlot t: r.schedule.timeSlots){
                //             t.toString();
                //             for(Offering offers: offerings){
                //                 offers.
                //             }

                //         }
                //     }
                // }
                for(Offering offers: offerings){
                    offers.toString();
                }
            }
            else if(UserType == "Administrator"){
                System.out.println("Here is all the current offerings");
                for(Offering offers: offerings){
                    offers.toString();
                }
                for(Offering pubOffers: publicOfferings){
                    pubOffers.toString();
                }
            }
            else{
                for(Offering pubOffers: publicOfferings){
                    pubOffers.toString();
                }
            }
        }

        //this allows for the instructor to enter or change their availabilities.
        public ArrayList<LocalDateTime> enterAvailability(){
            final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            ArrayList<LocalDateTime> availabilities = new ArrayList<>();
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter your availabilities (format: yyyy-MM-dd HH:mm). Type 'done' to finish:");
            while (true) {
                System.out.print("Enter availability: ");
                String input = scan.nextLine();
                if (input.equalsIgnoreCase("done")) {
                    break;
                }
                try {
                    LocalDateTime dateTime = LocalDateTime.parse(input, format);
                    availabilities.add(dateTime);
                    System.out.println("Availability added: " + dateTime);
                } catch (Exception e) {
                    System.out.println("Invalid format. Please try again.");
                }
            }

            System.out.println("your availabilities are: ");
            for(LocalDateTime availability : availabilities) {
                System.out.println(availability.format(format));
            }

            scan.close();
            return availabilities;

        }

        //this function allows an instructor to take a course and make it available to public.
        public void takeOffering(){
            int i = 0;
            Scanner scan = new Scanner(System.in);
            System.out.println("Here is all the current offerings with no instructors.");
            for(Offering offers: offerings){
                System.out.print("["+i+"] ");
                offers.toString();
                i++;
            }
            i = 0;
            int j = Integer.valueOf(scan.nextLine());
            for(Offering offers: offerings){
                if(i == j){
                    offerings.remove(offers);
                    publicOfferings.add(offers);
                }
                i++;
            }
            scan.close();
        }

        //this function allows the administrator to make an offering with no instructor.
        public void makeOffering(){
            Scanner scan = new Scanner(System.in);
            int i = 0;
            System.out.println("Select Location to make offering.");
            for(RentedLocation L: Locations){
                System.out.print("["+i+"]");
                L.toString();
                i++;
            }
            i=0;
            int locationChoice = Integer.parseInt(scan.nextLine());
            RentedLocation RL = Locations.get(locationChoice);

            System.out.println("Select room where offering will take place.");
            for(Room r: RL.rooms){
               System.out.print("["+i+"]");
               r.toString();
               i++;
            }
            i=0;
            int roomChoice = Integer.parseInt(scan.nextLine());
            Room room1 = RL.rooms.get(roomChoice);
            

            System.out.println("time to make an offering. please enter the following information:");
            
            System.out.println("Type of lesson: ");
            String type = scan.nextLine();

            System.out.println("Private lesson? (true of false): ");
            String Priv = scan.nextLine();
            boolean Private = Boolean.parseBoolean(Priv);
            


            System.out.println("Please add the necessary data for this offerings time slot.");
            final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            System.out.println("Enter the lesson's start (format: yyyy-MM-dd HH:mm): ");
            String start = scan.nextLine();
            LocalDateTime startTime = null;
            try {
                startTime = LocalDateTime.parse(start, format);
            } catch (Exception e) {
                System.out.println("Invalid format. Please try again.");
            }
            System.out.println("Enter the lesson's end (format: yyyy-MM-dd HH:mm): ");
            String end = scan.nextLine();
            LocalDateTime endTime = null;
            try {
                endTime = LocalDateTime.parse(end, format);
            } catch (Exception e) {
                System.out.println("Invalid format. Please try again.");
            }


            Offering offering = new Offering(type, Private);
            offerings.add(offering);
            TimeSlot timeSlot = new TimeSlot(startTime, endTime, offering);
            room1.schedule.timeSlots.add(timeSlot);

            scan.close();
        }



        //this will run the organizations systems.
        public void run(){
            Scanner scan = new Scanner(System.in);

            System.out.println("hello, please select an option.");

            while(true){
                if(this.UserType == "public"){
                    System.out.println("[1] log in");
                    System.out.println("[2] register Instructor");
                    System.out.println("[3] view Offerings");
                    System.out.println("[4] Exit");
                    
                    String input = scan.nextLine();

                    if(input == "1"){
                        this.login();
                    }
                    else if(input == "2"){
                        this.registerInstructor();
                    }
                    else if(input == "3"){
                        this.ViewOfferings();
                    }
                    else if(input == "4"){
                        break;
                    }
                }
                else{
                    break;
                }
            }
            while(true){
                if(this.UserType == "Instructor"){
                    System.out.println("[1] log out");
                    System.out.println("[2] Take Offering");
                    System.out.println("[3] view Offerings");
                    System.out.println("[4] Exit");
                    
                    String input = scan.nextLine();

                    if(input == "1"){
                        this.UserType = "Public";
                    }
                    else if(input == "2"){
                        this.takeOffering();
                    }
                    else if(input == "3"){
                        this.ViewOfferings();
                    }
                    else if(input == "4"){
                        break;
                    }
                }
                else{
                    break;
                }
            }
            while(true){
                if(this.UserType == "Administrator"){
                    System.out.println("[1] log out");
                    System.out.println("[2] make Offering");
                    System.out.println("[3] view Offerings");
                    System.out.println("[4] Exit");
                    
                    String input = scan.nextLine();

                    if(input == "1"){
                        this.UserType = "Public";
                    }
                    else if(input == "2"){
                        this.makeOffering();
                    }
                    else if(input == "3"){
                        this.ViewOfferings();
                    }
                    else if(input == "4"){
                        break;
                    }
                }
                else{
                    break;
                }
            }
            scan.close();
        }


}
