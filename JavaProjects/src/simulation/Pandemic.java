package simulation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import static simulation.Config.*;      //NEW imported final class Config

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;     //NEW import collections and Iterator
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Pandemic {
    static Disease d;                                           //NEW made all static and initialize
    static ArrayList<Human> population = new ArrayList<>();
    static HashSet<Human> currentlyInfected = new HashSet<>(); //NEW made currenlyInfected, recovered and dead HashSet, so no dublicates
    static HashSet<Human> recovered = new HashSet<>();
    static HashSet<Human> dead = new HashSet<>();

    public Pandemic(int populationSize, Disease disease){
        d = disease;
        int friendsRange = MAX_FRIENDS - MIN_FRIENDS + 1; // = 191

        for(int i=0; i<populationSize; i++){
            int friends = (int)(MIN_FRIENDS + (Math.random()*friendsRange));    //NEW cast to int
            Human human = new Human(friends);
            population.add(human);
        }
        //add friends to humans
        for(Human h : population){
            h.addFriends(getRandomSubset(population,h.num_friends));
        }
        //initialize infections
        for(Human selected : getRandomSubset(population,INITIALLY_INFECTED_HUMANS)) //no class neded to use getrandomsubset here
        {
            selected.infect(d);
            currentlyInfected.add(selected);
        }
    }

    /** select a random subset of a given size from a given set
     * @param set List of human from which the subset is sampled
     * @param size size of the sampled subset
     * @return random subset of set with size size
     */
    private static HashSet<Human> getRandomSubset(List<Human> set, int size){      //NEW make static
        HashSet<Human> subset = new HashSet<>();
        while(subset.size()<size){
            subset.add(set.get((int)(Math.random()*set.size())));
        }
        return subset;
    }

    /** simulates the changes of infected/recovered/dead people in the population
     */
    public static void simulateWeek(){
        //infected will infect others
        ArrayList<Human> newlyInfected = new ArrayList<>();
        for (Iterator<Human> itr = currentlyInfected.iterator(); itr.hasNext(); ) { //NEW use Iterator if removing some Elements from List
            Human h = itr.next();
            for(Human f : getRandomSubset(h.friends, (int) d.infection_rate)){  //NEW No calculations like num_contacts or num_infected needed, Human h can only infect infection_rate persons
                if(f.infected)
                    continue;
                f.infect(d);
                if(f.infected)
                    newlyInfected.add(f);
            }
        }
        currentlyInfected.addAll(newlyInfected);        //NEW add all newlyInfected into "all"- infected

        //some infected will die
        Iterator<Human>iterator1 = population.iterator();   //NEW same with Iterator
        ArrayList<Human> deadhuman = new ArrayList<>();
        for (Iterator<Human> iterator = currentlyInfected.iterator(); iterator.hasNext(); ) {
            Human human = iterator.next();
            Human human1 = iterator1.next();
            double r = Math.random();
            if (r < d.mortality_rate) {     //NEW random should be smaller than mortality rate
                human.alive = false;
                deadhuman.add(human);
                iterator1.remove();
                iterator.remove();
            }
            dead.addAll(deadhuman);     //NEW add deadhuman into "all"-deads
        }

        //some infected will recover
        ArrayList<Human> newlyRecovered = new ArrayList<>();
            for (Iterator<Human> it = currentlyInfected.iterator(); it.hasNext(); ) {   //NEW same with Iterator
                Human hmn = it.next();
                hmn.updateInfection();
                if(hmn.recovered && !(recovered.contains(hmn)))     //NEW avoid dublicates in recovered (not nessesary for HashSet)
                newlyRecovered.add(hmn);
            }
        recovered.addAll(newlyRecovered);
        currentlyInfected.removeAll(newlyRecovered);
    }


    /**
     * simulates the developments of dead/infected/recovered cases over multiple weeks and creates a plot
     * @param weeks number of weeks that should be simulated
     * @param image file to which the plot should be saved
     */
    public void simulate(int weeks, File image) {       //NEW remove all this.
        //plotting of number of dead/recovered/infected over time
        XYSeries deadLine = new XYSeries("dead");
        XYSeries recoveredLine = new XYSeries("recovered");
        XYSeries infectedLine = new XYSeries("infected");
        deadLine.add(0,dead.size());
        recoveredLine.add(0,recovered.size());
        infectedLine.add(0,currentlyInfected.size());
        System.out.println("#dead\tinfected\trecovered");
        System.out.println(dead.size() + "\t" + currentlyInfected.size() + "\t" + recovered.size());
        //simulation of the developments each week and update of plot
        for (int w = 0; w < weeks; w++) {
            simulateWeek();
            deadLine.add(w+1,dead.size());
            recoveredLine.add(w+1,recovered.size());
            infectedLine.add(w+1,currentlyInfected.size());
            System.out.println(dead.size() + "\t" + currentlyInfected.size() + "\t" + recovered.size());
        }
        //save plot as png
        XYSeriesCollection lines = new XYSeriesCollection();
        lines.addSeries(deadLine);
        lines.addSeries(recoveredLine);
        lines.addSeries(infectedLine);
        JFreeChart chart = ChartFactory.createXYLineChart(d.name,"weeks","humans",lines, PlotOrientation.VERTICAL, true,false,false);
        try {
            ChartUtils.saveChartAsPNG(image,chart,480,480);     //NEW change ChartUtilities to ChartUtils
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Disease corona = new Disease("Covid19",3,0.02,2);
        Pandemic p = new Pandemic(100000,corona);
        File image = new File(corona.name+".png");  //NEW args[1]+File.separator not needed
        p.simulate(20, image);
    }
}
