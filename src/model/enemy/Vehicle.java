package model.enemy;

import model.map.Box;


public interface Vehicle extends Box{ 

    /**
     * Call the methods sets the position and direction of the car.
     * @param stripYLoc contains the line where the car will be printed
     * @return get the car
     */
	Vehicle setCar(double stripYLoc);
	
    /**
     * Call the methods for sets the position and direction of the train.
     * @param stripYLoc contains the line where the train will be printed
     * @return get the train
     */
	Vehicle setTrain(double stripYLoc);
	
    /**
     * Sets the position and direction of the vehicles.
     * @param vehicle vehicle whose direction we want to set randomly.
     * @param speed of the vehicle.
     * @param imgR right sprite of the vehicle.
     * @param imgL left sprite of the vehicle.
     */
	void setRndDir(int speed, String imgR, String imgL);

    /**
     * return the vehicle 
     * @return get the vehicle
     */
	Vehicle getVehicle();


}
