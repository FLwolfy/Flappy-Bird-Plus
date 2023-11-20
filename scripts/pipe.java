public class pipe {
    
    public int index = 0;
    public boolean UpDowning = false;
    public double x = 350;
    public double y = 265*Math.random()-105;
    public double moveSpeed = 1.2*(Math.random() + 0.5);
    public String[] sprite = {"../sprites/pipe-green-up.png","../sprites/pipe-red-up.png","../sprites/pipe-green-down.png","../sprites/pipe-red-down.png"};
    
    public static boolean EnableAnimation = true;

    private boolean up = Math.random() < 0.5;
    
    /**
     * Enable pipe's renderer based on its spriteIndex
     */
    public void EnableRenderer(int SpriteIndex){
        index = SpriteIndex;
        
        StdDraw.picture(x, y - 205, sprite[index]);
        StdDraw.picture(x, y + 205, sprite[index+2]);

        if(index == 0 && UpDowning) UpDowning = false;
        if(index == 1 && !UpDowning) UpDowning = true;

    }

    /**
     * Render pipe's next animated sprites in next frame
     */
    public void AnimatedNextFrame(){      
            if(EnableAnimation){
                Horizontalmovement();
                if(UpDowning) Verticalmovement();
            }

            StdDraw.picture(x, y - 205, sprite[index]);
            StdDraw.picture(x, y + 205, sprite[index+2]);
    }

    /**
     * pipe's horizontal movement
     */
    private void Horizontalmovement(){
        x -= GameManager.BGSpeed;
    }

    /**
     * pipe's vertical movement:
     * ①random direction;
     * ②random speed;
     */
    private void Verticalmovement(){      
        if(!up) {
            if(y <= -105){
                up = true;
            }
            y -= moveSpeed;
        } 
        
        if(up) {
            if(y >= 160){
                up = false;
            }
            y += moveSpeed;
        } 
    }
}
