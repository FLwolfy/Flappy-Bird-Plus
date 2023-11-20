import java.util.*;

public class GameManager extends Thread
{
    // character properties
    public static int _life = 10;
    public static int _coin = 0;

    
    // Initializing variables
    public static boolean IsHurt = false;
    public static boolean IsDead = true;
    public static double BG_x = 0;
    public static double BGSpeed = 3;

    public static String[] lives = {"../sprites/Hearts/Hearts0.png","../sprites/Hearts/Hearts1.png","../sprites/Hearts/Hearts2.png","../sprites/Hearts/Hearts3.png","../sprites/Hearts/Hearts4.png","../sprites/Hearts/Hearts5.png","../sprites/Hearts/Hearts6.png","../sprites/Hearts/Hearts7.png","../sprites/Hearts/Hearts8.png","../sprites/Hearts/Hearts9.png","../sprites/Hearts/Hearts10.png",};
    public static Bird _bird = new Bird();
    public static pipe[] pipes = new pipe[3]; 
    public static coin[] coins = new coin[2];        
    public static int HitTimeCounter = 0;
    public static int BS_index = 1;

    public static void main(){


        // Initializing Canvas
        StdDraw.setCanvasSize(576, 512);
        StdDraw.setXscale(-288, 288);
        StdDraw.setYscale(-256, 256);
        StdDraw.enableDoubleBuffering();
        
        // Rendering initializing
        RenderBG();
        _bird.EnableRenderer();

        pipe_generator();
        pipes[0].EnableRenderer(0);

        coin_generator();

        AnimatedNextFloor();

        // start another thread for rendering animation
        GameManager _gamemanager = new GameManager();
        _gamemanager.start();
        
        // StartMenu (pause until press the START button)
        GUI.RenderMenu();

        // start timer
        timer.Start();

        // start looping function
        IsDead = false;
        Update();
    }
    
