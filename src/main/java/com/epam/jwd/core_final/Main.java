package com.epam.jwd.core_final;


import com.epam.jwd.core_final.context.Application;
import com.epam.jwd.core_final.exception.InvalidStateException;

public class Main {

    public static void main(String[] args) {
        try {
            Application.start();
        } catch (InvalidStateException e) {
            e.printStackTrace();
        }
       /* ApplicationContext context = NassaContext.getInstance();
        context.init();
        List<Planet> planets = (List<Planet>) context.retrieveBaseEntityList(Planet.class);
        Planet from = planets.get(3);
        Planet to = planets.get(9);*/



/*        NassaContext context = NassaContext.getInstance();
        context.init();
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now();
        String name = "start";
        long dist = 123695478445668L;

        List<Spaceship> ships = (List<Spaceship>) context.retrieveBaseEntityList(Spaceship.class);
        Spaceship spaceship = ships.get(3);
        List<CrewMember> assignedCrew = (List<CrewMember>) context.retrieveBaseEntityList(CrewMember.class);
        MissionResult result = MissionResult.COMPLETED;

        List<Planet> planets = (List<Planet>) context.retrieveBaseEntityList(Planet.class);
        Planet from = planets.get(3);
        Planet to = planets.get(9);

        FlightMission fm = new FlightMission(name, start, end, dist);
        fm.setAssignedSpaceShip(spaceship);
        fm.setAssignedCrew(assignedCrew);
        fm.setFrom(from);
        fm.setTo(to);
        fm.setMissionResult(result);
        //System.out.println(fm);


        Long max =  ships.stream().mapToLong(n->spaceship.getFlightDistance()).max().orElse(0L) ;
        System.out.println(max);*/




        /*SpaceMapReader readerSM = SpaceMapReader.getInstance();
        Collection<Planet> planetList = readerSM.initPlanets();
        planetList.forEach(System.out::println);

        CrewReader readerCM = CrewReader.getInstance();
        Collection<CrewMember> crewMembers = readerCM.initCrewMember();
        crewMembers.forEach(System.out::println);

        SpaceshipReader reader = SpaceshipReader.getInstance();
        Collection<Spaceship> spaceships = reader.initSpaceship();
        spaceships.forEach(System.out::println);*/

    }
}