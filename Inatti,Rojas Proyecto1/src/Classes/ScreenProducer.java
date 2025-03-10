package Classes;

import Windows.*;
import java.util.concurrent.Semaphore;

public class ScreenProducer extends Thread {
    
    private int dayDuration;
    private double dailyProduce = 1;
    private boolean stop;
    private Semaphore mutexNormalScreens;
    private Semaphore semNormalScreens;
    private Semaphore semEnsambladorNormalScreens;
    private Semaphore mutexTouchScreens;
    private Semaphore semTouchScreens;
    private Semaphore semEnsambladorTouchScreens;
    
    public ScreenProducer(int dayDuration, Semaphore mutexNormalScreens, Semaphore semNormalScreens, Semaphore semEnsambladorNormalScreens, Semaphore mutexTouchScreens, Semaphore semTouchScreens, Semaphore semEnsambladorTouchScreens) {
        this.dayDuration = dayDuration;
        this.stop = false;
        this.mutexNormalScreens = mutexNormalScreens;
        this.semNormalScreens = semNormalScreens;
        this.semEnsambladorNormalScreens = semEnsambladorNormalScreens;
        this.mutexTouchScreens = mutexTouchScreens;
        this.semTouchScreens = semTouchScreens;
        this.semEnsambladorTouchScreens = semEnsambladorTouchScreens;
    }
    
    public void run() {
        while (true) {
            if (!this.stop) {
                try {
                    semNormalScreens.acquire();
                    Thread.sleep(Math.round((dayDuration * 1000) / dailyProduce));
                    mutexNormalScreens.acquire();
                    Menu.NormalScreens++;
                    Menu.NormalScreenStorage.setText(Integer.toString(Menu.NormalScreens));
                    if (Menu.OutputNormalScreens.getText().split("\n").length !=10) {
                        Menu.OutputNormalScreens.setText(Menu.OutputNormalScreens.getText() + "Normal -> " + Menu.NormalScreens + "\n");
                    }else{
                        Menu.OutputNormalScreens.setText("Normal -> " + Menu.NormalScreens + "\n");
                    }
                    mutexNormalScreens.release();
                    semEnsambladorNormalScreens.release();
                    
                    this.dailyProduce = 0.5;
                    
                    semTouchScreens.acquire();
                    Thread.sleep(Math.round((dayDuration * 1000) / dailyProduce));
                    mutexTouchScreens.acquire();
                    Menu.TouchScreens++;
                    Menu.TouchScreenStorage.setText(Integer.toString(Menu.TouchScreens));
                    if (Menu.OutputTouchScreens.getText().split("\n").length != 10) {
                        Menu.OutputTouchScreens.setText(Menu.OutputTouchScreens.getText() + "Táctil -> " + Menu.TouchScreens + "\n");
                    }else{
                        Menu.OutputTouchScreens.setText("Táctil -> " + Menu.TouchScreens + "\n");
                    }
                    mutexTouchScreens.release();
                    semEnsambladorTouchScreens.release();
                    
                    this.dailyProduce = 1;
                } catch (Exception e) {
                    
                }
            }
        }
    }
    
    public boolean isStop() {
        return stop;
    }
    
    public void setStop(boolean stop) {
        this.stop = stop;
    }
    
}
