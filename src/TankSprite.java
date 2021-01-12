import java.util.ArrayList;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.*;
import javafx.scene.transform.*;

public class TankSprite extends Sprite {


    private double currentAngle;


    public TankSprite(Image image,String imagepath) {
        super(image,imagepath); // calls constructor
              
    }

    public void RotateTank(double angle)
    {
        currentAngle=currentAngle+angle;
        if(currentAngle>180) // if the current angle exceeds 180, this means angle is 235 , which is the same as -135
            currentAngle=-135; 
        else if (currentAngle<-180)
            currentAngle=135;
        //System.out.println("Currentangle is "+ currentAngle);
        Rotate rotation = new Rotate();
        rotation.setPivotX(getPositionX()+(GetImage().getWidth()/2));
        rotation.setPivotY(getPositionY()+(GetImage().getHeight()/2));
        GetimageView().getTransforms().add(rotation);//Add the Rotate to the ImageView
        
        
        rotation.setAngle(rotation.getAngle() + angle);// Rotate Angle heya heya the currentAngle before adding the angle passed in this function

        

    }


    public void MoveTank(int Key,Sprite HouseSpr,Sprite Mountain1Spr,Sprite Mountain2Spr)
    {   
        //For debugging purposes
        /*System.out.println("Angle is "+currentAngle);
        System.out.println("CurrentPositionY is "+getPositionY());
        System.out.println("CurrentPositionX is "+getPositionX());
        */
        double OldTankPositionX=getPositionX();
        double OldTankPositionY=getPositionY();
        double newTankPosX=getPositionX(),newTankPosY=getPositionY();
        if(Key==0) //if up arrow
            {
                if(currentAngle==0)
                {   
                    newTankPosX=getPositionX()-15;
                    SetPositionX(newTankPosX);
                                               
                }
                else if (currentAngle==45)
                    {   
                        RotateTank(-45); //Rotate back to original position to calculate set position into correct 
                        newTankPosX=getPositionX()-15;
                        SetPositionX(newTankPosX);
                        newTankPosY=getPositionY()-15;
                        SetPositionY(newTankPosY);
                        RotateTank(45); //After Calculateing Correct position, retun back to the rotated angle
       
                    }
                else if(currentAngle==90)
                {
                    {   RotateTank( -90); //Rotate back to original position to calculate set position into correct
                        newTankPosY=getPositionY()-15;
                        SetPositionY(newTankPosY);
                        RotateTank( 90); //After Calculateing Correct position, retun back to the rotated angle               
                    }
                }
                else if(currentAngle==135)
                {
                    {  
                        RotateTank( -135); //Rotate back to original position to calculate set position into correct
                        newTankPosX=getPositionX()+15;
                        newTankPosY=getPositionY()-15;
                        setPosition(newTankPosX, newTankPosY);
                        RotateTank( 135); //After Calculateing Correct position, retun back to the rotated angle 
                    }
                }
                else if(currentAngle==180)
                {
                    {   RotateTank( -180); //Rotate back to original position to calculate set position into correct
                        newTankPosX=getPositionX()+15;
                        SetPositionX(newTankPosX);
                        RotateTank( 180); //After Calculateing Correct position, retun back to the rotated angle 
                    }
                }
                else if (currentAngle==-45)
                {
                    RotateTank( 45); //Rotate back to original position to calculate set position into correct
                    newTankPosX=getPositionX()-15;
                    newTankPosY=getPositionY()+15;
                    SetPositionX(newTankPosX);
                    SetPositionY(newTankPosY);
                    RotateTank( -45); //After Calculateing Correct position, retun back to the rotated angle 
                    
                }
                else if(currentAngle==-90)
                {
                    { 
                        RotateTank( 90); //Rotate back to original position to calculate set position into correct
                        newTankPosY=getPositionY()+15;
                        SetPositionY(newTankPosY);
                        RotateTank( -90); //After Calculateing Correct position, retun back to the rotated angle 

                    }
                }
                else if(currentAngle==-135)
                {
                    {   
                        RotateTank( 135); //Rotate back to original position to calculate set position into correct
                        newTankPosX=getPositionX()+15;
                        newTankPosY=getPositionY()+15;
                        setPosition(newTankPosX,newTankPosY);
                        RotateTank( -135); //After Calculateing Correct position, retun back to the rotated angle 
    
                    }
                }
                else if(currentAngle==-180)
                {
                    { 
                        RotateTank( 180); //Rotate back to original position to calculate set position into correct
                        newTankPosX=getPositionX()+15;
                        SetPositionX(newTankPosX);
                        RotateTank( -180); //After Calculateing Correct position, retun back to the rotated angle 
                    }
                }
                    
            }
            
        else  if (Key==1) // down
        {
            if(currentAngle==0)
            {   
                
                newTankPosX=getPositionX()+15;
                SetPositionX(newTankPosX);

            }
            else if (currentAngle==45)
                {
                    RotateTank( -45); //Rotate back to original position to calculate set position into correct
                    newTankPosX=getPositionX()+15;
                    newTankPosY=getPositionY()+15;
                    setPosition(newTankPosX, newTankPosY);
                    RotateTank( 45); //After Calculateing Correct position, retun back to the rotated angle
     
                }
            else if(currentAngle==90)
            {
                {   
                    RotateTank( -90); //Rotate back to original position to calculate set position into correct
                    newTankPosY=getPositionY()+15;
                    SetPositionY(newTankPosY);
                    RotateTank( 90); //After Calculateing Correct position, retun back to the rotated angle

                }
            }
            else if(currentAngle==135)
            {
                {   
                    RotateTank( -135); //Rotate back to original position to calculate set position into correct
                    newTankPosX=getPositionX()-15;
                    newTankPosY=getPositionY()+15;
                    setPosition(newTankPosX, newTankPosY);
                    RotateTank( 135); //After Calculateing Correct position, retun back to the rotated angle
                }
            }
            else if(currentAngle==180)
            {
                {  
                    RotateTank( -180); //Rotate back to original position to calculate set position into correct
                    newTankPosX=getPositionX()-15;    
                    SetPositionX(newTankPosX);     
                    RotateTank( 180); //After Calculateing Correct position, retun back to the rotated angle
                }
            }
            else if (currentAngle==-45)
            {
                
                RotateTank( 45); //Rotate back to original position to calculate set position into correct
                newTankPosX=getPositionX()+15;
                newTankPosY=getPositionY()-15;
                setPosition(newTankPosX, newTankPosY);
                RotateTank( -45); //After Calculateing Correct position, retun back to the rotated angle
            
            }
            else if(currentAngle==-90)
            {
                {   
                    RotateTank(90); //Rotate back to original position to calculate set position into correct
                    newTankPosY=getPositionY()-15;
                    SetPositionY(newTankPosY);
                    RotateTank(-90); //After Calculateing Correct position, retun back to the rotated angle
    
                }
            }
            else if(currentAngle==-135)
            {
                {   
                    RotateTank( 135); //Rotate back to original position to calculate set position into correct
                    newTankPosX=getPositionX()-15;
                    newTankPosY=getPositionY()-15;
                    setPosition(newTankPosX, newTankPosY);
                    RotateTank( -135); //After Calculateing Correct position, retun back to the rotated angle
                
                }
            }
            else if(currentAngle==-180)
            {
                { 
                     RotateTank( 180); //Rotate back to original position to calculate set position into correct
                    newTankPosX=getPositionX()-15;
                    SetPositionX(newTankPosX);
                    RotateTank( -180); //After Calculateing Correct position, retun back to the rotated angle

                }
            }
        }
        //System.out.println(" New CurrentPositionY is "+getPositionY());
        //System.out.println(" New CurrentPositionX is "+getPositionX());
        
        setPosition(newTankPosX, newTankPosY);


        if(intersectsHouse(HouseSpr))
            {
                double OldAngle=currentAngle;
                //System.out.println("Intersection House ");
                RotateTank(-currentAngle); //Rotate angle to orignal position
                newTankPosX=OldTankPositionX;  //Calculate position x and y
                newTankPosY=OldTankPositionY;
                setPosition(newTankPosX, newTankPosY); // Update position
                RotateTank(OldAngle); // Return back to angle

            }
          
        if(intersectsMountain(Mountain1Spr))
            {
                double OldAngle=currentAngle;
                //System.out.println("Intersection Mountain ");
                RotateTank(-currentAngle);//Rotate angle to orignal position
                newTankPosX=OldTankPositionX;//Calculate position x and y
                newTankPosY=OldTankPositionY;
                setPosition(newTankPosX, newTankPosY);// Update position
                RotateTank(OldAngle);// Return back to angle
            }

        if(intersectsMountain(Mountain2Spr))
            {
                double OldAngle=currentAngle;
                //System.out.println("Intersection Mountain ");
                RotateTank(-currentAngle);
                newTankPosX=OldTankPositionX;
                newTankPosY=OldTankPositionY;
                setPosition(newTankPosX, newTankPosY);
                RotateTank(OldAngle);

            }

        //Checking if Tank moved out of window
        if (this.getPositionX() + this.GetWidth() < 0)
            {
                double oldangle = currentAngle;
                RotateTank(-oldangle);
                double currPosX = 775;   
                SetPositionX(currPosX);
                RotateTank(oldangle);
    
            } else if (this.getPositionX() > 775) {
    
                double oldangle = currentAngle;
                RotateTank(-oldangle);
                double currPosX  = -25;
                SetPositionX(currPosX);
                RotateTank(oldangle);
                
            }
            if (this.getPositionY() + this.getHeight() < 0) {
    
                double oldangle = currentAngle;
                RotateTank(-oldangle);
                double currPosY  = 575;
                SetPositionY(currPosY);
                RotateTank(oldangle);
                
            }
            if (this.getPositionY() > 575) {
                double oldangle = currentAngle;
                RotateTank(-oldangle);
                double currPosY = -25;
                SetPositionY(currPosY);
                RotateTank(oldangle);
            }
       
        
    }
    public boolean CheckIntersectionTankWithMines(ArrayList<MineSprite> MinesList) {
        for(int i=0; i<MinesList.size();i++)
        {   
            if(intersectsMine(MinesList.get(i)))
                    return true;
                
        }

        return false;

    }
   
