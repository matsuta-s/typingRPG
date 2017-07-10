public class Test2 implements Runnable{
  private String str;
  private int count;

  public Test2(String str,int count){
    this.str = str;
    this.count = count;
  }

  public void run(){
    while(count > 0){
      try{
        Thread.sleep(1000);
        System.out.println(str);
        count--;
      }catch(InterruptedException e){
        System.err.println(e);
      }
    }
  }

  public static void main(String[] args) {
    Test2 test = new Test2("nuan",10);
    new Thread(test).start();
  }
}
