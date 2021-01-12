
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.transform.*;
import java.util.Random;


public class HouseRocketSprite extends Sprite {

    private double currentRocketAngle;

    public HouseRocketSprite(Image image, String imagepath)
    {
        super(image, imagepath);
        setPosition(100, 300);
        GetimageView().setX(100);
        GetimageView().setY(300);
    }

    public void RocketRotate(double angle,Pane root)
    {       
        Rotate rotation = new Rotate();
        rotation.setPivotX(getPositionX()+(GetImage().getWidth()/2));
        rotation.setPivotY(getPositionY()+(GetImage().getHeight()/2));
        GetimageView().getTransforms().add(rotation);//Add the Rotate to the ImageView
        rotation.setAngle(rotation.getAngle() + angle);// Rotate Angle heya heya the currentAngle before adding the angle passed in this function
                       
    }

    public double GetRandomAngle(){
        Random randomGenerator = new Random();
        //int randomAngle = randomGenerator.nextInt(90 +45) - 45; //using rhe rule random.nextInt(max - min) + min; // hena max angle is 90 and lest is -45
        int randomAngle = randomGenerator.nextInt(180 +180) -180;  
        //System.out.println("Random number is : " + randomAngle);
        return randomAngle;
    }

    public double GetCorrespondingAccuracyLevelAngle(TankSprite Tankspr,Sprite TowerDefense,double AccuracyLevel){
        
        Random randomGenerator = new Random();
        double angle=0;
        double MaximumAccuracyAngle=GetMaximumAccuracyAngle(Tankspr, TowerDefense);  
        double randomangle= randomGenerator.nextInt(180 +180) -180;//using rhe rule random.nextInt(max - min) + min; //Max is 45 and Min is -45
        angle=MaximumAccuracyAngle+(1-AccuracyLevel)*randomangle;
        
       
        return angle;
    }
    
    public double GetMaximumAccuracyAngle(TankSprite Tankspr,Sprite TowerDefense){
        
        double TankpositionX=Tankspr.getPositionX();
        double TankpositionY=Tankspr.getPositionY();

        double Radangle=0; 
        //if(currentRocketAngle<=45 && currentRocketAngle>=0)
        //    Radangle=Math.atan((TankpositionX-CurrentpositionX)/(CurrentpositionY-TankpositionY));
       // else if (currentRocketAngle>45 && currentRocketAngle<=90)
        if(TankpositionX>=getPositionX()) 
            Radangle= Math.toRadians(90)+ Math.atan((TankpositionY-getPositionY())/(TankpositionX-getPositionX()));
        else
            Radangle=- Math.toRadians(90)+ Math.atan((TankpositionY-getPositionY())/(TankpositionX-getPositionX()));
        
        double Degangle=Math.toDegrees(Radangle);
        //System.out.println("Calculated Rocket Angle is "+Degangle);
        return Degangle;



    }



    public void FireRocket(Pane root,TankSprite TankSpr,Sprite TowerDefenseSpr,double AccuracyLevel){
        
        double angle=0;
        //System.out.println("Accuracy level is "+AccuracyLevel);
        angle=GetCorrespondingAccuracyLevelAngle(TankSpr, TowerDefenseSpr,AccuracyLevel);
        //double angle=GetRandomAngle();
        //angle=90;
        //double angle=CalculateAccuracy(TankSpr,TowerDefenseSpr);
        currentRocketAngle=angle;
        RocketRotate(angle,root);

    }

    public void updateRocketPosition ( double deltaTime,Pane root)
    {
        double MoveVal=20;
        RocketRotate(-currentRocketAngle, root);//return rocket to original position to make the calculations
        //System.out.println("currentRocketAngle is  "+ currentRocketAngle);
        double currpositionX=getPositionX()+MoveVal*Math.sin(Math.toRadians(currentRocketAngle))*deltaTime;
        //System.out.println("Current PosX of rocket is "+ CurrentpositionX);
        double currpositionY=getPositionY()-MoveVal*Math.cos(Math.toRadians(currentRocketAngle))*deltaTime;
        //System.out.println("Current PosY of rocket is "+ CurrentpositionY);
        setPosition(currpositionX, currpositionY);
        RocketRotate(currentRocketAngle, root); //return rocket to rotated position
        
    }



}
