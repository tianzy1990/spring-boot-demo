package test;

  class Thread2 implements Runnable{  
    private String name;  
  
    public Thread2(String name) {  
        this.name=name;  
    }  
  
    @Override  
    public synchronized void run() {  
          for (int i = 0; i < 5; i++) {  
                System.out.println(name + "运行  :  " + i);  
                try {  
                    Thread.sleep((int) Math.random() * 10);  
                } catch (InterruptedException e) {  
                    e.printStackTrace();  
                }  
            }  
          
    }  
      
}  
public class TestThread2 {  
  
    public static void main(String[] args) {  
        new Thread(new Thread2("C")).start();  
        new Thread(new Thread2("D")).start();  
    }  
  
} 