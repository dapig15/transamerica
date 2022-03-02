import java.awt.Color;
public class City extends Node {
	public static final String[] cityNames = new String[] {
			"Seattle", null, null, null, null, null, null, null, null, null,
            "Portland", null, null, "Helena", null, null, null, "Bismarck", null, null, "Duluth", null, null, null,
            null, null, null, null, null, null, null, null, null, null, "Minneapolis", null, null, "Buffalo", null, "Boston",
            "Medford", null, null, null, null, null, null, null, null, null, null, null, "Chicago", null, null, null, null,
            null, null, null, "Salt Lake City", null, null, null, null, "Omaha", null, null, null, null, null, null, null, "New York",
            "Sacremento", null, null, null, null, "Denver", null, null, null, null, null, null, null, "Cincinnati", null, "Washington",
            "San Francisco", null, null, null, null, null, null, null, null, "Kansas City", null, "St. Louis", null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "Richmond",
            null, null, null, null, "Santa Fe", null, null, "Oklahoma City", null, null, null, null, null, "Winston", null, null,
            "Los Angeles", null, "Phoenix", null, null, null, null, null, null, null, "Memphis", null, null, null, null,
            "San Diego", null, null, null, null, null, null, "Dallas", null, null, null, "Atlanta", null, "Charleston",
            null, null, "El Paso", null, null, null, null, null, null, null, null, null,
            null, null, null, "Houston", null, "New Orleans", null, null, "Jacksonville"
    };
	public static final Color[] cityColors = new Color[] {
			Color.green, null, null, null, null, null, null, null, null, null,
			Color.green, null, null, Color.blue, null, null, null, Color.blue, null, null, Color.blue, null, null, null,
            null, null, null, null, null, null, null, null, null, null, Color.blue, null, null, Color.blue, null, Color.orange,
            Color.green, null, null, null, null, null, null, null, null, null, null, null, Color.blue, null, null, null, null,
            null, null, null, Color.yellow, null, null, null, null, Color.yellow, null, null, null, null, null, null, null, Color.orange,
            Color.green, null, null, null, null, Color.yellow, null, null, null, null, null, null, null, Color.blue, null, Color.orange,
            Color.green, null, null, null, null, null, null, null, null, Color.yellow, null, Color.yellow, null, null, null, null,
            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, Color.orange,
            null, null, null, null, Color.yellow, null, null, Color.yellow, null, null, null, null, null, Color.orange, null, null,
            Color.green, null, Color.red, null, null, null, null, null, null, null, Color.red, null, null, null, null,
            Color.green, null, null, null, null, null, null, Color.red, null, null, null, Color.red, null, Color.orange,
            null, null, Color.red, null, null, null, null, null, null, null, null, null,
            null, null, null, Color.red, null, Color.red, null, null, Color.orange
	};
	String name;
	Color color;
	public City(int x, int y, int id, String name, Color color) {
		super(x, y, id);
		this.name = name;
		this.color = color;
	}
	public String getName() {
		return name;
	}
	public Color getColor() {
		return color;
	}
}