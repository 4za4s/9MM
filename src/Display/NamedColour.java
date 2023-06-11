package Display;


public  class NamedColour extends java.awt.Color {

    public static final NamedColour BLUE = new NamedColour(0,0,255, "Blue");

    public static final NamedColour RED = new NamedColour(255,0,0, "Red");

    public static final NamedColour YELLOW = new NamedColour(255,255,0, "Yellow");

    public static final NamedColour MAROON = new NamedColour( 128,0,0, "Maroon");

    public static final NamedColour GREEN = new NamedColour( 0,255,0, "Green");

    public static final NamedColour PURPLE = new NamedColour( 127,0,255, "Purple");

    public static final NamedColour LIGHT_SLATE_GREY = new NamedColour( 119,136,153, "Light Slate Grey");

    public static final NamedColour MAGENTA = new NamedColour( 255,0,255, "Magenta");

    public static final NamedColour BLACK = new NamedColour( 0,0,0, "Black (silly)");

    public static final NamedColour WHITE = new NamedColour( 255,255,255, "White (silly)"); 

  

    String name;

    public NamedColour(int r, int g, int b, String name){
        super(r,g,b);
        this.name = name;

    }




    @Override
    public String toString() {
        return name;







            

    //     }


     }





        
    


    
    
}
