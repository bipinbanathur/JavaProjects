import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
 
/**
 * A JavaFX Hello World
 */
public class HelloWorld extends Application 
{
 
   /**
    * @param args the command line arguments
    */
    public static void main(String[] args) 
	{
       Application.launch(args);
    }
 
    @Override
    public void start(Stage stage) 
	{
       stage.setTitle("Hello World");
	   
       Group root = new Group();
       Scene scene = new Scene(root, 300, 250);
       Button btn = new Button();
       btn.setLayoutX(100);
       btn.setLayoutY(80);
       btn.setText("Hello World");
	   
	   Button btn1 = new Button();
       btn1.setLayoutX(200);
       btn1.setLayoutY(80);
       btn1.setText("World Hello");
       btn1.setOnAction( 
	   actionEvent -> 
	   
	   System.out.println("Hello World"));
       root.getChildren().add(btn);
	   root.getChildren().add(btn1);
       stage.setScene(scene);
       stage.show();
    }
}