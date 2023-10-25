package dev.onurb.travelassistant;

import dev.langchain4j.agent.tool.Tool;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class TripServices {

    private static final Random rand = new Random();

    private static final Map<Integer, Trip> trips = new HashMap<>();

    @Tool("""
        Booking a trip with the traveller's surname, first name and date of birth, as well as the destination and date of travel
        """)
    public Trip bookTrip(String lastname, String firstname, String birthDate, String tripDestination, String tripDate) {
        Trip trip = new Trip(rand.nextInt(Integer.SIZE - 1), lastname, firstname, birthDate, tripDestination, tripDate);
        System.out.println("\u001B[32m -> API FOR BOOKING A TRIP FOR " + trip + "\u001B[37m");
        trips.put(trip.id(), trip);
        return trip;
    }

    @Tool("""
        Cancel a trip using its id
        """)
    public boolean cancelTrip(int id) {
        System.out.println("\u001B[32m -> API FOR CANCELLING THE TRIP " + id + "\u001B[37m");
        return trips.remove(id) != null;
    }

    @Tool("""
        List booked trip of a customer
        """)
    public Set<String> listTrips(String lastname, String firstname) {
        System.out.println("\u001B[32m -> API FOR LINSTING TRIPS\u001B[37m");
        return trips.values().stream()
                .filter(trip -> lastname.equalsIgnoreCase(trip.lastname()) && firstname.equalsIgnoreCase(trip.firstname()))
                .map(trip -> trip.tripDestination() + " - " + trip.tripDate())
                .collect(Collectors.toSet());
    }
}
