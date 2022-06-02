package simulation;

import java.util.ArrayList;
import java.util.Collection;

public class Human {

    ArrayList<Human> friends;
    int num_friends;
    int infectedWeeksLeft;
    boolean alive;
    boolean infected;
    boolean recovered;

    /**
     * @param num_friends number of other humans that the human will be
     *                  in contact with each week, and that could get infected
     */
    public Human(int num_friends){
        this.num_friends = num_friends;     //NEW this.
        alive = true;
        infected = false;
        friends = new ArrayList<>();
    }

    /**
     * adds persons that this Human knows and can have contact with
     * @param friendsOfHuman persons that the Human knows
     */
    public void addFriends(Collection<Human> friendsOfHuman)       //NEW changed paramter friends to friendsOfHuman because of possible collision
    {
        friends.addAll(friendsOfHuman);
    }

    /**
     * changes the status to infected and sets the time the Human will stay infected
     * @param d Disease with which the Human gets infected
     */
    public void infect(Disease d)
    {
        if(recovered || !alive)
            return;
        infected = true;
        infectedWeeksLeft = d.duration;
    }

    /**
     * updates the infected status after another week has passed, if the duration of
     * the infection is over the status is set to recovered
     */
    public void updateInfection()
    {
        if (infected)                   //wrote same, but other way for better understanding (for me)
            infectedWeeksLeft--;
        else return;

        if(infectedWeeksLeft == 0) {
            infected = false;
            recovered = true;
        }
    }
}
