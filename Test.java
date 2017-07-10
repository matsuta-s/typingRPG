public class Test implements Runnable {
  private String str;
  private int count;

  public Test(String str,int count){
    this.str = str;
    this.count = count;
  }

  public void run(){
    while(count > 0){
      try{
        Thread.sleep(100);
        System.out.println(str);
        count--;
      }catch(InterruptedException e){
        System.err.println(e);
      }
    }
  }

  public static void main(String[] args){
    Test test = new Test("hogetarou",10);
    new Thread(test).start();
  }
}
