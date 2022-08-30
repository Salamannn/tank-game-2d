package game.obj;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import javax.swing.ImageIcon;
import java.awt.geom.Path2D;


public class Player {
    
    public Player(){
        this.image = new ImageIcon(getClass().getResource("/game/image/tank1.png")).getImage();
        this.image_speed = new ImageIcon(getClass().getResource("/game/image/tank1.png")).getImage();
        
        Path2D p=new Path2D.Double();
        p.moveTo(0, PLAYER_SIZE/2);
        p.lineTo(0, 0);
        p.lineTo(PLAYER_SIZE, PLAYER_SIZE);
        
        playerShap=new Area(p);
    }
    
    
    
    public static final double PLAYER_SIZE =64; //ukuran obj player
    private double x; //titik x 
    private double y; //titik y
    private final float MAX_SPEED = 1f; //maks speed
    private float speed = 0f; //speed
    private float angle = 0f;   //sudut arah gambar/obj player
    private final Image image;  //gambar obj
    private final Image image_speed; //gambar obj
    private boolean speedUP;  //kecepatan bertambah
    private static String username;
    private static int score=0;
    private static int hp=3;
    private final Area playerShap;
    
    
    //ubah sudut arah obj player
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
    
    //void untuk bergerak mundur
    public void mundur(){
        x-=Math.cos(Math.toRadians(angle))*speed;
        y-=Math.sin(Math.toRadians(angle))*speed;
    }
    
    //void membuat gambar player
    public void draw(Graphics2D g2){
        AffineTransform oldTransform = g2.getTransform();
        g2.translate(x, y);
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle+90),PLAYER_SIZE / 2, PLAYER_SIZE / 2);
        g2.drawImage(speedUP ? image_speed : image, tran, null);
        Shape shap=getShape();
        g2.setTransform(oldTransform);
        
        
        //tes
        g2.setColor(Color.green);
        g2.draw(shap);
        g2.draw(shap.getBounds2D());
        
    }
    
    public void setUsername(String username){
       this.username = username;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public void setScore(int score){
        this.score+=100;
    }
    
    public int getScore(){
        return this.score;
        
    }
    
    public void setHP(int hp){
        this.hp-=1;
    }
    
    public int getHP(){
        return this.hp;
    }
    
    public Area getShape(){
        AffineTransform afx=new AffineTransform();
        afx.translate(x, y);
        afx.rotate(Math.toRadians(angle),PLAYER_SIZE/2,PLAYER_SIZE/2);
        return new Area(afx.createTransformedShape(playerShap));
    }
    
    // 
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public float getAngle(){
        return angle;
    }
    
    //void kecepatan bertambah
    public void speedUP(){
        speedUP = true;
        if(speed>MAX_SPEED){
            speed=MAX_SPEED;
        }else{
            speed += 0.01f;
        }
    }
    
    //void kecepatan berkurang
    public void speedDown(){
        speedUP = false;
        if(speed<=0){
            speed=0;
        }else{
            speed -= 0.003f;
        }
    }
}
