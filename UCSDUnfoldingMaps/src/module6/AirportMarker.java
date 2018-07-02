package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {

	public static List<SimpleLinesMarker> routes;

	public static int RECT_SIZE = 5;  // The size of the triangle marker

	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(0, 255, 0);
		pg.rect(x-0.5f * radius, y-0.5f * radius, radius, radius);
	}

	@Override
	/** Show the title of the city if this marker is selected */
	public void showTitle(PGraphics pg, float x, float y)
	{
		String name = "Name: " + getName() + " Code: " + getCode();
		String pop = "Altitude: " + getAltitude() + " meters";

		pg.pushStyle();

		pg.fill(255, 255, 255);
		pg.textSize(12);
		pg.rectMode(PConstants.CORNER);
		pg.rect(x, y- RECT_SIZE -39, Math.max(pg.textWidth(name), pg.textWidth(pop)) + 6, 39);
		pg.fill(0, 0, 0);
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.text(name, x+3, y- RECT_SIZE -33);
		pg.text(pop, x+3, y - RECT_SIZE -18);

		pg.popStyle();
	}

	private String getName()
	{
		return getStringProperty("name");
	}

    private String getCode()
    {
        return getStringProperty("code");
    }

    private String getAltitude()
	{
		return getStringProperty("altitude");
	}
	
}
