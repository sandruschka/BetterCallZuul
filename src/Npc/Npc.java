package Npc;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import betterCallZuul.Game;
import betterCallZuul.Room;

public class Npc {

    private String name;
    private Room currentRoom;
    private static int SECOND = 1000;
    
    /**
     * @see zuul.LivingEntity
     * @param name
     * @param room
     */
    public Npc(String name, Room room) {
    	currentRoom = room;
        this.name = name;
    }
    
    /**
     *
     * @return the name of the npc
     */
    public String getName() {
        return name;
    }
    
    /**
     * this function makes the npc move about the university on its own
     */
    public void computerControlledNpc() {
        
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                List<String> dir = currentRoom.getAllExits();
                System.out.println("DIR: " + dir);
                
                /**
                 * The npc is choosing an exit randomly
                 */
                Collections.shuffle(dir);
                

                /**
                 * going to a new room
                 */
                currentRoom = Game.allRooms.get(dir);
                
               // currentRoom = currentRoom.leaveRoom(dir.get(0));
            }
        };

        long delay = 10 * SECOND;
        long intevalPeriod = 30 * SECOND; 
        
        Timer timer = new Timer();
        
        /**
         * the task will be run everyintevalPeriod starting from delay
         */
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);

    }
	
}
