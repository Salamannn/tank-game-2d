package game.component;


import game.main.Connectdb;
import game.main.HighScore;
import game.main.Main;
import game.main.Username;
import game.obj.Bullet;
import game.obj.Enemy;
import game.obj.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class PanelGame extends JComponent {
    
    private int hp;
    private int score;
    private Graphics2D g2; 
    private BufferedImage image; //gambar
    private int width; //ukuran panel
    private int height; //ukuran panel
    private Thread thread;
    private boolean start = true; 
    private Key key;
    private int shotTime; //durasi shot
    private String username;
    private int time;
    
    
    //fps
    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;
    
    //obj
    public static Player player;
    private List<Bullet> bullets;
    private List<Enemy> enemys;
    //private List<Player> playerss;

    
    public PanelGame(){
        player = new Player();
    }
    
    public void start(){
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        thread = new Thread(new Runnable(){
            @Override
            public void run(){
                while(start){
                    long startTime=System.nanoTime();
                    drawBackground();
                    drawGame();
                    render();
                    long time=System.nanoTime() - startTime;
                    if(time<TARGET_TIME){
                        long sleep = (TARGET_TIME - time) / 1000000;
                        sleep(sleep);
                    }
                }
            }
        });
        initObjectGame();
        initKeyboard();
        initBullets();
        getTime();
        thread.start();
    }
    
    private void addEnemy(){
        Random ran = new Random();
        int locationY = ran.nextInt(height-50)+25;
        Enemy enemy = new Enemy();
        enemy.changeLocation(0, locationY);
        enemy.chageAngle(0);
        enemys.add(enemy);
        int locationY2 = ran.nextInt(height-50)+25;
        Enemy enemy2 = new Enemy();
        enemy2.changeLocation(width, locationY2);
        enemy2.chageAngle(180);
        enemys.add(enemy2);
    }
    
    private void initObjectGame(){
        player.changeLocation(width/2, height/2); //posisi start obj player
        enemys=new ArrayList<>();
        new Thread(new Runnable(){
            @Override
            public void run() {
                while(start){
                    addEnemy();
                    sleep(3000);
                }
            }
        
        }).start();
    }
    
    private void initKeyboard(){
        key = new Key();
        requestFocus();
        addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode()== KeyEvent.VK_A){
                    key.setKey_left(true);
                }else if(e.getKeyCode()== KeyEvent.VK_D){
                    key.setKey_right(true);
                }else if(e.getKeyCode()== KeyEvent.VK_W){
                    key.setKey_w(true);
                }else if(e.getKeyCode()== KeyEvent.VK_J){
                    key.setKey_j(true);
                }else if(e.getKeyCode()== KeyEvent.VK_S){
                    key.setKey_s(true);
                }else if(e.getKeyCode()== KeyEvent.VK_K){
                    key.setKey_k(true);
                }
            }
            
            
            @Override
            public void keyReleased(KeyEvent e){
                if(e.getKeyCode()== KeyEvent.VK_A){
                    key.setKey_left(false);
                }else if(e.getKeyCode()== KeyEvent.VK_D){
                    key.setKey_right(false);
                }else if(e.getKeyCode()== KeyEvent.VK_W){
                    key.setKey_w(false);
                }else if(e.getKeyCode()== KeyEvent.VK_J){
                    key.setKey_j(false);
                }else if(e.getKeyCode()== KeyEvent.VK_S){
                    key.setKey_s(false);
                }else if(e.getKeyCode()== KeyEvent.VK_K){
                    key.setKey_k(false);
                }
            }
        });
        new Thread(new Runnable(){
            @Override
            public void run(){
                float s = 0.5f;
                while(start){
                    float angle = player.getAngle();
                    if(key.isKey_left()){
                        angle -= s;
                    }
                    if(key.isKey_right()){
                        angle += s;
                    }
                    if(key.isKey_j()||key.isKey_k()){
                        if(shotTime == 0){
                            if(key.isKey_j()){
                                bullets.add(0, new Bullet(player.getX(),player.getY(),player.getAngle(),5,3f));
                            }else{
                                bullets.add(0, new Bullet(player.getX(),player.getY(),player.getAngle(),15,3f));
                            }
                        }
                        shotTime++;
                        if(shotTime==15){
                            shotTime=0;
                        }
                    }else{
                        shotTime=0;
                    }
                    if(key.isKey_w()||key.isKey_s()){
                        player.speedUP();
                        player.speedDown();
                        if(key.isKey_w()){
                            player.update();
                        }else{
                            player.mundur();
                        }
                    }
                    player.chageAngle(angle);
                    cekPlayer(player);
                    for(int i=0;i<enemys.size();i++){
                        Enemy enemy=enemys.get(i);
                        if(enemy!=null){
                            enemy.update();
                            if(!enemy.cek(width, height)){
                                enemys.remove(enemy);
                            }
                        }
                    }
                    sleep(5);
                }
            }
        }).start();
    }
  
    private void initBullets(){
        bullets = new ArrayList<>();
        new Thread(new Runnable(){
            @Override
            public void run(){
                while(start){
                    for(int i=0;i<bullets.size();i++){
                        Bullet bullet=bullets.get(i);
                        if(bullet!=null){
                            bullet.update();
                            cekBullets(bullet);
                            if(!bullet.cek(width, height)){
                                bullets.remove(bullet);
                            }
                        }else{
                            bullets.remove(bullet);
                        }
                    }
                    sleep(1);
                }
            }
        }).start();
    }
    
    private void cekBullets(Bullet bullet){
        for(int i=0;i<enemys.size();i++){
            Enemy enemy = enemys.get(i);
            if(enemy!=null){
                Area area=new Area(bullet.getShape());
                area.intersect(enemy.getShape());
                if(!area.isEmpty()){
                    enemys.remove(enemy);
                    bullets.remove(bullet);
                    if(player.getHP()!=0){
                    player.setScore(score);
                    }
                }
            }
        }
    }
    
    private void cekPlayer(Player player){
        for(int i=0;i<enemys.size();i++){
            Enemy enemy = enemys.get(i);
            if(enemy!=null){
                Area area=new Area(player.getShape());
                area.intersect(enemy.getShape());
                if(!area.isEmpty()){
                    enemys.remove(enemy);
                    if(player.getHP()!=0){
                    player.setScore(score);
                    player.setHP(hp);
                    }
                }
            }
        }
    }
    
    public void GameOver(){
    }
    
    public Player getPlayer(){
        return this.player;
    }
    
    private void getTime(){
        Timer timer =new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            int i = 60;
            public void run() {
                g2.setFont(new Font("Arial", 1, 36));
                g2.setColor(Color.black);
                g2.drawString("Time 0:"+i, width/2, 30);
                i--;
                if (i < 0) {
                    timer.cancel();
                    g2.setFont(new Font("Arial", 1, 72));
                    g2.setColor(Color.red);
                    g2.drawString("GAME OVER", width/2, height/2);
                    start=false;
        new HighScore().setVisible(true);
        new Main().setVisible(false);
                }
        }},0,1000);
    }
    
    private void drawBackground(){
        g2.setColor(new Color(255,250,205));
        g2.fillRect(0,0,width,height);
        //font score
        g2.setFont(new Font("Arial", 1, 36));
        g2.setColor(Color.black);
        g2.drawString("Your score: "+player.getScore(), 1000, 30);
        //font user name
        g2.setFont(new Font("Arial", 1, 12));
        g2.setColor(Color.black);
        g2.drawString(""+player.getUsername(), (int) player.getX(), (int) player.getY()+2);
        //font HP
        g2.setFont(new Font("Arial", 1, 36));
        g2.setColor(Color.black);
        g2.drawString("Life Point :"+player.getHP(), 100, 30);
        //font Timer
//        g2.setFont(new Font("Arial", 1, 36));
//        g2.setColor(Color.black);
//        g2.drawString("Time 0:"+getTime(), width/2, 30);
        //game over
        if(player.getHP()==0){
        g2.setFont(new Font("Arial", 1, 72));
        g2.setColor(Color.red);
        g2.drawString("GAME OVER", width/2, height/2);
        
        try{
            String sql = "INSERT INTO highscore (username,score) VALUES ('"+player.getUsername()+"','"+player.getScore()+"')";
            java.sql.Connection con=(Connection)Connectdb.ConnectDB();
            java.sql.PreparedStatement pst=con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Permainan Berakhir");
           
        } catch (Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
        
        start=false;
        new HighScore().setVisible(true);
        new Main().setVisible(false);
        }
    }
    
    private void drawGame(){
        player.draw(g2);
        for(int i=0;i<bullets.size();i++){
            Bullet bullet=bullets.get(i);
            if(bullet!=null){
                bullet.draw(g2);
            }
        }
        for(int i=0;i<enemys.size();i++){
            Enemy enemy=enemys.get(i);
            if(enemy!=null){
                enemy.draw(g2);
            }
        }
    }
    
    private void render(){
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }
    
    
    public void sleep(long speed){
        try {
            Thread.sleep(speed);
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
    }
    
        

}
