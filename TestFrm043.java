package test;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
public class TestFrm043 extends TestFrm042 implements KeyListener, ActionListener
{
	public TestFrm043()	{
		this.btn1.removeActionListener(this);
		this.btn1.addActionListener(this);
		this.txt1.addKeyListener(this);
	}
	public void actionPerformed(ActionEvent e){
		if (this.btn1.getText().equals("delete") == false){
			super.actionPerformed(e);
		} else {
			int idx = this.lst1.getSelectedIndex();
			if (idx != -1){
				this.listModel.remove( idx );
			}
		}
	}
	public void keyPressed(KeyEvent e){
		System.out.println(this.txt1.getText() + ":Press=" + e.getKeyCode());
	}
	public void keyReleased(KeyEvent e)	{
		System.out.println(this.txt1.getText() + ":Released=" + e.getKeyCode());
		if (this.txt1.getText().equals("")) {
			this.btn1.setText("delete");
		} else {
			this.btn1.setText("add");
			if (e.getKeyCode() == 10) {
				this.actionPerformed(null);
			}
		}
	}
	public void keyTyped(KeyEvent e) {
		System.out.println(this.txt1.getText() + ":Typed=" + e.getKeyChar());
	}
	public static void main(String[] args) {
		new TestFrm043();
	}
}
