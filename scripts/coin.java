public class coin {
    public double x = 800;
    public double y = 265*Math.random()-105;
    public static boolean EnableAnimation = true;
    
    private String[] coinSprites = {"../sprites/Coins/coin0.png","../sprites/Coins/coin1.png","../sprites/Coins/coin2.png","../sprites/Coins/coin3.png","../sprites/Coins/coin4.png","../sprites/Coins/coin5.png","../sprites/Coins/coin6.png","../sprites/Coins/coin7.png"};
    private int coinIndex = 0;

    /**
     * Enable coin's renderer based on its coinIndex
     */
    public void EnableRenderer(){
        StdDraw.picture(x,y,coinSprites[coinIndex]);
    }
    
    /**
     * Render coin's next animated sprites in next frame
     */
    public void AnimatedNextFrame(){
        if(EnableAnimation){
            coinIndex ++;
            if(coinIndex > 7) coinIndex = 0;

            x -= GameManager.BGSpeed;

            x = Math.min(x,2500);

            EnableRenderer();
        }
    }
}
