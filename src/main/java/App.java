import service.MenuService;

/**
 * @author Paweł Długołęcki
 */
public class App {
	public static void main(String[] args) {
		String filename = "cars2";
		new MenuService(filename).menu();
	}
}
