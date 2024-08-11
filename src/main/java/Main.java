import Controller.SocialMediaController;
import io.javalin.Javalin;

/**
 * This class allows manual running and testing of the application.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);
    }
}
