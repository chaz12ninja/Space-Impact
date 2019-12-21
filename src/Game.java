import javax.swing.JFrame;

public class Game {
	public static int windowWidth = 1200;
	public static int windowHeight = 675;
	
	public static void main(String[] args) {
	
		JFrame obj = new JFrame();
			
		Gameplay gamePlay = new Gameplay();
		obj.setBounds( 10, 10, windowWidth, windowHeight);
		obj.setTitle("Space Impact");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
	}
}
