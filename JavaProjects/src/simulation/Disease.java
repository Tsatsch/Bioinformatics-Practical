package simulation;     //NEW package added

public class Disease {

    String name;
    double infection_rate;
    double mortality_rate;
    int duration;


    /**
     * a disease like a virus or infection
     * @param name of the disease
     * @param infection_rate number of other humans that an infected human will infect each week
     * @param mortality_rate probability that an infected human will die (per week)
     * @param duration number of weeks that the infection lasts before the human will recover
     */
    public Disease(String name, double infection_rate, double mortality_rate, int duration)
    {
        this.name = name;
        this.infection_rate = infection_rate;
        this.mortality_rate = mortality_rate;
        this.duration = duration;       //NEW duration initialization
    }
}

