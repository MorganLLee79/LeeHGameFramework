package units;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import stages.S_Mission;

/**
 * To represent a given moving entity; hero or enemy
 * @author Harrison Lee
 */
public abstract class Unit {
	UnitView uView;
	int statusOffset;
	
	int x, y;
	int targetX;
	int targetY;
	
	int delay;	//Refers to delay until next action
	
	
	//Non-Unit-defined values
	String name;
	boolean allied;
	int moveSpeed;
	int reset;
	//Refers to value to reset delay to.
	//Essentially millisecond cooldown between actions
	
	int width, height;
	
	
	/* Subclasses must assign: name, moveSpeed, reset
	 * , width, height, allied */
	public Unit(int initX, int initY, S_Mission parent){
		//Set initial values
		targetX = initX;
		targetY = initY;
		x = initX;
		y = initY;
		delay = 0;
		
		//Set default values
		statusOffset = 15;	//Redefine pending status type
	}
	
	/**
	 * Use this to set up unit view, the default way
	 * @param newUView: The specific unit view to be used
	 * @param parent: The parent stage of the unit
	 */
	protected void setUpUnitView(Unit unit, S_Mission parent){
		uView = new UnitView(unit);
		parent.getStageView().addToLayer(uView, 1);
		uView.addMouseListener(parent);
		
		//Set location and size, accounting for status bar
		uView.setLocation(x, y-statusOffset);
		uView.setSize(width, height+statusOffset);
	}
	
		/* Unit Painting */
	/* Subtypes will define how unit is painted */
	public abstract void paintUnit(Graphics2D g);
	/**
	 * Function to paint the status bar and name tag
	 * 	for this unit.
	 * Intended for usage in paint
	 * @param g: The graphics object we are painting with
	 */
	public void paintStatusBar(Graphics2D g){
		//Draw name tag
		g.setColor(Color.DARK_GRAY);
		if(allied) {
			//Note: drawString draws from the bottom left
			g.drawString(getName(), 0, statusOffset);
		}
	}
	//TODO private abstract void paintSupplyBar 
	
	/**
	 * At every game tick, do this. This default form
	 * 	currently only steps unit towards target position
	 * 	and updates delay.
	 * @param deltaTime
	 */
	public void update(int deltaTime){
		if(delay <= 0) {
			delay = reset;
		} else {
			delay -= deltaTime;
		}
		
		if(targetX != x || targetY != y) {
			step(deltaTime);
		}
	}
	
	/**
	 * Sets the given position as the target to move to
	 * 	over time.
	 * @param x: The x pixel position
	 * @param y: The y pixel position
	 */
	public void moveTo(int x, int y){
		targetX = x;
		targetY = y;
	}
	
	/**
	 * Step along towards the current target location.
	 * Calculate distance changed based off the time given.
	 * @param deltaTime: The change in time to move in.
	 */
	public void step(int deltaTime){
		//How much can we move in given time?
		int deltaPos = (int) (moveSpeed * deltaTime * 0.01);

		//Check if we have reached/not set target
		int newX = moveAlongAxis(x, deltaPos, targetX);
		int newY = moveAlongAxis(y, deltaPos, targetY);
		
		x = newX;
		y = newY;
		uView.setLocation(newX, newY-statusOffset);
	}
	
	/**
	 * Generically move along an axis (e.g. the x or y axis)
	 * 	from the starting position, with given rate of
	 * 	change, to the target position. 
	 * Does not overshoot the target position
	 * Returns the new position on the axis.
	 * @param start: The starting position
	 * @param deltaPos: The maximum change possible (∆ position)
	 * @param target: The target position
	 * @return The new position
	 */
	private int moveAlongAxis(int start, int deltaPos,
			int target){
		//Check if we have past or before the target,
		//	accounting for deltaPos.
		if(start > target + deltaPos){
			//Move down
			return start - deltaPos;
		} else if(start < target - deltaPos){
			//Move up
			return start + deltaPos;
		} else {
			//We are close enough to the target that we can
			//	reach it now.
			return target;
		}
	}
	
	/* Simple distance check. */ 
	public int getDistance(Unit target){
		return (int) Math.sqrt((target.x-this.x)*(target.x-this.x)
				+ (target.y-this.y)*(target.y-this.y));
	}
	
	/* Simple getter functions */
	public String getName() { return name;	}
	public int getX()		{ return x;		}
	public int getY()		{ return y;		}
	public int getWidth()	{ return width;	}
	public int getHeight()	{ return height;}
	public int getStatusOffset() 	{ return statusOffset; }
	public boolean isControllable()	{ return allied; }
}

	
	
	
	
	
	
	