    /**
     * Updating function in 144 FPS
     */ 
    @Override
    public void run(){ 
        while(true){ 
            /////////////////////////////////////////////////////////////////////////////////////
            // add code here:
            NextAnimation();     
            DeathRenderer();



            /////////////////////////////////////////////////////////////////////////////////////
            try {
                Thread.sleep(7);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }    
    }

    /**
     * Updating function per 0.02s
     */
    private static void Update(){                   
        Timer _timer = new Timer(true);
        _timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                /////////////////////////////////////////////////////////////////////////////////////
                // add codes here:
                _bird.MoveEngine();
                HurtDetector();
                CoinGetter();



                /////////////////////////////////////////////////////////////////////////////////////
            }
        }, new Date(),20);        
    }

    /**
     * Render All sprites that will be shown up in the next frame
     */
    private static void NextAnimation(){
        if(!IsDead){
            RenderBG();       
            pipe_generator();
            coin_generator();
            _bird.AnimatedNextFrame();
            AnimatedNextFloor();
            RenderLives();

            timer.RenderEventSign();
            timer.RenderTimer();
            
            StdDraw.show();
        }
    }

    /**
     * Render bird's lives that will be shown up in the next frame
     */
    private static void RenderLives(){
    
        StdDraw.picture(-255, 225, lives[_life]);

    }   
    

    /**
     * Render BackGround sprites that will be shown up in the next frame
     */
    public static void RenderBG(){
    
        StdDraw.picture(- 144, 0, "../sprites/background-day.png");
        StdDraw.picture(144, 0, "../sprites/background-day.png");
    }   

    /**
     * Render Floor sprites that will be shown up in the next frame
     */
    private static void AnimatedNextFloor(){
        if(!IsDead) BG_x -= BGSpeed;   
        if(BG_x <= -288) BG_x = 0;

        StdDraw.picture(BG_x - 168, -250, "../sprites/base.png");
        StdDraw.picture(BG_x + 168, -250, "../sprites/base.png");
        StdDraw.picture(BG_x + 504, -250, "../sprites/base.png");
    }

    /**
     * count the coin number and add lives every two coins
     */
    private static void CoinGetter(){
        if(HitCoin() != null){     

            HitCoin().x = -350;
            StdAudio.playInBackground("../audio/coin.wav");

            _coin ++;
            System.out.println("You have "+_coin+" coins!");

            if(_coin%2==0 && _life < 10){
                _life ++;
                System.out.println("lives added!");
            }
        }
    }

    /**
     * Detect and function bird's hurtings
     */
    private static void HurtDetector(){
        if(!IsDead && HitPipe() && !IsHurt){
            IsHurt = true;
            _life --;
            StdAudio.playInBackground("../audio/hit.wav");
            System.out.println(_life + " lives left!");

            // Hit Flashing Animation
            if(_life > 0){
                Timer flashingTimer = new Timer(true);
                flashingTimer.schedule(new TimerTask() {
                    @Override
                    public void run(){
                        HitTimeCounter ++;
                        if(HitTimeCounter%2==1) _bird.visible = false;
                        if(HitTimeCounter%2==0) _bird.visible = true;
                        
                        if(HitTimeCounter >=12){
                            IsHurt = false;
                            HitTimeCounter = 0;
                            flashingTimer.cancel();
                        }
                    }
                }, new Date(), 80);
            } else {
                IsHurt = false;
                IsDead = true;
            }
        }    
    }

    /**
     * Render and function bird's death
     */
    private static void DeathRenderer(){
        // dying animation and sound
        if(_bird.y < -185 || _life == 0) {
            _life = 0;
            RenderLives();

            IsDead = true;
            timer.End();
            StdAudio.playInBackground("../audio/die.wav");
            pipe.EnableAnimation = false;
            coin.EnableAnimation = false;

            while(_bird.y > -185){
                _bird.y += _bird.velocity*0.1;
                _bird.velocity += _bird.gravity*0.1;
                _bird.x += BGSpeed*0.5;
                

                RenderBG();
                pipe_generator();
                _bird.EnableRenderer();
                AnimatedNextFloor();
                timer.RenderEventSign();

                StdDraw.show();
            }

            timer.setNormal();
            StdDraw.picture(0, 0, "../sprites/gameover.png");
            StdDraw.show();

            // wait for 2 second
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            ResetGame();
        }
    }

    /**
     * Detect bird's collision with pipe
     */
    private static boolean HitPipe(){
        for(int i = 0; i < 3; i++){
            if(_bird.x+15 > pipes[i].x-26 && _bird.x-15 < pipes[i].x+26) {
                if(!(_bird.y+11 < pipes[i].y+45 && _bird.y-11 > pipes[i].y-45)) return true;
            }
        }

        return false;
    }

    private static coin HitCoin(){
        if(!IsDead){       
            for(int m = 0; m < 2; m++){
                if(Distance(_bird.x,_bird.y, coins[m].x, coins[m].y) < 27) 
                    return coins[m];
                
            }
        }

        return null;
    }

    /**
     * Generate random type of pipes; Enable cycle and pipes' spriteRenderers
     */
    private static void pipe_generator(){
        for(int i = 0; i < 3; i++){
            if(pipes[i] == null || pipes[i].x < -350) {
                pipes[i] = new pipe();
                pipes[i].EnableRenderer((int) Math.floor(1.25*Math.random()));
                
                if(i>0) pipes[i].x = pipes[i-1].x + 300;
            }

            if(pipes[0].x < -350) pipes[0].x = pipes[2].x + 300;

            pipes[i].AnimatedNextFrame();
        }
    }
    
    /**
     * Generate coins; Enable coins' cycle
     */
    private static void coin_generator(){     
        for(int i = 0;i<2;i++){
            if(coins[i] == null || coins[i].x < -350) {
                coins[i] = new coin();
                
                if(i>0) coins[i].x = coins[i-1].x + 300*(1+(int) Math.round(3*Math.random()));
            }

            if(coins[0].x < -350) coins[0].x = pipes[0].x + 1350 + 300*((int) Math.round(2*Math.random()));
            coins[i].AnimatedNextFrame();
        }
    }

    /**
     * Reset all game properties and return to main menu
     */
    private static void ResetGame(){
        _bird = new Bird();
        _life = 10;
        _coin = 0;

        pipe.EnableAnimation = true;
        pipes = new pipe[3]; 
        pipe_generator();
        pipes[0].EnableRenderer(0);

        coin.EnableAnimation = true;
        coins = new coin[2];

        RenderBG();       
        pipe_generator();
        coin_generator();
        _bird.AnimatedNextFrame();
        AnimatedNextFloor();
        timer.RenderEventSign();

        IsDead = false;
        
        StdDraw.show();

        GUI.RenderMenu();

        _bird = new Bird();
        timer.Start();
    }

    /**
     * calculate the distance between (x1,y1) and (x2,y2)
     */
    public static double Distance(double x1,double y1,double x2,double y2){
        return Math.sqrt(Math.pow(Math.abs(x1-x2),2) + Math.pow(Math.abs(y1-y2),2));
    }

    /**
     * Output "text" for debugging
     */
    public static void Debug(String text){
        System.out.println("Debugging...'" + text + "'");
    }
}
