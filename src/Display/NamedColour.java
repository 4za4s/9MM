package Display;

import java.awt.Color;

public  class NamedColour extends java.awt.Color {

    public static final Color BLUE = Color.BLUE;

    public NamedColour(){
        super(int i);
    }

    
    @Override
    public String toString() {
        String s = super.toString();

            //Maually check for each color. Not checking for lower case letters

        if (s == Color.BLACK.toString() ){
             return "Black";
        } else if (s == Color.BLUE.toString() ){
            return "Blue";
        } else if (s == Color.CYAN.toString()){
            return "Cyan";
        }


        return "Mystery Colour";
    //     switch (s){
    //         case Color.BLACK.toString():
               


    //         case Color.BLUE.toString():
    //             return "Blue";

    //         case Color.CYAN.toString():
    //             return "Cyan";

    //         case Color.DARK_GRAY.toString():
    //             return "Dark Grey";

    //         case Color.GRAY.toString():
    //             return "Grey";

    //         case Color.black.toString():
    //             return "Black";

    //         case Color.black.toString():
    //             return "Black";

    //         case Color.black.toString():
    //             return "Black";

    //         case Color.black.toString():
    //             return "Black";

    //         default:
    //             return "Mystery Colour";







            

    //     }


     }





        
    


    
    
}