    public void UpdateImage(Image image){
        GetimageView().setImage(image);
        GetimageView().setFitHeight(image.getHeight());
        GetimageView().setFitWidth(image.getWidth());
    }

    @Override
    public Rectangle2D getBoundary() { //Boundary of Tank
        return new Rectangle2D(getPositionX()+GetWidth()/2, getPositionY()+getHeight()/2, GetWidth()-3, getHeight()-3);
    }

    public Rectangle2D getBoundaryTank() { //Boundary of Tank2
        return new Rectangle2D(getPositionX(), getPositionY(), GetWidth(), getHeight());
    }
 
    public Rectangle2D getBoundaryMountain() { //Boundary of Mountain
        return new Rectangle2D(getPositionX()+15, getPositionY()+15, GetWidth()-45, getHeight()/2-10);
    }
    
    public Rectangle2D getBoundaryHouse() { //Boundary of House
        return new Rectangle2D(getPositionX()+15, getPositionY()+10, GetWidth()-40, getHeight()-30);
    }
    
    public Rectangle2D getBoundaryHouseRocket() { //Boundary of House Rocket
        return new Rectangle2D(getPositionX()+35, getPositionY()+20, GetWidth()-70, getHeight()-35);
    }

    public Rectangle2D getBoundaryMine(MineSprite spr) { // Boundary of Mine
        return new Rectangle2D(spr.getPositionX()+40, spr.getPositionY()+27.5, spr.GetWidth()/2-35, spr.getHeight()/2-7.5);
    }

    public boolean intersectsMountain(Sprite spr) { //Intersection of Mountain
        return spr.getBoundary().intersects(this.getBoundaryMountain());
    }

    public boolean intersectsHouseRocket(HouseRocketSprite spr) { //Intersection of House Rocket
        return spr.getBoundary().intersects(this.getBoundaryHouseRocket());
    }

    public boolean intersectsHouse(Sprite spr) { //Intersection of House
        return spr.getBoundary().intersects(this.getBoundaryHouse());
    }


    public boolean intersectsMine(MineSprite spr) //Intersection of Mine
    {
        return this.getBoundaryTank().intersects(this.getBoundaryMine(spr));
        
    }
    public double GetTankCurrentAngle()
    {
        return currentAngle;
    }

}