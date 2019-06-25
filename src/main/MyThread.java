package src.main;




public class MyThread extends Thread{
    Text text = new Text();





    public void run(){
        try {
            wait();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
