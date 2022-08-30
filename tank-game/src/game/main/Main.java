package game.main;

import game.component.PanelGame;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Main extends JFrame{
     
    PanelGame panelgame = new PanelGame(); //memanggil panel game
    
    public Main(){
        init();
        
    }
    
    
    
    private void init(){
        setTitle("Tank Game");      //judul frame
        setSize(1366,768);          //ukuran frame
        setLocationRelativeTo(null); //posisi frame
        setResizable(false);        // tidak bisa diubah ukuran framenya
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //ketika frame di close
        setLayout(new BorderLayout());  //layoutaaaaaaaaa  
        add(panelgame); //menambah panelgame pada frame
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowOpened(WindowEvent e){
                panelgame.start();
            }
        });
        //remove(panelgame);
    }
    
    public static void main(String[] args){
        Main main = new Main();
        main.setVisible(true);
    }
}