import javax.swing.*;
import java.awt.event.*;
public class JpTest extends JFrame implements ActionListener{
  private JLabel cntLbl;
  private int index = 0;
  private JPanel tstPnl;
  public static void main(String[] args){
    new JpTest();
  }
  JpTest(){
    cntLbl = new JLabel("Crick:" + index);
    JButton addBtn = new JButton("Count");
    addBtn.addActionListener(this);
    tstPnl = new JPanel();
    tstPnl.add(addBtn);
    tstPnl.add(cntLbl);
    getContentPane().add(tstPnl);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(10, 10, 300, 100);
    setTitle("タイトル");
    setVisible(true);
  }
  public void actionPerformed(ActionEvent e){
    cntLbl = new JLabel("Crick:" + ++index);
    cntLbl.repaint(); //NG
    cntLbl.revalidate(); //NG
    tstPnl.repaint(); //NG
  }
}
