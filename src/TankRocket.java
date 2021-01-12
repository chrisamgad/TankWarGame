import javafx.geometry.Rectangle2D;
import javafx.scene.image.*;
import javafx.scene.layout.Pane;
import javafx.scene.transform.*;

public class TankRocket extends Sprite {

    double CurrentRocketAngle;

    public TankRocket(Image image, String imagepath)
    {
        super(image, imagepath);
        CurrentRocketAngle=0;    
    }


    public void RotateTankRocket(double angle)
    {       
        Rotate rotation = new Rotate();
        rotation.setPivotX(getPositionX()+(GetImage().getWidth()/2));
        rotation.setPivotY(getPositionY()+(GetImage().getHeight()/2));
        GetimageView().getTransforms().add(rotation);//Add the Rotate to the ImageView
        rotation.setAngle(rotation.getAngle() + angle);// Rotate Angle heya heya the currentAngle before adding the angle passed in this function
                    
    }
    public void FireTankRocket( TankSprite tankspr){ //Same angle as the currentAngle of the tank

        RotateTankRocket(-CurrentRocketAngle); // rotate back to original angle
        CurrentRocketAngle=tankspr.GetTankCurrentAngle(); // update angle of rocket to tank angle
        //System.out.println(tankspr.GetTankCurrentAngle());
        double currPositionX=tankspr.getPositionX();// update positionX of rocket to tank positionX
        double currPositionY=tankspr.getPositionY();// update positionY of rocket to tank positionY
        setPosition(currPositionX, currPositionY);
        
        double angle=CurrentRocketAngle;
        RotateTankRocket(angle); // Rotate to New Angle      

    }


    public void updateTankRocketPosition (double deltaTime ,Pane root)
    {
        double MoveVal=4;

        double currPosX=getPositionX(),currPosY=getPositionY();
        RotateTankRocket(-CurrentRocketAngle);//return rocket to original position to make the calculations
        if(CurrentRocketAngle==0)
        {
            currPosX=getPositionX()-2*MoveVal;
            SetPositionX(currPosX);
        }
        else if(CurrentRocketAngle==45)
        {
            currPosX=getPositionX()-MoveVal;
            currPosY=getPositionY()-MoveVal;
            setPosition(currPosX, currPosY);
        }
        else if(CurrentRocketAngle==90)
        {       
            currPosY=getPositionY()-2*MoveVal;
            SetPositionY(currPosY);
        }
        else if(CurrentRocketAngle==135)
        {       
            currPosX=getPositionX()+MoveVal;
            currPosY=getPositionY()-MoveVal;
            setPosition(currPosX, currPosY);
        }
        else if(CurrentRocketAngle==180)
        {       
            currPosX=getPositionX()+2*MoveVal;
            SetPositionX(currPosX);
        }
        else if(CurrentRocketAngle==-45)
        {
            currPosX=getPositionX()-MoveVal;
            currPosY=getPositionY()+MoveVal;
            setPosition(currPosX, currPosY);
        }
        else if(CurrentRocketAngle==-90)
        {       
            currPosY=getPositionY()+2*MoveVal;
            SetPositionY(currPosY);
        }
        else if(CurrentRocketAngle==-135)
        {       
            currPosX=getPositionX()+MoveVal;
            currPosY=getPositionY()+MoveVal;
            setPosition(currPosX, currPosY);
        }
        else if(CurrentRocketAngle==-180)
        {       
            currPosX=getPositionX()+2*MoveVal;
            SetPositionX(currPosX);
        }
        RotateTankRocket(CurrentRocketAngle);
        //System.out.println("Current PositionX of Tank Rocket is "+ CurrentpositionX);
        //System.out.println("Current PositionY of Tank Rocket is "+ CurrentpositionY);
        
    }

    public Rectangle2D getBoundaryHouse(Sprite spr) { //Boundary of House
        return new Rectangle2D(spr.getPositionX()+62.5, spr.getPositionY()+60, spr.GetWidth()-60, spr.getHeight()-60);
    }

    public Rectangle2D getBoundaryMountain(Sprite spr){
        return new Rectangle2D(spr.getPositionX()+65, spr.getPositionY()+60, spr.GetWidth()-60, spr.getHeight()-55);
    }

    @Override
    public Rectangle2D getBoundary() { //Boundary of Tank Rocket
        return new Rectangle2D(getPositionX()+GetWidth()/2, getPositionY()+getHeight()/2, GetWidth()-3, getHeight()-3);
    }
    
    public boolean intersectsHouse(Sprite spr) {
        return this.getBoundary().intersects(getBoundaryHouse(spr));
    }

    public boolean intersectsMountain(Sprite spr) {
        return this.getBoundary().intersects(getBoundaryMountain(spr));
    }

}
