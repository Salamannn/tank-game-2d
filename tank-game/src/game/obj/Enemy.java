
package game.obj;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import javax.swing.ImageIcon;

public class Enemy {
    public Enemy(){
        this.image = new ImageIcon(getClass().getResource("/game/image/tank1.png")).getImage();
        Path2D p=new Path2D.Double();
        p.moveTo(0, ENEMY_SIZE/2);
        p.lineTo(0, 0);
        p.lineTo(ENEMY_SIZE, ENEMY_SIZE);
        
        enemyShap=new Area(p);
    }
    
    public static final double ENEMY_SIZE =64;
    private double x; //titik x 
    private double y; //titik y
    private final float speed = 0.3f; //speed
    private float angle = 0f;   //sudut arah gambar/obj player
    private final Image image;
    private final Area enemyShap;
    
    
    public void chageAngle(float angle){
        if(angle<0){
            angle=359;
        }else if(angle>360){
            angle=0;
        }
        this.angle = angle;
    }
    
    //ubah posisi titik dari obj player
    public void changeLocation(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    //void untuk bergerak maju
    public void update(){
        x+=Math.cos(Math.toRadians(angle))*speed;
        y+=Math.sin(Math.toRadians(angle))*speed;
    }
    
    public void draw(Graphics2D g2){
        AffineTransform oldTransform = g2.getTransform();
        g2.translate(x, y);
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle+90),ENEMY_SIZE / 2, ENEMY_SIZE / 2);
        g2.drawImage(image, tran, null);
        Shape shap=getShape();
        g2.setTransform(oldTransform);
        
        //tes
        g2.setColor(Color.red);
        g2.draw(shap);
        g2.draw(shap.getBounds2D());
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public float getAngle(){
        return angle;
    }
    
    public Area getShape(){
        AffineTransform afx=new AffineTransform();
        afx.translate(x, y);
        afx.rotate(Math.toRadians(angle),ENEMY_SIZE/2,ENEMY_SIZE/2);
        return new Area(afx.createTransformedShape(enemyShap));
    }
    
    public boolean cek(int width,int height){
        Rectangle size=getShape().getBounds();
         if(x<= -size.getWidth()|| y<=-size.getHeight() || x>width || y>height){
             return false;
         }else{
             return true;
         }
     }
}
