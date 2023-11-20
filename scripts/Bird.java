public class Bird { 
    public double x = -220;
    public double y = 0;
    public double flyForce = 5.8;
    public double gravity = -0.3;
    public double velocity = 0;
    public int SpriteIndex = 1;
    public static int ColorIndex = 0;

    public boolean visible = true;
    public boolean IsFlied = false;

    public String[][] birdSprites = {{"../sprites/yellowbird-downflap.png","../sprites/yellowbird-midflap.png","../sprites/yellowbird-upflap.png"},{"../sprites/bluebird-downflap.png","../sprites/bluebird-midflap.png","../sprites/bluebird-upflap.png"},{"../sprites/redbird-downflap.png","../sprites/redbird-midflap.png","../sprites/redbird-upflap.png"}};

    /**
     * Enable bird's spriterenderer
     */
    public void EnableRenderer(){
        if(visible) StdDraw.picture(x, y, birdSprites[ColorIndex][SpriteIndex]);
    }

    /**
     * Enable bird's gravity engine and fly engine
     */
    public void MoveEngine(){
        // Bird's Gravity Engine
        velocity += gravity;
        y += velocity; 
        velocity = Math.max(velocity, -20);

        // Flying Engine
        if(!GameManager.IsDead && !IsFlied && StdDraw.isKeyPressed(32)){
            IsFlied = true;
            velocity = flyForce;
            if(IsFlied)StdAudio.playInBackground("../audio/wing.wav");
        } else if(velocity < 0.5) IsFlied = false;    
    }

    /**
     * Render bird's next animated sprites in next frame 
     */
    public void AnimatedNextFrame(){
        if(velocity < -0.5) SpriteIndex = 2;
        else if(velocity > 0.5) SpriteIndex = 0;
        else SpriteIndex = 1;
        
        EnableRenderer();
    }
}
