
package game.obj;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;


public class Bullet {
     private double x;      //titik posisi peluru pada x
     private double y;      //titik posisi peluru pada y
     private final Shape shape; //bentuk peluru
     private final Color color =new Color(0,0,0); //warna peluru
     private final float angle; //sudut arah peluru
     private double size; //ukuran peluru
     private float speed = 1f; //kecepatan peluru
     
     //konstruktor untuk memanggil peluru
     public Bullet(double x,double y, float angle,double size,float speed){
         x+=Player.PLAYER_SIZE/2-(size/2);
         y+=Player.PLAYER_SIZE/2-(size/2);
         this.x = x;
         this.y = y;
         this.angle = angle;
         this.size = size;
         this.speed = speed;
         shape = new Ellipse2D.Double(0,0,size,size);
     }
     
     //void untuk peluru bergerak maju
     public void update(){
        x+=Math.cos(Math.toRadians(angle))*speed;
        y+=Math.sin(Math.toRadians(angle))*speed;
     }
     
     
     public boolean cek(int width,int height){
         if(x< -size || y< -size || x>width || y>height){
             return false;
         }else{
             return true;
         }
     }
     
     //void memuat gambar peluru pada panel
     public void draw(Graphics2D g2){
       AffineTransform oldTransform = g2.getTransform();
       g2.setColor(color);
       g2.translate(x, y);
       g2.fill(shape);
       g2.setTransform(oldTransform);
     }
     
    public Shape getShape(){
        return new Area(new Ellipse2D.Double(x, y, size, size));
    } 
     
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public double getSize(){
        return size;
    }
     
    public double getCenterX(){
        return x+size/2;
    }
    
    public double getCenterY(){
        return y+size/2;
    }
}
