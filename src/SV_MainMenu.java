import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * StageView corresponding to the main menu
 * @author Harrison Lee
 */
public class SV_MainMenu extends StageView{
	
	//Keep track of some custom fonts
	Font titleFont;
	Font buttonFont;
	
	Rectangle selected;	//The bounds of the selected button
	
	/**
	 * Initialize all MVC-View portions of the main menu
	 * @param width: The width resolution of this stage
	 * @param height: The height resolution of this stage
	 * @param initialSelected: The bounds of the initially selected MenuButton
	 */
	public SV_MainMenu(View parentView){
		super(parentView);
		
		//Set up fonts TODO actually think about
		titleFont = new Font(Font.SERIF, Font.ITALIC, 32);
		buttonFont = new Font(Font.DIALOG, Font.BOLD, 16);
		
		//Using this to make background
		setBackground(Color.CYAN);
	}
	
	@Override
	public void paint(Graphics g){
		//Note: Components painted by super.paint(g) will be below other parts
		super.paint(g);
		
		//Create background
		//g.setColor(Color.CYAN);
		//g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.BLACK);
		
			//Draw Title
		int titleX = (int) (getWidth() * 0.3);
		int titleY = (int) (getHeight()* 0.2);
		
		g.setFont(titleFont);
		g.drawString("_ Game!", titleX, titleY);
		
			//Subtitle
		g.setFont(buttonFont);
		g.drawString("Main Menu", titleX + 100, titleY + 36);
		
			//Draw the selector
		if(selected != null) {
			//Check just in case, but normally should have an initial selected
			
			//Collect current button's location/size and modify for selector
			int x = selected.x - 2;
			int y = selected.y - 2;
			int width = selected.width + 2;
			int height = selected.height + 2;
			
			//Draw the selector around it.
			g.drawRect(x, y, width, height);
		}
		
	}
	
	/**
	 * Update where the selector should be
	 * @param currBounds: The bounds of the button selected
	 */
	public void setSelected(Rectangle currBounds) {
		selected = currBounds;
	}
}

















