import javafx.application.Application;
import javafx.stage.Stage;
import javafx.application.Platform;
import java.util.Timer;
import java.util.TimerTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AnimationGUI extends Application {
	
	private static int picture = 0;
	public static AnimationTask task;
	private static Timer time;
	Label image;

    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage primaryStage) {
		image = new Label();
		time = new Timer();
		task = new AnimationTask();
		VBox main = new VBox();
    	Scene scene = new Scene(main, 400, 200);
    	Slider slider = new Slider(1, 999, 0);
    	Label label = new Label("Please drag the slider:");
    	
    	slider.setShowTickMarks(true);
    	slider.setShowTickLabels(true);
    	slider.setMajorTickUnit(250);
    	slider.setValue(501);
    	slider.setPrefWidth(400);
    	time.scheduleAtFixedRate(task, 0, 500);
    	
    	slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
            	 task.cancel();
                 task = new AnimationTask();
                 time.scheduleAtFixedRate(task, 0, 1000 - newValue.intValue());
            }
        });
    	
    	main.getChildren().add(label);
    	main.getChildren().add(slider);
    	main.getChildren().add(image);
    	
    	primaryStage.setTitle("Animation GUI");
    	primaryStage.setScene(scene);
    	primaryStage.show();	
    }

    private class AnimationTask extends TimerTask {
    	
        public void run() {
            Platform.runLater(() -> {
    			image.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(picture + ".png"))));

        		if (picture < 13) {
      				picture++;
       			} else {
       				picture = 0;
       			}
     		});   
        }
    }
}
