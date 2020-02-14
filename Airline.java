import java.util.*;
import static java.lang.Math.*;
import static java.lang.Character.*;

public class Airline{
 
   static Scanner input = new Scanner ( System.in);
   private static Flight[] FlightsList = new Flight[100];
 
   public static void main ( String [] args ){// start main
   
   
      char choice = ' ';
   
      for ( int i = 0 ; i < FlightsList.length; i++){
         FlightsList[i] = new Flight();
         
      }
   
   
       
      do{
       
         System.out.println("Please what you would like to do: \n(1) Add a Flight. \n(2) Find a flight. \n(3) List all flights. \n(4) List flights for a given date. \n(5) Update Departure & Arrival time. \n(6) Total number of flights. \n(7) Exit.");
         choice = input.next().charAt(0);
         
         
         switch ( choice ){
          
         
            case '1' :
            
            
               System.out.println("choose the destination: \n 0- Dammam\n 1- Jeddah\n 2- Yanbu\n 3- Hail\n 4- Abha\n 5- Tabuk\n 6- Taif ");
               int des = input.nextInt();
               
               System.out.println("enter the date: dd/mm ");
               String date = input.next();
               
               System.out.println("enter the gate: ");
               int gate = input.nextInt();
               
               System.out.println("enter the departure time: hh:mm ");
               String dep = input.next();
            
            
               if ( addFlight( des, date, gate,dep) ) 
                  System.out.println("the flight was added successfully");
               else 
                  System.out.println("couldn't add the flight, please make sure of your information");
            
               break;
         
         
         
            case '2':
            
            
               System.out.println("Enter the flight number: for example DAM003 ");
               String flightNum = input.next();
               int result = findFlight(flightNum);
               
               if ( result == -1)
                  System.out.println("flight was not found: " + result );
               else 
                  System.out.println("flight was found at the index: " + result );
            
            
               break;
         
              
            case '3':
            
            
               printAll();
               break;
         
         
            case '4':
            
            
               System.out.println("Enter the date: dd/mm ");
               String userDate = input.next();
               
               printAll(userDate);
            
               break;
         
         
            case '5':
            
            
               System.out.println("Enter the flight number: for example DAM003 ");
               String userFlightNumber = input.next();
               
               System.out.println("Enter the departure time: hh:mm ");
               String userDepartureTime = input.next();
               
               updateTime(userFlightNumber,userDepartureTime);
               
               break;
               
               
            case '6':
            
               System.out.println("The number of flights is: " + getNumberOfFlights());
            
               break;
         
         
            case '7':
               break;
         
         
            default : 
               System.out.println("invalid");
               
         }// end switch
         
         
      }while(choice != '7');
          
   
   
   }// end main 


   public static boolean addFlight(int destination, String date, int gate, String departure){
   
      int day  = Integer.parseInt( date.substring(0,2) );
      int month = Integer.parseInt( date.substring(3) );
    
      int hour  = Integer.parseInt( departure.substring(0,2) );
      int min = Integer.parseInt( departure.substring(3) );
      boolean check = false;
   
      if ( Flight.TotalFlights < 100 ) 
         if ( destination >= 0 && destination < 6 )
         
            if ( date.length() == 5 && date.charAt(2) == '/' ) 
            
               if ( day > 0 && day <= 31 )
                  if ( month > 0 && month <=12 ) 
                  
                     if ( departure.length() == 5 && departure.charAt(2) == ':' ) 
                     
                        if ( hour > 0 && hour <= 24 )
                           if ( min > 0 && min <=60 ){
                           
                              for (int i = 0 ; i < FlightsList.length; i++){
                              
                                 if ( (FlightsList[i].getDestination() == destination) && (FlightsList[i].getDate().equals(date)) && (FlightsList[i].getGate() == gate) && (FlightsList[i].getDeparture().equals(departure)) ){ 
                                          
                                    check = false;
                                    break;
                                           
                                 }else
                                    check = true;                                    
                              }                                                            
                           
                           }       
           
      if(check){
         FlightsList[Flight.TotalFlights] = new Flight(destination, date, gate, departure);
         return check;
      }
      return false;
   
   }

   
   
   public static int findFlight( String flightNumber ){
   
   
      flightNumber = flightNumber.toUpperCase();
    
      for ( int i = 0; i < FlightsList.length ; i++){
      
         if ( flightNumber.equals(FlightsList[i].getFlightNumber()) )
            return i;
      }
   
      return -1;
      
   }
   
   
   
   public static void printAll(){
      
      for ( int i = 0 ; i < Flight.TotalFlights ; i++ )
         FlightsList[i].printFlightInfo();
      
   }
   
   
   
