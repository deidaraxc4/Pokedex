import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JScrollPane;

public class Pokedex extends JFrame {

	private JPanel contentPane;
	private Scanner input;
	private File source;
	private ArrayList<Pokemon> pokelist;
	private Pokemon x;
	private JList list;
	private JLabel picture;
	private JLabel desc;
	private JLabel type1;
	private JLabel type2;
	//private DefaultListModel listModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pokedex frame = new Pokedex();
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
	public Pokedex() {
		loadPokedex();
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainApp.class.getResource("/resources/images/Pokeball.png")));
		setTitle("Pokedex by Nick Tang");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 765, 459);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Border blackline = BorderFactory.createLineBorder(Color.black);
		
		
		
		list = new JList(pokelist.toArray());
		list.setBounds(21, 11, 140, 355);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setVisibleRowCount(10);
		list.setCellRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				if (renderer instanceof JLabel && value instanceof Pokemon) {
                    // Here value will be of the Type 'CD'
                    ((JLabel) renderer).setText(((Pokemon) value).getName());
                }
				return renderer;
				
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				//change the jlabel to picture of whatever is selected
				Pokemon x = (Pokemon) list.getSelectedValue();
				updateLabel(x.getName());
				updateDesc(x);
				if(x.isMonoType()==true) {
					//updateType1(x);
					//clearType2();
					updateColor(x.getType1(),true);
					resetColor();
				} else if(x.isMonoType()!=true){
					//updateType1(x);
					//updateType2(x);
					updateColor(x.getType1(),true);
					updateColor(x.getType2(),false);
				}
				
			}
			
		});
		//contentPane.add(list);
		
		picture = new JLabel("", SwingConstants.CENTER);
		picture.setBounds(192, 11, 289, 214);
		picture.setBorder(blackline);
		contentPane.add(picture);
		
		desc = new JLabel(" National ID:");
		desc.setBounds(192, 237, 289, 39);
		desc.setBorder(blackline);
		contentPane.add(desc);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 11, 140, 355);
		scrollPane.setViewportView(list);
		contentPane.add(scrollPane);
		
		type1 = new JLabel("", SwingConstants.CENTER);
		type1.setBounds(192, 287, 140, 39);
		type1.setBorder(blackline);
		type1.setOpaque(true);
		contentPane.add(type1);
		
		type2 = new JLabel("", SwingConstants.CENTER);
		type2.setBounds(342, 287, 140, 39);
		type2.setBorder(blackline);
		type2.setOpaque(true);
		contentPane.add(type2);
		
		
	}
	
	/**
	 * Read the .txt file and create pokemon objects and load into ArrayList
	 */
	public void loadPokedex() {
		ArrayList<String> a;
		pokelist = new ArrayList<Pokemon>();
		String name;
		String id;
		input = new Scanner(Test.class.getResourceAsStream("/resources/Pokemon.txt"));
		//Loop to read the text file and make Pokemon objects and store them into the pokelist arraylist
		while(input.hasNext()) {
			a = new ArrayList<String>();
			String line = input.nextLine();
			String[] words = line.split("\\t");
			if(words.length==3){
				a.add(words[2]);
				x= new Pokemon(words[0],words[1],a);
			}
			if(words.length==4) {
				a.add(words[2]);
				a.add(words[3]);
				x= new Pokemon(words[0],words[1],a);
			}
			if(words.length<3) {
				x = new Pokemon(words[0],words[1]);
			}
			pokelist.add(x);	
		}
		input.close();
	}
	public static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = MainApp.class.getResource(path);
		if(imgURL!=null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Could not find "+path);
			return null;
		}
	}
	/**
	 * updates the JLabel 'picture'
	 */
	public void updateLabel(String name) {
		ImageIcon icon = createImageIcon("resources/images/pokemon/" +name.toLowerCase()+ ".png");
		picture.setIcon(icon);
	}
	/**
	 * updates the JLabel 'type1' and 'type2'
	 */
	public void updateLabelType(String name, boolean primary) {
		ImageIcon icon = createImageIcon("resources/images/" +name+ ".png");
		if(primary==true) {
			type1.setIcon(icon);
		} if(primary!=true) {
			type2.setIcon(icon);
		}
		
	}
	/**
	 * updates the JLabel 'desc'
	 */
	public void updateDesc(Pokemon x) {
		desc.setText(" National ID: "+x.getID());
		//type1.setText(x.getType1());
		//type2.setText(x.getType2());
	}
	public void updateType1(Pokemon x) {
		type1.setText(" "+x.getType1());
	}
	public void updateType2(Pokemon x) {
		type2.setText(" "+x.getType2());
	}
	public void clearType2() {
		type2.setText("");
	}
	/**
	 * updates the JLabel's color based on the text
	 * @param x
	 */
	public void updateColor(String x,boolean monoType) {
		switch(x) {
		case "Grass": if(monoType==true) {
			updateLabelType("Grass",true);
		}	else {
			updateLabelType("Grass",false);
		}
			break;
		case "Fire": if(monoType==true) {
			updateLabelType("Fire",true);
		}	else {
			updateLabelType("Fire",false);
		}
			break;
		case "Water": if(monoType==true) {
			updateLabelType("Water",true);
		}	else {
			updateLabelType("Water",false);
		}
			break;
		case "Normal": if(monoType==true) {
			updateLabelType("Normal",true);
		}	else {
			updateLabelType("Normal",true);
		}
			break;
		case "Poison": if(monoType==true) {
			updateLabelType("Poison",true);
		}	else {
			updateLabelType("Poison",false);
		}
			break;
		case "Bug": if(monoType==true) {
			updateLabelType("Bug",true);
		}	else {
			updateLabelType("Bug",false);
		}
			break;
		case "Ghost": if(monoType==true) {
			updateLabelType("Ghost",true);
		}	else {
			updateLabelType("Ghost",false);
		}
			break;
		case "Fighting": if(monoType==true) {
			updateLabelType("Fighting",true);
		}	else {
			updateLabelType("Fighting",false);
		}
			break;
		case "Electric": if(monoType==true) {
			updateLabelType("Electric",true);
		}	else {
			updateLabelType("Electric",false);
		}
			break;
		case "Dragon": if(monoType==true) {
			updateLabelType("Dragon",true);
		}	else {
			updateLabelType("Dragon",false);
		}
			break;
		case "Ice": if(monoType==true) {
			updateLabelType("Ice",true);
		}	else {
			updateLabelType("Ice",false);
		}
			break;
		case "Ground": if(monoType==true) {
			updateLabelType("Ground",true);
		}	else {
			updateLabelType("Ground",false);
		}
			break;
		case "Rock": if(monoType==true) {
			updateLabelType("Rock",true);
		}	else {
			updateLabelType("Rock",false);
		}
			break;
		case "Steel": if(monoType==true) {
			updateLabelType("Steel",true);
		}	else {
			updateLabelType("Steel",false);
		}
			break;
		case "Flying": if(monoType==true) {
			updateLabelType("Flying",true);
		}	else {
			updateLabelType("Flying",false);
		}
			break;
		case "Psychic": if(monoType==true) {
			updateLabelType("Psychic",true);
		}	else {
			updateLabelType("Psychic",false);
		}
			break;
		}
	}
	public void resetColor() {
		type2.setIcon(null);
	}
}
