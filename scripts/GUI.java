public class GUI {
    
    private static boolean IsStart = false;
    private static boolean IsSoundPlayed = false;

    /**
     * Render the Menu
     */
    public static void RenderMenu(){
        // Rendering initializing
        
        GameManager.RenderBG();
        StdDraw.picture(-220, 0, GameManager._bird.birdSprites[Bird.ColorIndex][1]);

        StdDraw.picture(GameManager.BG_x - 168, -250, "../sprites/base.png");
        StdDraw.picture(GameManager.BG_x + 168, -250, "../sprites/base.png");
        StdDraw.picture(GameManager.BG_x + 504, -250, "../sprites/base.png");
        
         
        StdDraw.picture(0, 80, "../sprites/message.png");

        while(!IsStart){
            if(CirButton(255,225, 18, "../sprites/option.png")) Option();
            if(RecButton(0,-95,200,73,"../sprites/StartButton.png")) IsStart = true;
        }

        IsStart = false;
    }

    /**
     * Create a rectangular button, and stop the program until the button is pressed
     */
    public static boolean RecButton(double x,double y, double SpriteWidth, double SpriteHeight, String filename){  
        String filename_pressed = filename.substring(0, filename.length()-4) + "-pressed.png";

        StdDraw.show();

        if(StdDraw.isMousePressed() && IsMouseInSprite(x,y,SpriteWidth,SpriteHeight)){
            while(StdDraw.isMousePressed());
            StdAudio.playInBackground("../audio/select.wav");
            return true;
        }
        
        if(!IsMouseInSprite(x,y,SpriteWidth,SpriteHeight)){
            StdDraw.picture(x,y,filename);
        } else {
            StdDraw.picture(x,y,filename_pressed);
        }

        return false;
          
    }

    /**
     * Create a circular button
     */
    public static boolean CirButton(double x,double y, double SpriteRadius,String filename){
        String filename_pressed = filename.substring(0, filename.length()-4) + "-pressed.png";
        
        StdDraw.show();

        if(StdDraw.isMousePressed() && IsMouseInSprite(x,y,SpriteRadius)){
            while(StdDraw.isMousePressed());
            StdAudio.playInBackground("../audio/select.wav");
            return true;
        }

        if(!IsMouseInSprite(x,y,SpriteRadius)){
            StdDraw.picture(x,y,filename);
        } else {
            StdDraw.picture(x,y,filename_pressed); 
        }

        return false;
    }

    /**
     * Change the gameplay settings
     */
    private static void Option(){
        StdDraw.picture(0,0,"../sprites/OptionBoard.png");
        StdDraw.show();
     
        while(true){  
            // adding buttons
            if(CirButton(255,225, 18, "../sprites/option.png")) break;
            if(CirButton(15,100, 17, "../sprites/yellowbird-midflap.png"))Bird.ColorIndex = 0;
            if(CirButton(90,100, 17, "../sprites/bluebird-midflap.png"))Bird.ColorIndex = 1;
            if(CirButton(165,100, 17, "../sprites/redbird-midflap.png"))Bird.ColorIndex = 2;
            if(CirButton(15,3, 17, "../sprites/numbers/1.png"))GameManager.BGSpeed =2;
            if(CirButton(90,3, 17, "../sprites/numbers/2.png"))GameManager.BGSpeed = 3;
            if(CirButton(165,3, 17, "../sprites/numbers/3.png"))GameManager.BGSpeed = 4;
            if(CirButton(15,-98, 17, "../sprites/numbers/2.png"))timer.EventDensity = 20;
            if(CirButton(90,-98, 17, "../sprites/numbers/3.png"))timer.EventDensity = 30;
            if(CirButton(165,-98, 17, "../sprites/numbers/4.png"))timer.EventDensity = 40;

        }
       
        GameManager.RenderBG();
        StdDraw.picture(-220, 0, GameManager._bird.birdSprites[Bird.ColorIndex][1]);

        StdDraw.picture(GameManager.BG_x - 168, -250, "../sprites/base.png");
        StdDraw.picture(GameManager.BG_x + 168, -250, "../sprites/base.png");
        StdDraw.picture(GameManager.BG_x + 504, -250, "../sprites/base.png");
        
         
        StdDraw.picture(0, 80, "../sprites/message.png");

    }


    /**
     * Detect whether the mouse is inside the given sprite
     */
    private static boolean IsMouseInSprite(double x,double y, double SpriteWidth, double SpriteHeight){
        return (StdDraw.mouseX() > x - SpriteWidth/2) && (StdDraw.mouseX() < x + SpriteWidth/2) && (StdDraw.mouseY() > y - SpriteHeight/2) && (StdDraw.mouseY() < y + SpriteHeight/2);
    }

    /**
     * Detect whether the mouse is inside the given sprite
     */
    private static boolean IsMouseInSprite(double x,double y, double SpriteRadius){
        return (GameManager.Distance(StdDraw.mouseX(), StdDraw.mouseY(), x, y) <= SpriteRadius);
    }

}