   public static void printAll( String date ){
   
     
      for ( int i = 0 ; i < Flight.TotalFlights ; i++ )
         if(FlightsList[i].getDate().equals(date))
            FlightsList[i].printFlightInfo();
   
   }

   
   
   public static void updateTime( String FlightNumber, String departure ){
   
      for ( int i = 0 ; i < Flight.TotalFlights ; i++ ) {
         if(FlightsList[i].getFlightNumber().equals(FlightNumber)){
            FlightsList[i].setDeparture(departure);
            FlightsList[i].printFlightInfo();
            return;
         } 
      }
   
   
   } 

   public static int getNumberOfFlights(){
   
      return Flight.TotalFlights;
   
   }


}// end class Airline



class Flight{// start class flight 


   private String FlightNumber;
   private String[] Cities = {"dammam", "jeddah", "yanbu", "hail", "abha", "tabuk", "taif"};
   private int Destination;
   private int Gate;
   private String Date;
   private String DepartureTime;
   private String ArrivalTime;
   public static int TotalFlights;

   public Flight(){
   
      FlightNumber= "";
      Destination=  0;
      Gate= 0;
      Date= "";
      DepartureTime= "";
      ArrivalTime= "";
      TotalFlights= 0;
      
   }// counstructer method flight 


   public Flight (int destination, String date, int gate, String departure){
   
      Destination = destination;
      Gate = gate; 
      Date = date;
      DepartureTime = departure;
      caluclateArrivalTime();
      generateFlightNumber();
   
      TotalFlights++;
      generateFlightNumber();

   }//second method constructer
   
   
   //Setter:
      
   public void setDestination( int destination ){
      Destination = destination;
   }


   public void setGate( int gate ){
      Gate = gate;
   }
 
 
   public void setDate( String date ){
      Date = date;
   }
 
 
   public void setDeparture( String departure ){
      DepartureTime = departure;
      caluclateArrivalTime();
   }
   
  
   
   // getter:
  
 
   public int getDestination(){
      return Destination;
   }
   

   public int getGate(){
      return Gate;
   }
 
 
   public String getDate(){
      return Date;
   }
   
   
   public String getFlightNumber(){
      return FlightNumber;
   }
      
 
   public String getDeparture(){
      return DepartureTime;
   }
   
   
 
 
 
 //Methods:
 
   public void generateFlightNumber(){ // start Generate method.
   
   
      FlightNumber = Cities[Destination].substring(0, 3).toUpperCase() +"00"+TotalFlights;
   
   
   } //end Generate method
   
   
   public void caluclateArrivalTime(){// start caluclateArraivalTime method.
   
      int cityIndex = Destination;
      int duration=0;
      int hours = 0;
      int min = 0;
      int departureHours = 0;
      int departureMin =0;
      int arrivalHours = 0;
      int arrivalMin = 0;
   
   
      switch (cityIndex){
      
         case 0:
            duration = 65;
            break;
      
         case 1: 
         case 2:
         case 4:
            duration = 105;
            break;
      
         case 3: 
            duration = 75;
            break;
      
         case 5:
            duration = 80;
            break;
      
         case 6:
            duration = 95;
            break; 
      }//end switch
      
      hours = duration / 60;
      min = duration % 60;  
   
      departureHours = Integer.parseInt( DepartureTime.substring(0, 2) );
      departureMin = Integer.parseInt( DepartureTime.substring(3) );
   
      arrivalHours = departureHours + hours;
      arrivalMin = departureMin + min;
   
      if ( arrivalMin > 59 ) 
         arrivalHours++;
   
      arrivalMin %= 60;
   
      boolean plus = arrivalHours > 23;
      
      if ( plus ) 
         arrivalHours %= 24;
   
      String h = arrivalHours + "";
      String m = arrivalMin + "";
   
      if ( h.length() < 2 ) 
         h = "0" + h;
   
      if ( m.length() < 2 ) 
         m = "0" + m;
   
      if ( plus )
         ArrivalTime = h+ ":" +m+ "+1";
      else
         ArrivalTime = h+ ":" +m;
   
   } //end caluclateArrivalTime method
   
   
   public void printFlightInfo(){// start printFlightInfo method.
   
      System.out.printf("Flight Number: %-15s Gate: %-15s%n", FlightNumber, "(" + Gate + ")" );
      System.out.printf("Destination: %-15s Date: %-15s%n", Cities[Destination], Date );
      System.out.printf("Departure Time: %-15s Arrival Time: %-15s%n", DepartureTime, ArrivalTime );
      System.out.println("_____________________________________________________");
   
   } //end printFlightInfo method


}// end class flight 


