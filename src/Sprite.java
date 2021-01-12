import javafx.geometry.Rectangle2D;
import javafx.scene.image.*;




public class Sprite {

    private Image image;
    private double CurrentpositionX;
    private double CurrentpositionY;
    private double width;
    private double height;
    private ImageView ImgView;

    public Sprite(Image image,String imagepath) {
        this.image = image;
        width = image.getWidth();
        height = image.getHeight();
        CurrentpositionX = 0;
        CurrentpositionY = 0;
        ImgView = new ImageView(new Image(imagepath)); //sets image to the imageview
        ImgView.setFitHeight(image.getHeight());
        ImgView.setFitWidth(image.getWidth());

    }

    public void setPosition(double x, double y) {
        CurrentpositionX = x;
        CurrentpositionY = y;
        ImgView.setX(CurrentpositionX);
        ImgView.setY(CurrentpositionY);
    }

    public void SetPositionX(double x)
    {
        CurrentpositionX=x;
        ImgView.setX(x);
    }

    public void SetPositionY(double y)
    {
        CurrentpositionY=y;
        ImgView.setY(y);
    }

    public double getPositionX()
    {
        return CurrentpositionX;
    }

    public double getPositionY()
    {
        return CurrentpositionY;
    }

    public void SetWidth(double x)
    {
        width=x;
    }

    public double GetWidth()
    {
        return width;
    }

    public void setHeight(double y)
    {
        height=y;
    }

    public double getHeight()
    {
        return height;
    }
    
    public Image GetImage()
    {
        return image;
    }
    public void SetimageView(Image img)
    {
        ImgView.setImage(img);
    }

    public ImageView GetimageView()
    {
        return ImgView;
    }
    public Rectangle2D getBoundary() {
        Rectangle2D rect= new Rectangle2D(CurrentpositionX, CurrentpositionY, width, height);
        return rect;
    }

    public boolean intersects(Sprite spr) {
        return spr.getBoundary().intersects(this.getBoundary());
    }



}