import javafx.util.Duration;
import java.io.File; // Import the File class
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.ArrayList;


import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class App extends Application {

    Scene scene1,scene2,scene3;
    boolean TankCanFire=true;
    boolean HouseRocketHitsTank=false;
    boolean HouseFirstHit=false,HouseSecondHit=false,HouseThirdHit=false;
    boolean TankFirstHit=false,TankSecondHit=false,TankThirdHit=false;
    int NumberOfMines=1;
    double AccuracyLevel=0;
    double MisslesRateAggressivenessLevel= 0.5;
    boolean StartHouseRocketsLaunch=false;
    int House_Rocket_Hit_Tank_counter=0;
    int Tank_Rocket_Hit_House_counter=0;
    int Total_Tank_Rockets_Fired=0; 
    int Total_House_Rockets_Fired=0; // equals the RocketCounter
    int NTank_Rockets_Missed=0;
    int NHouse_Rockets_Missed=0;
    int RocketCounter=0;
    int HouseRocketsListSize;
    int CurrentHouseRocketNumber=0;
    boolean SameTankRocket=false;
    ArrayList<MineSprite> MinesList;
    boolean IntersectedAMine=false;
    Timeline HouseRocketScheduler,TankRocketTimeline,TankRocketCheckIntersectionTimeline,HouseRocketCheckIntersectionTimeline
    ,HouseRocketPositionUpdaterTimeline;
    
    enum GameState {LOSTdueMine,LOSTdueHouse,WON};
    
    static GameState gameState;

    //function to log the Game stats to text file after end of game
    public static void LogToTextFile(int Total_Tank_Rockets_Fired,int Total_House_Rockets_Fired,int House_Rocket_Hit_Tank_counter, int Tank_Rocket_Hit_House_counter)
    {

        try {  
            FileWriter myWriter = new FileWriter("highScores.txt");
            if(gameState==GameState.WON)
                {
                    myWriter.write("You Have Won...Congratulations, you have destroyed the house!");
                }
            else       
                
                {
                    if(gameState==GameState.LOSTdueHouse)
                    {
                        myWriter.write("Unfortuanetly, you Have Lost because the House destroyed you :(");
                    }
                    else if(gameState==GameState.LOSTdueMine)
                    {
                        myWriter.write("Unfortuanetly, you Have Lost because you have hit a mine :(");
                    }
                }

            myWriter.write("\nHere are the GameStats");
            myWriter.write("\n\n######################");
            myWriter.write("\nStats for the Tank\n");
            myWriter.write("\nYou have fired a total of "+Total_Tank_Rockets_Fired+" Rockets from your Tank");
            myWriter.write("\nYou have Hit the House "+Tank_Rocket_Hit_House_counter+" times");
            myWriter.write("\nYou have have missed the House "+(Total_Tank_Rockets_Fired-Tank_Rocket_Hit_House_counter)+" times");
            myWriter.write("\n\n######################");
            myWriter.write("\nStats for the House\n");
            myWriter.write("\nThe House has fired a total of "+Total_House_Rockets_Fired+" Missles");
            myWriter.write("\nThe House has hit you "+House_Rocket_Hit_Tank_counter+" times");
            myWriter.write("\nThe House has missed you "+(Total_House_Rockets_Fired-House_Rocket_Hit_Tank_counter)+" times");
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred due to an exception(check if file in correct directory).");
            e.printStackTrace();
          } 
    }

    //function to print message to user after end of game
    public static void PrintWinningORLosingMsg(VBox vbox2)
    {
        Label MessageToUser=new Label();
        if(gameState==GameState.WON) 
            MessageToUser=new Label("You Have Won...Congratulations you destroyed \n the house!");
        else if(gameState==GameState.LOSTdueHouse)
            {
                MessageToUser=new Label("You Have Lost...because the House destroyed you :(");
            }
        else if(gameState==GameState.LOSTdueMine)
            {
                MessageToUser=new Label("You Have Lost...because you have hit a mine :(");
            }
        
        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 20); 
        MessageToUser.setFont(font);
        MessageToUser.setTextFill(Color.web("#794024"));
        vbox2.getChildren().addAll(MessageToUser);
        
    }

    //function to stop all timers at end of game to prevent any Game Stats counters of getting modiefed
    public static void StopAllTimeLines(Timeline HouseRocketScheduler, Timeline TankRocketTimeline,Timeline TankRocketCheckIntersectionTimeline,Timeline HouseRocketCheckIntersectionTimeline
    , Timeline HouseRocketPositionUpdaterTimeline)
    {   
        HouseRocketScheduler.stop();
        TankRocketTimeline.stop();
        TankRocketCheckIntersectionTimeline.stop();
        HouseRocketCheckIntersectionTimeline.stop();
        HouseRocketPositionUpdaterTimeline.stop();

    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Tank Game"); // Title of window

        Pane root= new Pane();
        root.setMaxSize(800, 600); 
 
        Canvas canvas = new Canvas(800,600); //draw canvas
        root.getChildren().add(canvas); // add canvas
        
        //inserting Tank sprite
        Image image1 = new Image("/images/NewTank_resized.png");
        TankSprite Tank= new TankSprite(image1,"/images/NewTank_resized.png"); 
        Tank.setPosition(500, 500);//sets position of tank sprite
    
        //inserting Tower Defense
        Image image2 = new Image("/images/TowerFortress.png");
        Sprite TowerDefense= new Sprite(image2,"/images/TowerFortress.png"); 
        TowerDefense.setPosition(100, 300);//sets size of Tower defense sprite
        
        //Inserting Mountain
        Image image3 = new Image("/images/MountainNew.png");
        
        Sprite Mountain1= new Sprite(image3,"/images/MountainNew.png"); 
        Mountain1.setPosition(430, 220);//sets size of Mountain 
        
        Sprite Mountain2= new Sprite(image3,"/images/MountainNew.png"); 
        Mountain2.setPosition(350, 450);//sets size of Mountain sprite


         //Inserting House rocket
         Image image4 = new Image("/images/HouseRocket2_resized.png");

         //Creating an arraylist that stores HouseRocketsList
         ArrayList<HouseRocketSprite> HouseRocketsList = new ArrayList<HouseRocketSprite>();
 

        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 12); 

        //Layout1
        // create label 
        Label label = new Label("Choose how many mines do you want in the game");
        label.setFont(font);
        label.setTextFill(Color.web("#794024"));

        Label label2 = new Label("After how many seconds do you want the house to launch a single rocket?\nNote: 0.5sec is the fastest rate and 4sec is slowest rate of launching missles"); 
        label2.setFont(font);
        label2.setTextFill(Color.web("#794024"));


        Label label3 = new Label("Choose the accuracy of the House Missles from 0 (Random Accuracy) up to 1 (Greatest Accuracy)"); 
        label3.setFont(font);
        label3.setTextFill(Color.web("#794024"));
        
        // create slider 
        Slider slider1 = new Slider();
        Slider slider2 = new Slider();
        Slider slider3 = new Slider();

        Button button1= new Button("Let's Play! ");
        button1.setOnAction(
            Event-> 
            {
                
                Image image7 = new Image("/images/DangerTank_Resized.png");
                MinesList = new ArrayList<MineSprite>();
                //NumberOfMines=1;
                for(int i=0;i<NumberOfMines;i++)
                {
                    MinesList.add(new MineSprite(image7,"/images/DangerTank_Resized.png",TowerDefense,Mountain1,Mountain2,Tank));
                    MinesList.get(i).CheckIfIntersectAnotherMine(i,MinesList);
                    root.getChildren().add(MinesList.get(i).GetimageView());
                }

                //Timline used to Fire rocket every MisslesRateAggressivenessLevel seconds
                HouseRocketScheduler = new Timeline(new KeyFrame(Duration.seconds(MisslesRateAggressivenessLevel), ev -> {
 
                        
                
                        HouseRocketsList.add(new HouseRocketSprite(image4,"/images/HouseRocket2_resized.png"));
                        HouseRocketsList.get(RocketCounter).FireRocket(root,Tank,TowerDefense,AccuracyLevel);
                        root.getChildren().add(HouseRocketsList.get(RocketCounter).GetimageView());
                        RocketCounter++;
                        Total_House_Rockets_Fired=RocketCounter;
                    
                }));
                
                HouseRocketScheduler.setCycleCount(Animation.INDEFINITE);
                HouseRocketScheduler.play();
                stage.setScene(scene2);// go to the game screen
            }
        );


		// max and value 
		slider1.setMin(1); 
		slider1.setMax(4); 
		slider1.setValue(1); 
        slider1.setBlockIncrement(1);
        slider1.setMajorTickUnit(1);
        slider1.setMinorTickCount(0);
        slider1.setShowTickLabels(true);
        slider1.setSnapToTicks(true);
            
		// Adding Listener to value property. 
		slider1.valueProperty().addListener( 
			new ChangeListener<Number>() { 

			public void changed(ObservableValue <? extends Number > 
					observable, Number oldValue, Number newValue) 
			{ 

                NumberOfMines=newValue.intValue();
			} 
        }); 
        
        // max and value 
		slider2.setMin(0.5); 
		slider2.setMax(4); 
		slider2.setValue(0.5); 
        slider2.setMajorTickUnit(1);
        slider2.setShowTickLabels(true);
            
		// Adding Listener to value property. 
		slider2.valueProperty().addListener( 
			new ChangeListener<Number>() { 

			public void changed(ObservableValue <? extends Number > 
					observable, Number oldValue, Number newValue) 
			{ 
 
                MisslesRateAggressivenessLevel=  newValue.doubleValue();
               
                
			} 
        }); 
        // max and value 
		slider3.setMin(0); 
		slider3.setMax(1); 
		slider3.setValue(0); 
        slider3.setMajorTickUnit(1);
        slider3.setMinorTickCount(0);
        slider3.setShowTickLabels(true);


		// Adding Listener to value property. 
		slider3.valueProperty().addListener( 
			new ChangeListener<Number>() { 

			public void changed(ObservableValue <? extends Number > 
					observable, Number oldValue, Number newValue) 
			{ 
                AccuracyLevel=newValue.doubleValue();
                 
                
                
			} 
        }); 
        // create a VBox 
		VBox vbox1 = new VBox(); 

        Insets insets= new Insets(100,30,20,100);
		vbox1.setPadding(insets); 
		vbox1.setSpacing(10); 
		
        Scene scene1 = new Scene(vbox1, 800, 500);

        vbox1.setId("WelcomeScreen");
        scene1.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());// includes style.css in the java file
        vbox1.getChildren().addAll(label, slider1,label2,slider2,label3,slider3,button1); 
        //Layout 2        
        scene2 = new Scene(root); // puts root in scene
        
        root.setId("pane"); // identifier in css file that inserts background
        scene2.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());// includes style.css in the java file
        
        //Layout 3
        VBox vbox2 = new VBox();
        scene3=new Scene(vbox2,800,600);
        Insets insets2= new Insets(250,0,20,130);
		vbox2.setPadding(insets2); 
        vbox2.setSpacing(0); 
        
        vbox2.setId("GameOverScreen"); // identifier in css file that inserts background
        scene3.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());// includes style.css in the java file
       
       
    
        Image image6 = new Image("/images/RocketTank.png");
        TankRocket TankRocket= new TankRocket(image6,"/images/RocketTank.png"); 
        
        scene2.setOnKeyPressed( // Handling KeyBoard Events
            (event) ->{
                KeyCode keyCode = event.getCode();
                if (keyCode.equals(KeyCode.UP)  && TankCanFire==true) {
                    //System.out.println("Up is clicked");
                    Tank.MoveTank(0,TowerDefense,Mountain1,Mountain2); //number 0 for up arrow
                    IntersectedAMine = Tank.CheckIntersectionTankWithMines(MinesList);
                    if(IntersectedAMine)
                   {
                        //System.out.println("GAME LOST BECAUSE TANK HITS A  MINE");
                        gameState=GameState.LOSTdueMine;
                        StopAllTimeLines(HouseRocketScheduler,TankRocketTimeline,TankRocketCheckIntersectionTimeline,HouseRocketCheckIntersectionTimeline
                        ,HouseRocketPositionUpdaterTimeline);
                        LogToTextFile(Total_Tank_Rockets_Fired,Total_House_Rockets_Fired,House_Rocket_Hit_Tank_counter,Tank_Rocket_Hit_House_counter);
                        stage.setScene(scene3);// go to the game screen
                        PrintWinningORLosingMsg(vbox2);
                    }

                    
                
                }
                if (keyCode.equals(KeyCode.DOWN)  && TankCanFire==true) {
                    //System.out.println("Down is clicked");
                    Tank.MoveTank(1,TowerDefense,Mountain1,Mountain2); //number 1 for down arrow
                    IntersectedAMine = Tank.CheckIntersectionTankWithMines(MinesList);
                    
                    if(IntersectedAMine)
                    {
                         //System.out.println("GAME LOST BECAUSE TANK HITS A  MINE");
                         gameState=GameState.LOSTdueMine;
                         StopAllTimeLines(HouseRocketScheduler,TankRocketTimeline,TankRocketCheckIntersectionTimeline,HouseRocketCheckIntersectionTimeline
                         ,HouseRocketPositionUpdaterTimeline);
                         LogToTextFile(Total_Tank_Rockets_Fired,Total_House_Rockets_Fired,House_Rocket_Hit_Tank_counter,Tank_Rocket_Hit_House_counter);
                         stage.setScene(scene3);// go to the game screen
                         PrintWinningORLosingMsg(vbox2);
                     }


                }
                if (keyCode.equals(KeyCode.RIGHT)  && TankCanFire==true) {//If Key is clicked and tank rocket havent hit mountain nor house nor left screen
                    //System.out.println("Right is clicked");
                    Tank.RotateTank(45);

                    
                }
                if (keyCode.equals(KeyCode.LEFT)  && TankCanFire==true) {//If Key is clicked and tank rocket havent hit mountain nor house nor left screen
                    //System.out.println("Left is clicked");
                    Tank.RotateTank(-45);
                }
                

                if (keyCode.equals(KeyCode.SPACE) && TankCanFire==true) { //If Key is clicked and tank rocket havent hit mountain nor house nor left screen
                    TankRocket.SetimageView( new Image("/images/RocketTank.png")); // updates image of Rocket Tank after hitting house
                    //System.out.println("Tank Fires Rocket");
                    TankRocket.FireTankRocket(Tank);
                    TankCanFire=false; //Reset Time for Re-generating Rocket 
                    Total_Tank_Rockets_Fired++;
                    
                                 
                }
                 
            }
        );

        root.getChildren().addAll(Tank.GetimageView(),TowerDefense.GetimageView(),TankRocket.GetimageView(),Mountain1.GetimageView(),Mountain2.GetimageView());   


            //TimeLine used to update the Rocket fired from tank every 0.01 seconds
            TankRocketTimeline = new Timeline(new KeyFrame(Duration.seconds(0.01), ev -> {
                TankRocket.updateTankRocketPosition(1/12.0,root);               

                if(HouseFirstHit==true)
                    Tank_Rocket_Hit_House_counter=1;
                if(HouseSecondHit==true)
                    Tank_Rocket_Hit_House_counter=2;
                if(HouseThirdHit==true)
                    Tank_Rocket_Hit_House_counter=3;
                //if out of window screen, Tank is able to move and fire again
                if(TankRocket.getPositionX()<-175 || TankRocket.getPositionY() <-100 || TankRocket.getPositionX()>975 ||TankRocket.getPositionY()>700) // Add the intersection conditon, lma haroon ye5alasha 
                {
                    TankCanFire=true;
                }
                else if (TankRocket.intersectsMountain(Mountain1) || TankRocket.intersectsMountain(Mountain2))
                {
                    TankRocket.SetimageView(null);
                    TankCanFire=true;
                }
            
                
                                    
            }));
            TankRocketTimeline.setCycleCount(Animation.INDEFINITE);
            TankRocketTimeline.play();


            TankRocketCheckIntersectionTimeline = new Timeline(new KeyFrame(Duration.seconds(0.08), ev -> {
            
                if(TankRocket.intersectsHouse(TowerDefense))
                {
                    TankRocket.SetimageView(null);
                    
                    if(Tank_Rocket_Hit_House_counter==0 && SameTankRocket==false )
                    {
                        //System.out.println("testsingfsf");
                        Total_Tank_Rockets_Fired++;
                        Tank_Rocket_Hit_House_counter=1;
                        HouseFirstHit=true;
                        SameTankRocket=true;   
                        TowerDefense.SetimageView(new Image("/images/HouseExploded1.png"));                    
                    }

                    else if(Tank_Rocket_Hit_House_counter==1 && SameTankRocket==false )
                    {
                        Total_Tank_Rockets_Fired++;
                        Tank_Rocket_Hit_House_counter=2;
                        HouseSecondHit=true;
                        SameTankRocket=true;
                        TowerDefense.SetimageView(new Image("/images/HouseExploded2.png")); 
                        
                    }

                    else if(Tank_Rocket_Hit_House_counter==2 && SameTankRocket==false ) //Won Game
                    {
                        Total_Tank_Rockets_Fired++;
                        Tank_Rocket_Hit_House_counter=3;
                        HouseThirdHit=true;
                        SameTankRocket=true;
                        gameState=GameState.WON; 
                        //System.out.println("GAME WON BECAUSE House_Rocket_Hit_Tank_counter reached  "+House_Rocket_Hit_Tank_counter);
                        StopAllTimeLines(HouseRocketScheduler,TankRocketTimeline,TankRocketCheckIntersectionTimeline,HouseRocketCheckIntersectionTimeline
                        ,HouseRocketPositionUpdaterTimeline);
                        LogToTextFile(Total_Tank_Rockets_Fired,Total_House_Rockets_Fired,House_Rocket_Hit_Tank_counter,Tank_Rocket_Hit_House_counter);
                        stage.setScene(scene3);
                        PrintWinningORLosingMsg(vbox2);
                    }


                    PauseTransition pause = new PauseTransition(Duration.seconds(0.08)); // wait for an 0.08 second to prevent having intersection more than 1 time on same rocket
                    pause.setOnFinished(event ->                                    //then toggle SameTankRocket variable back to false;
                        {
                        //System.out.println("Finished: 0.08 second elapsed");
                        SameTankRocket=false;
                        
                        }
                    );
                    pause.play();
                          
                }

               
            }));
            TankRocketCheckIntersectionTimeline.setCycleCount(Animation.INDEFINITE);
            TankRocketCheckIntersectionTimeline.play();

            HouseRocketCheckIntersectionTimeline = new Timeline(new KeyFrame(Duration.seconds(0.1), ev -> {
                
                for( int i=0;i<HouseRocketsList.size();i++)
                            {                        
                                if(Tank.intersectsHouseRocket(HouseRocketsList.get(i)) ) //  add && TankFirstHit==false
                                    {

                                    //System.out.println("INTERSECTION ROCKET HAPPENED of the rocket i =  "+i);
                                    HouseRocketsList.get(i).SetimageView(null);
                                    if(House_Rocket_Hit_Tank_counter==0 )
                                        {
                                            House_Rocket_Hit_Tank_counter++;
                                            TankFirstHit=true;
                                            CurrentHouseRocketNumber=i;
                                            Tank.SetimageView(new Image("/images/TankExploded1.png"));

                                        }
                                    if(House_Rocket_Hit_Tank_counter==1 && i!=CurrentHouseRocketNumber)
                                        {
                                        House_Rocket_Hit_Tank_counter=2;
                                        TankSecondHit=true;
                                        CurrentHouseRocketNumber=i;
                                        Tank.SetimageView(new Image("/images/TankExploded2.png"));
                                        
                                        }
                                    if(House_Rocket_Hit_Tank_counter==2 && i!=CurrentHouseRocketNumber ) //Lost Game
                                        {
                                        House_Rocket_Hit_Tank_counter=3;
                                        TankThirdHit=true;
                                        CurrentHouseRocketNumber=i;
                                        gameState=GameState.LOSTdueHouse; 
                                        //System.out.println("GAME LOST BECAUSE House_Rocket_Hit_Tank_counter reached  "+House_Rocket_Hit_Tank_counter);
                                        StopAllTimeLines(HouseRocketScheduler,TankRocketTimeline,TankRocketCheckIntersectionTimeline,HouseRocketCheckIntersectionTimeline
                                        ,HouseRocketPositionUpdaterTimeline);
                                        LogToTextFile(Total_Tank_Rockets_Fired,Total_House_Rockets_Fired,House_Rocket_Hit_Tank_counter,Tank_Rocket_Hit_House_counter);
                                        stage.setScene(scene3);
                                        PrintWinningORLosingMsg(vbox2);
                                        }                                  
                                    
                                    }
                                
                            }    
                                    
            }));
            HouseRocketCheckIntersectionTimeline.setCycleCount(Animation.INDEFINITE);
            HouseRocketCheckIntersectionTimeline.play();

            HouseRocketPositionUpdaterTimeline = new Timeline(new KeyFrame(Duration.seconds(0.01), ev -> {
                
                for( int i=0;i<HouseRocketsList.size();i++)
                    {
                        HouseRocketsList.get(i).updateRocketPosition(1/12.0,root);

                    }
                                    
            }));
            HouseRocketPositionUpdaterTimeline.setCycleCount(Animation.INDEFINITE);
            HouseRocketPositionUpdaterTimeline.play();
        stage.setScene(scene1);//sets scene
        stage.setResizable(false);//disables maximize window
        stage.show(); //show window
        
        
    }

    public static void main(String[] args) {
        launch(args);
        
    }
}




