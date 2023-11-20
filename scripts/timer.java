import java.util.*;

public class timer {
    
    // initialize timer and task
    public static double sign_y = 270;
    public static String[] timeInt = {"../sprites/numbers/0.png","../sprites/numbers/1.png","../sprites/numbers/2.png","../sprites/numbers/3.png","../sprites/numbers/4.png","../sprites/numbers/5.png","../sprites/numbers/6.png","../sprites/numbers/7.png","../sprites/numbers/8.png","../sprites/numbers/9.png"};
    public static String[] signSprites = {"../sprites/Crazy-Pipes.png","../sprites/More-Gravity.png"};
    public static int EventDensity = 20;
    public static int EventLastTime = 12;
    public static int signIndex = -1;
    public static int counter = 0;
    public static Timer GameTimer;   

    private static double timer_y = -235;

    /**
     * Initialize timer and start SpecialEvent counter
     */
    public static void Start(){
        
        assert EventDensity > EventLastTime;
        
        TimerTask task = new TimerTask(){   
        
            @Override
            public void run(){
                counter ++;
    
                // Special Event every "EventDensity" seconds
                if(counter%EventDensity==0) {
                    System.out.println("Special Event comes!");
                    SpecialEvent();
                }    
            }
        };

        GameTimer = new Timer(true);
        
        // run every second
        GameTimer.scheduleAtFixedRate(task,new Date(),1000);

        System.out.println("Timer starts!");
    }

    /**
     * cancel the timer and its counter
     */
    public static void End(){
        GameTimer.cancel();
        counter = 0;
    }

    /**
     * Holding random special events in a regular basis "EventDensity" with the duration "EventLastTime"
     */
    public static void SpecialEvent(){
        int event = (int) Math.round(1*Math.random());

        switch(event){
            case 0: {
                System.out.println("Moving pipes come!");

                signIndex = 0;

                // run every 0.1s
                Timer mad_pipe = new Timer(false);
                mad_pipe.schedule(new TimerTask() {
                    @Override
                    public void run(){
                        for(int i=0;i<3;i++){
                            GameManager.pipes[i].index = 1;
                            GameManager.pipes[i].UpDowning = true;
                        }

                        if(counter%EventDensity >= EventLastTime + 1 || signIndex < 0) mad_pipe.cancel();
                    }
                }, new Date(),100); 

                break;
            }
            
            case 1: {
                System.out.println("More Gravity!");

                signIndex = 1;

                GameManager._bird.flyForce *= 1.4;
                GameManager._bird.gravity *= 2;
                break;
            }
        }
    }

    /**
     * Render the timer
     */
    public static void RenderTimer(){
        int digit = (int) Math.floor(Math.log10(counter)) + 1;

        if(digit%2==1){
            for(int i=0;i<digit;i++){
                if(counter % EventDensity == 0){
                    StdDraw.picture(((i+1)-(digit/2+1))*30, timer_y, timeInt[counter/(int)Math.pow(10,digit-i-1)%10].substring(0, timeInt[counter/(int)Math.pow(10,digit-i-1)%10].length()-4) + "-pressed.png");
                    continue;
                } else StdDraw.picture(((i+1)-(digit/2+1))*30, timer_y, timeInt[counter/(int)Math.pow(10,digit-i-1)%10]); 

            }
        } else {
            for(int i=0;i<digit;i++){
                if(counter % EventDensity == 0){
                    StdDraw.picture(((i+1)-(digit/2+1))*30+15, timer_y, timeInt[counter/(int)Math.pow(10,digit-i-1)%10].substring(0, timeInt[counter/(int)Math.pow(10,digit-i-1)%10].length()-4) + "-pressed.png");
                    continue;
                } else StdDraw.picture(((i+1)-(digit/2+1))*30+15, timer_y, timeInt[counter/(int)Math.pow(10,digit-i-1)%10]);
            }
        }

    }

    /**
     * Render Event's sign in next frame
     */
    public static void RenderEventSign(){
        if(signIndex >= 0){
            StdDraw.picture(0, sign_y,signSprites[signIndex]);
            
            if(counter%EventDensity >= EventLastTime) sign_y += 5;
            else if(counter%EventDensity <=2 && sign_y > 190){
                sign_y -= 5;
            }

            if(sign_y >= 270) setNormal();
            
        }    
    }

    /**
     * Reset all variable changes caused by special events
     */
    public static void setNormal(){
        GameManager._bird.gravity = -0.3;
        GameManager._bird.flyForce = 5.8;
        signIndex = -1;
    }

}
