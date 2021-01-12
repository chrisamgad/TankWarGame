import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.*;


public class MineSprite extends Sprite {


    public MineSprite(Image image,String imagepath,Sprite TowerDefense,Sprite Mountain1,Sprite Mountain2, TankSprite Tank) {
        super(image,imagepath); // calls constructor
        
        Random randomGenerator = new Random();
        double RandomPositionX=randomGenerator.nextInt(750 -50) + 50; //using the rule random.nextInt(max - min) + min; ;
        double RandomPositionY= randomGenerator.nextInt(550 -50) + 50; //using the rule random.nextInt(max - min) + min; ;

        this.setPosition(RandomPositionX, RandomPositionY);
        while(
            Tank.intersectsMine(this) //Mine intersects tank
            ||this.GetimageView().intersects(Mountain1.GetimageView().getBoundsInParent()) //Mine intersects Mountain1
            ||this.GetimageView().intersects(Mountain2.GetimageView().getBoundsInParent()) // Mine intersects Mountain2
            ||this.GetimageView().intersects(TowerDefense.GetimageView().getBoundsInParent()) //Mine intersects Towerdefense
            ||RandomPositionX<100 // If passed this X
            ||RandomPositionX>650 // If passed this X
            ||RandomPositionY<100 // If passed this Y
            ||RandomPositionY>500 // If passed this Y
            ) 
        
        {   
            RandomPositionX= randomGenerator.nextInt(750 -50) + 50; //using the rule random.nextInt(max - min) + min; 
            RandomPositionY= randomGenerator.nextInt(550 -50) + 50; //using the rule random.nextInt(max - min) + min;            
            this.setPosition(RandomPositionX, RandomPositionY);
        }
                 
    }

    public void CheckIfIntersectAnotherMine(int CurrentMineIndex,ArrayList<MineSprite> MinesList)
    {
        Random randomGenerator = new Random();
        double RandomPositionX,RandomPositionY;
        
        for (int i=0;i<MinesList.size();i++) // If mine intersects another mine
        {    
            if(!(i< MinesList.size()))
                    break;
            
            if (i!=CurrentMineIndex)
            {   
                
                if (MinesList.get(CurrentMineIndex).intersects(MinesList.get(i)))
                {
                    RandomPositionX= randomGenerator.nextInt(750 -50) + 50; //using the rule random.nextInt(max - min) + min; 
                    RandomPositionY= randomGenerator.nextInt(550 -50) + 50; //using the rule random.nextInt(max - min) + min;

                    this.setPosition(RandomPositionX, RandomPositionY);
                    //MinesList.get
                    while(MinesList.get(CurrentMineIndex).intersects(MinesList.get(i)) 
                    ||RandomPositionX<100 // If passed this X
                    ||RandomPositionX>650 // If passed this X
                    ||RandomPositionY<100 // If passed this Y
                    ||RandomPositionY>500 // If passed this Y)
                    )
                    {   
                        //System.out.println("Mine number "+CurrentMineIndex+ " is intersecting with Mine number "+i);  
                        RandomPositionX= randomGenerator.nextInt(750 -50) + 50; //using the rule random.nextInt(max - min) + min; 
                        RandomPositionY= randomGenerator.nextInt(550 -50) + 50; //using the rule random.nextInt(max - min) + min;        

                        this.setPosition(RandomPositionX, RandomPositionY);
                    }
                }
            }
        }
    }



}