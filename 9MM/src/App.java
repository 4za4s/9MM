import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;


public class App{

    public static void main(String[] args) {  

        JFrame frame = new JFrame("9 Mans Morris");

        

        //Set frame settings
        int frameWidth = 1000;
        int frameHeight = 1100;

        frame.setSize(frameWidth, frameHeight);

        // frame.setResizable(false); //prevent resizing 

        //Frame settings
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the board
        Board board = new Board();
    
        //Create board visual
        DrawBoard panel = new DrawBoard(frame,100,board);




        panel.setLayout(new FlowLayout());

        frame.add(panel);


        //Not sure why this line can't be earlier. It seems to get rid of non-determinism in the drawing
        frame.setVisible(true);


        // //Fun resizing
        // frame.addComponentListener(new ComponentAdapter() {
        //     public void componentResized(ComponentEvent e) {
        //         System.out.println("Size Changed");
                
        //         frame.remove(panel);
             
        //         frame.add(new DrawBoard(frame,100,board));
        //         frame.revalidate();
        //         frame.repaint();
        //     }
        // });


        // frame.repaint(); //try this if rendering looks weird
       

        


    }
    }


