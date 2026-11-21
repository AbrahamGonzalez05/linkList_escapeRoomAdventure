//Abraham Gonzalez

package game;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class GameGUI extends JFrame {

	private JPanel contentPane;
	private static JTextArea sceneDescription;
	private static JLabel lblImage;
	private static JButton btnChoice1;
	private static JButton btnChoice2;
	private static Scene currentScence;
	private static AdventureGame game;
	private static JTextArea txtItemsRoom;
	private JTextField userInventory;
	private JButton btnPickUpItem;

	private JLabel lblRoomTitle;
	
	public static void main(String[] args) {
        //game.play();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI frame = new GameGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameGUI() {
		
		game = new AdventureGame();
		currentScence = game.getCurrentScene();
		//updateSceneDisplay();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 511);
		contentPane = new JPanel();
		contentPane.setBackground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Choose Your Own Adventure Game");
		lblTitle.setFont(new Font("Fahkwang", Font.BOLD, 14));
		lblTitle.setBounds(148, -14, 282, 79);
		contentPane.add(lblTitle);
		
		lblImage = new JLabel("");
		ImageIcon icon = new ImageIcon("images2/lobby.png");
		Image img = icon.getImage();
		
		Image scaledImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);

		lblImage.setIcon(scaledIcon);
		lblImage.setBounds(145, 47, 300, 300);
		contentPane.add(lblImage);

		lblRoomTitle = new JLabel("");
		lblRoomTitle.setFont(new Font("Fahkwang", Font.BOLD, 18));
		lblRoomTitle.setBounds(170, 330, 285, 25);
		contentPane.add(lblRoomTitle);

		
		sceneDescription = new JTextArea();
		sceneDescription.setBounds(170, 363, 285, 37);
		sceneDescription.setEditable(false);
		sceneDescription.setLineWrap(true);
		sceneDescription.setWrapStyleWord(true);
		contentPane.add(sceneDescription);
		
		btnChoice1 = new JButton("New button");
		btnChoice1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nextId = currentScence.getChoices().get(0).getNextSceneId();	            
				game.moveToScene(nextId);
				currentScence = game.getCurrentScene();

	            updateSceneDisplay();
	            
	            if (currentScence.getSceneId() == 5) {
	    			checkWinCondition();
	    		}
				
			}
		});
		btnChoice1.setBounds(6, 170, 99, 79);
		contentPane.add(btnChoice1);
		
		btnChoice2 = new JButton("New button");
		btnChoice2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nextId = currentScence.getChoices().get(1).getNextSceneId();
	            
				game.moveToScene(nextId);
				currentScence = game.getCurrentScene();

	            updateSceneDisplay();
	            
	            if (currentScence.getSceneId() == 5) {
	    			checkWinCondition();
	    		}
				
			}
		});
		btnChoice2.setBounds(463, 170, 101, 79);
		contentPane.add(btnChoice2);
		
		txtItemsRoom = new JTextArea();
		txtItemsRoom.setBounds(28, 334, 130, 75);
		txtItemsRoom.setLineWrap(true);
		txtItemsRoom.setWrapStyleWord(true);
		txtItemsRoom.setEditable(false);
		contentPane.add(txtItemsRoom);
		//txtItemsRoom.setColumns(10);
		
		userInventory = new JTextField();
		userInventory.setText("Inventory: empty");
		userInventory.setColumns(10);
		userInventory.setBounds(166, 422, 305, 55);
		contentPane.add(userInventory);
		
		btnPickUpItem = new JButton("Pick up Item");
		btnPickUpItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.pickupCurrentItem();

				currentScence = game.getCurrentScene();

				updateSceneDisplay();
			}
		});
		btnPickUpItem.setBounds(37, 421, 117, 29);
		contentPane.add(btnPickUpItem);
		
		updateSceneDisplay();
	}
	
	public void checkWinCondition() {
		if (game.canWinGame()){
			javax.swing.JOptionPane.showMessageDialog(this, "You used the Keycard " + "and the Code Note " + "to unlock the exit.\n" + "you escaped. \n" + "You WIN!");
		} else {
			javax.swing.JOptionPane.showMessageDialog(this, "The exit is Locked. \n" + " You need Both:\n" + "Keycard\n" + "code Note");
		}

		btnChoice1.setEnabled(false);
		btnChoice2.setEnabled(false);
		btnPickUpItem.setEnabled(false);	
		
	}

	public void updateSceneDisplay() {

        lblRoomTitle.setText(
                currentScence.getTitle());

        sceneDescription.setText(
                currentScence.getDescription());

        Item itemInRoom =
                currentScence.getItem();

        if (itemInRoom == null) {

            txtItemsRoom.setText(
                    "No items in this room.");

            btnPickUpItem.setEnabled(false);

        } else {

            txtItemsRoom.setText(
                    "Item Found:\n"
                    + itemInRoom.getName());

            btnPickUpItem.setEnabled(true);
        }

        userInventory.setText(
                game.getPlayer()
                    .getInventoryText());

		String path = currentScence.getImagePath();

		ImageIcon icon = new ImageIcon(path);

		if (icon.getIconWidth() != -1) {

    	Image img = icon.getImage().getScaledInstance(300,300,Image.SCALE_SMOOTH);

    	lblImage.setIcon(new ImageIcon(img));
}

        if (currentScence.getChoices().size() > 0) {

            btnChoice1.setVisible(true);

            btnChoice1.setText(
                "<html>"
                + currentScence
                    .getChoices()
                    .get(0)
                    .getText()
                + "</html>");

        } else {

            btnChoice1.setVisible(false);
        }

        if (currentScence.getChoices().size() > 1) {

            btnChoice2.setVisible(true);

            btnChoice2.setText(
                "<html>"
                + currentScence
                    .getChoices()
                    .get(1)
                    .getText()
                + "</html>");

        } else {

            btnChoice2.setVisible(false);
        }
    }
}
