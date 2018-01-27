/**
 * Dylan Hudson - u1652664
 * Description: The main class for the Assignment 2 Cinema, this controls all of the variables and
 * methods which are used in the project. It allows a user to select films from a list, see their
 * prices as well as the screen they are on and what day.
 * It also has snacks and drinks which can be purchased too from lists, it allows the user to review their
 * receipt and then to confirm the purchase.
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.*;

import java.text.DecimalFormat;

public class CinemaGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel moviesPanel;			//Holds all the movies
	private JPanel buttonsPanel;		//Has add/remove/checkout buttons
	private JPanel receiptPanel;		//To hold movies added by user
	private JPanel bannerPanel;			//Banner panel
	private JPanel drinkSnackPanel;		//Holds the drink and snack buttons

	private JSplitPane removeDrinkSnack; 		//Holds the remove drink and snack buttons
	private JSplitPane checkOutRemoveMovie; 	//Holds the checkout and remove buttons

	private JList<?> jListList;				//List with all movie names
	private JList<?> drinksList;			//List with all drink names
	private JList<?> snacksList;			//List with all snack names
	private JList<Object> selectedList;		//List in the receipt section

	private JButton addSelected;			//Adds movie to receipt
	private JButton addDrink;				//Adds drink to receipt
	private JButton addSnack;				//Adds snack to receipt
	private JButton checkOut;				//Adds all the item prices together
	private JButton removeSelected;			//Removes a movie from receipt
	private JButton removeDrink;			//Removes a drink from receipt
	private JButton removeSnack;			//Removes a snack from receipt

	private CinemaInfo cinemaInfo = new CinemaInfo(); 			//MovieInfo object
	@SuppressWarnings("static-access")
	private String[] movieNames = cinemaInfo.getMovieNames();	//Array that Holds all movie names
	@SuppressWarnings("static-access")
	private double[] moviePrices = cinemaInfo.getMoviePrices();	//Array that holds all movie prices

	private DrinkInfo drinkInfo = new DrinkInfo(); 				//DrinkInfo object
	@SuppressWarnings("static-access")
	private String[] drinkNames = drinkInfo.getDrinkNames();	//Array that Holds all drink names
	@SuppressWarnings("static-access")
	private double[] drinkPrices = drinkInfo.getDrinkPrices();	//Array that holds all drink prices

	private SnackInfo snackInfo = new SnackInfo(); 				//SnackInfo object
	@SuppressWarnings("static-access")
	private String[] snackNames = snackInfo.getSnackNames();	//Array that Holds all snack names
	@SuppressWarnings("static-access")
	private double[] snackPrices = snackInfo.getSnackPrices();	//Array that holds all snack prices

	private JScrollPane scrollPane;		//Holds available movies list
	private JScrollPane scrollPane2;	//Holds selected movies list
	private JScrollPane scrollPane3;	//Holds selected drinks list
	private JScrollPane scrollPane4;	//Holds selected snacks list

	private JLabel titleLabel; 			//Application Panel title
	private JLabel panelTitle;			//Movie Panel title
	private JLabel receiptTitle;		//Receipt Panel title
	private JLabel drinkTitle;			//Drink Panel title
	private JLabel snackTitle;			//Snack Panel title

	private int element = -1;			//control variable
	private int count;					//Control variable

	private double total;				//Total of prices
	private double moviePrice;			//Hold movie prices
	private double drinkPrice;			//Hold drink prices
	private double snackPrice;			//Hold snack prices

	private JTextField ticketRemaining; 	//Textfield where tickets stored
	public int ticketsRemaining = 50;		//Value for the tickets remaining

	private ListModel<Object> receipt;				//List model for receipt list
	private DefaultListModel<Object> receiptDFM;

	private DecimalFormat money;		//Money format
	private Object selectedMovieName; 	//Selected movie
	private Object selectedDrinkName; 	//Selected movie
	private Object selectedSnackName; 	//Selected movie

	private int selectedIndex;				//Index selected among available movies
	private int selectedIndexDrink;			//Index selected among available drinks
	private int selectedIndexSnack;			//Index selected among available snacks

	private int index;						//Int that holds selected index.
	private int indexDrink;					//Int that holds selected index.
	private int indexSnack;					//Int that holds selected index.


	/*Constructor
	 * CinemaGUI - Builds a GUI with multiple panels as well as the remove drink and remove snack buttons
	 */

	public CinemaGUI() throws IOException{
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(900, 350));
		//Title of GUI
		setTitle("Assignment 2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setSize(1600, 800);

		//BuildPanels
		buildMoviesPanel();
		buildButtonsPanel();
		buildReceiptPanel();
		buildBannerPanel();
		buildDrinkSnackPanel();

		//Add panels to GUI frame
		getContentPane().add(bannerPanel,BorderLayout.NORTH);

		titleLabel = new JLabel("Welcome to HudCinema");
		titleLabel.setBackground(Color.GRAY);
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setFont(new Font("Lato", Font.BOLD, 28));
		titleLabel.setBorder(null);
		bannerPanel.add(titleLabel);


		getContentPane().add(moviesPanel, BorderLayout.WEST);
		getContentPane().add(buttonsPanel, BorderLayout.CENTER);

		removeDrinkSnack = new JSplitPane();
		removeDrinkSnack.setBackground(Color.LIGHT_GRAY);
		removeDrinkSnack.setEnabled(false);
		buttonsPanel.add(removeDrinkSnack);

		removeDrink = new JButton("Remove Drink");
		removeDrink.setForeground(Color.WHITE);
		removeDrink.setBackground(Color.BLACK);
		removeDrink.setFont(new Font("Lato", Font.PLAIN, 11));
		removeDrink.setMaximumSize(new Dimension(120, 23));
		removeDrink.setMinimumSize(new Dimension(120, 23));
		removeDrink.setPreferredSize(new Dimension(120, 23));
		removeDrink.setSize(new Dimension(120, 23));
		removeDrinkSnack.setLeftComponent(removeDrink);
		removeSnack = new JButton("Remove Snack");
		removeSnack.setForeground(Color.WHITE);
		removeSnack.setBackground(Color.BLACK);
		removeSnack.setFont(new Font("Lato", Font.PLAIN, 11));
		removeSnack.setMaximumSize(new Dimension(120, 23));
		removeSnack.setMinimumSize(new Dimension(120, 23));
		removeSnack.setPreferredSize(new Dimension(120, 23));
		removeSnack.setSize(new Dimension(120, 23));
		removeDrinkSnack.setRightComponent(removeSnack);

		//Ticket Counter
		ticketRemaining = new JTextField();
		ticketRemaining.setFont(new Font("Lato Light", Font.PLAIN, 11));
		ticketRemaining.setBackground(Color.GRAY);
		buttonsPanel.add(ticketRemaining);
		ticketRemaining.setEditable(false);
		ticketRemaining.setBorder(null);
		ticketRemaining.setColumns(10);
		ticketRemaining.setText("Tickets Remaining: " + ticketsRemaining);

		getContentPane().add(receiptPanel, BorderLayout.EAST);
		getContentPane().add(drinkSnackPanel, BorderLayout.SOUTH);

		removeDrink.addActionListener(new RemoveDrinkListener());
		removeSnack.addActionListener(new RemoveSnackListener());

		//set visibility
		setVisible(true);
		pack();
	}


	//METHODS
	/*
	 *buildMoviesPanel() - Builds panel containing a JList/ScrollPane
	 */

	public void buildMoviesPanel(){

		//Create panel to hold list of movies
		moviesPanel = new JPanel();
		moviesPanel.setBackground(UIManager.getColor("Button.light"));

		//Set Panel layout
		moviesPanel.setLayout(new BorderLayout());
		jListList = new JList<Object>(movieNames);
		jListList.setForeground(Color.WHITE);
		jListList.setBackground(Color.DARK_GRAY);

		//Set selection preference
		jListList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		//Visible movie names
		jListList.setVisibleRowCount(5);

		//Create scroll pane containing movie list
		scrollPane = new JScrollPane(jListList);
		scrollPane.setBackground(Color.LIGHT_GRAY);

		//JLabel/Panel title
		panelTitle = new JLabel("Available Movies");
		panelTitle.setFont(new Font("Lato Semibold", Font.PLAIN, 11));
		panelTitle.setBackground(UIManager.getColor("Button.light"));

		//Add JLabel and scroll to panel
		moviesPanel.add(panelTitle, BorderLayout.NORTH);
		moviesPanel.add(scrollPane);

		//Create Buttons
		addSelected = new JButton("Add Movie");
		addSelected.setForeground(Color.WHITE);
		addSelected.setBackground(Color.BLACK);
		addSelected.setFont(new Font("Lato Semibold", Font.PLAIN, 11));
		scrollPane.setColumnHeaderView(addSelected);

		//Add Listeners
		addSelected.addActionListener(new AddButtonListener());

		//Set text to movie title
		jListList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

				if (ticketsRemaining >= 1){
					ticketRemaining.setText(jListList.getSelectedValue().toString() + " - " + ticketsRemaining + " Tickets Left");
					ticketRemaining.update(ticketRemaining.getGraphics());
				} else {
					JOptionPane.showMessageDialog(new JFrame(),
							("There are no tickets left"), "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}

				//Disable and enable buttons
				addSelected.setEnabled(true);
				removeDrink.setEnabled(false);
				removeSnack.setEnabled(false);
				removeSelected.setEnabled(false);
				addDrink.setEnabled(false);
				addSnack.setEnabled(false);
			}
		});
		return;
	}


	/*
	 * buildButtonsPanel - builds panel containing the remove and print buttons
	 */
	public void buildButtonsPanel(){

		//Create panel to hold buttons
		buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.LIGHT_GRAY);

		//Set Layout
		buttonsPanel.setLayout(new GridLayout(3,1));

		checkOutRemoveMovie = new JSplitPane();
		checkOutRemoveMovie.setBackground(Color.LIGHT_GRAY);
		checkOutRemoveMovie.setEnabled(false);
		buttonsPanel.add(checkOutRemoveMovie);

		removeSelected = new JButton("Remove Movie");
		removeSelected.setForeground(Color.WHITE);
		removeSelected.setBackground(Color.BLACK);
		removeSelected.setFont(new Font("Lato", Font.PLAIN, 11));
		removeSelected.setMaximumSize(new Dimension(120, 23));
		removeSelected.setMinimumSize(new Dimension(120, 23));
		removeSelected.setPreferredSize(new Dimension(120, 23));
		removeSelected.setSize(new Dimension(120, 23));

		checkOutRemoveMovie.setRightComponent(removeSelected);
		checkOut = new JButton("Purchase");
		checkOut.setForeground(Color.WHITE);
		checkOut.setBackground(Color.BLACK);
		checkOut.setFont(new Font("Lato", Font.PLAIN, 11));
		checkOut.setMinimumSize(new Dimension(120, 23));
		checkOut.setMaximumSize(new Dimension(120, 23));
		checkOut.setSize(new Dimension(120, 23));
		checkOut.setPreferredSize(new Dimension(120, 23));
		checkOutRemoveMovie.setLeftComponent(checkOut);

		checkOut.addActionListener(new CheckOutButtonListener());
		removeSelected.addActionListener(new RemoveButtonListener());
	}


	/*
	 * buildReceiptPanel builds panel containing JList/Scroll pane
	 */

	public void buildReceiptPanel(){
		//Create panel
		receiptPanel = new JPanel();

		//Set panel layout
		receiptPanel.setLayout(new BorderLayout());

		//Create receipt list
		selectedList = new JList<Object>();
		selectedList.setForeground(Color.WHITE);
		selectedList.setBackground(Color.DARK_GRAY);

		//Set row visibility
		selectedList.setVisibleRowCount(5);

		//Create ScrollPane containing selected list items
		scrollPane2 = new JScrollPane(selectedList);

		//JLabel/Panel title
		receiptTitle = new JLabel("Reciept ");
		receiptTitle.setBackground(Color.WHITE);
		receiptTitle.setFont(new Font("Lato Semibold", Font.PLAIN, 11));

		//Add JLabel and scroll pane to panel
		receiptPanel.add(receiptTitle, BorderLayout.NORTH);
		receiptPanel.add(scrollPane2);

		//Enable and disable buttons for the receipt
		selectedList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//Disable and enable buttons
				removeSnack.setEnabled(true);
				removeDrink.setEnabled(true);
				removeSelected.setEnabled(true);
				addSelected.setEnabled(false);
				addSnack.setEnabled(false);
				addDrink.setEnabled(false);
			}
		});
	}


	/*
	 * buildBannerPanel - builds panel containing banner for GUI
	 */
	public void buildBannerPanel(){
		//Create panel
		bannerPanel = new JPanel();
		bannerPanel.setBackground(Color.GRAY);

		//Set Border layout
		getContentPane().setLayout(new BorderLayout());
	}


	/*
	 * buildDrinkSnackPanel - builds panel containing the drink and snack lists
	 */

	public void buildDrinkSnackPanel(){
		//Create panel
		drinkSnackPanel = new JPanel();

		//Set Border layout
		drinkSnackPanel.setLayout(new GridLayout(1, 3 ,5,5));

		//Create the drink list, add ScrollPane and layout
		scrollPane3 = new JScrollPane();
		scrollPane3.setBackground(Color.WHITE);
		drinkSnackPanel.add(scrollPane3);
		drinksList = new JList<Object>(drinkNames);
		drinksList.setForeground(Color.WHITE);
		drinksList.setBackground(Color.DARK_GRAY);

		//Add the title to the drink list
		scrollPane3.setViewportView(drinksList);
		drinkTitle = new JLabel("Available Drinks");
		drinkTitle.setFont(new Font("Leelawadee UI", Font.PLAIN, 11));
		drinkTitle.setBackground(Color.WHITE);
		scrollPane3.setColumnHeaderView(drinkTitle);

		//Set selection preference for drink list
		drinksList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		drinksList.setVisibleRowCount(5);

		addDrink = new JButton("Add Drink");
		addDrink.setForeground(Color.WHITE);
		addDrink.setBackground(Color.BLACK);
		addDrink.setFont(new Font("Lato Semibold", Font.PLAIN, 11));
		scrollPane3.setRowHeaderView(addDrink);

		//Create the snack list, set view and title
		scrollPane4 = new JScrollPane();
		scrollPane4.setBackground(Color.WHITE);
		drinkSnackPanel.add(scrollPane4);
		snacksList = new JList<Object>(snackNames);
		snacksList.setForeground(Color.WHITE);
		snacksList.setBackground(Color.DARK_GRAY);

		//Add the title to the snack list
		scrollPane4.setViewportView(snacksList);
		snackTitle = new JLabel("Available Snacks");
		snackTitle.setFont(new Font("Leelawadee UI", Font.PLAIN, 11));
		snackTitle.setBackground(Color.WHITE);

		//Set selection preference for the snack list
		scrollPane4.setColumnHeaderView(snackTitle);
		snacksList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		snacksList.setVisibleRowCount(5);

		addSnack = new JButton("Add Snack");
		addSnack.setForeground(Color.WHITE);
		addSnack.setBackground(Color.BLACK);
		addSnack.setFont(new Font("Lato Semibold", Font.PLAIN, 11));
		scrollPane4.setRowHeaderView(addSnack);

		addDrink.addActionListener(new AddDrinkListener());
		addSnack.addActionListener(new AddSnackListener());

		//Enable and disable buttons for the drinksList and snacksList
		drinksList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//Disable and enable buttons
				addDrink.setEnabled(true);
				removeSelected.setEnabled(false);
				removeSnack.setEnabled(false);
				removeDrink.setEnabled(false);
				addSelected.setEnabled(false);
				addSnack.setEnabled(false);
			}
		});

		snacksList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				//Disable and enable buttons
				addSnack.setEnabled(true);
				removeSelected.setEnabled(false);
				removeSnack.setEnabled(false);
				removeDrink.setEnabled(false);
				addSelected.setEnabled(false);
				addDrink.setEnabled(false);
			}
		});
		return;
	}


	//ACTION LISTENERS
	/*
	 * AddButtonListener - adds selected item to receipt upon selection
	 */

	public class AddButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			selectedIndex = jListList.getSelectedIndex();
			selectedMovieName = jListList.getSelectedValue();

			jListList.getModel();
			receipt = selectedList.getModel();

			receiptDFM = new DefaultListModel<Object>();

			for(count=0; count<receipt.getSize(); count++){
				receiptDFM.addElement(receipt.getElementAt(count));
			}

			if(element == -1)
				moviePrice += moviePrices[selectedIndex];
			else
				moviePrice += moviePrices[element];


			receiptDFM.addElement(selectedMovieName);
			selectedList.setModel(receiptDFM);
			ticketsRemaining = ticketsRemaining - 1;

		}
	}


	public class AddDrinkListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			selectedIndexDrink = drinksList.getSelectedIndex();
			selectedDrinkName = drinksList.getSelectedValue();

			drinksList.getModel();
			receipt = selectedList.getModel();

			receiptDFM = new DefaultListModel<Object>();


			for(count=0; count<receipt.getSize(); count++){
				receiptDFM.addElement(receipt.getElementAt(count));
			}

			if(element == -1)
				drinkPrice += drinkPrices[selectedIndexDrink];
			else
				drinkPrice += drinkPrices[element];

			receiptDFM.addElement(selectedDrinkName);
			selectedList.setModel(receiptDFM);

		}
	}


	public class AddSnackListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			selectedIndexSnack = snacksList.getSelectedIndex();
			selectedSnackName = snacksList.getSelectedValue();

			snacksList.getModel();
			receipt = selectedList.getModel();

			receiptDFM = new DefaultListModel<Object>();


			for(count=0; count<receipt.getSize(); count++){
				receiptDFM.addElement(receipt.getElementAt(count));
			}

			if(element == -1)
				snackPrice += snackPrices[selectedIndexSnack];
			else
				snackPrice += snackPrices[element];

			receiptDFM.addElement(selectedSnackName);
			selectedList.setModel(receiptDFM);

		}
	}


	/*
	 * RemoveButtonListener - Removes selected item from receipt upon selection
	 */

	public class RemoveButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			index = selectedList.getSelectedIndex();
			((DefaultListModel<Object>)selectedList.getModel()).remove(index);

			if(element == -1)
				if(moviePrices[selectedIndex] <= moviePrice)
					moviePrice -= (moviePrices[selectedIndex]);
				else
					moviePrice = (moviePrices[index]) - moviePrice;
			else
				if(moviePrices[element] <= moviePrice)
					moviePrice -= (moviePrices[element]);
				else
					moviePrice = (moviePrices[index]) - moviePrice;

			ticketsRemaining = ticketsRemaining + 1;
		}
	}


	public class RemoveDrinkListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			indexDrink = selectedList.getSelectedIndex();
			((DefaultListModel<Object>)selectedList.getModel()).remove(indexDrink);

			if(element == -1)
				if(drinkPrices[selectedIndexDrink] <= drinkPrice)
					drinkPrice -= (drinkPrices[selectedIndexDrink]);
				else
					drinkPrice = (drinkPrices[indexDrink]) - drinkPrice;
			else
				if(drinkPrices[element] <= drinkPrice)
					drinkPrice -= (drinkPrices[element]);
				else
					drinkPrice = (drinkPrices[indexDrink]) - drinkPrice;
		}
	}


	public class RemoveSnackListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			indexSnack = selectedList.getSelectedIndex();
			((DefaultListModel<Object>)selectedList.getModel()).remove(indexSnack);

			if(element == -1)
				if(snackPrices[selectedIndexSnack] <= snackPrice)
					snackPrice -= (snackPrices[selectedIndexSnack]);
				else
					snackPrice = (snackPrices[indexSnack]) - snackPrice;
			else
				if(snackPrices[element] <= snackPrice)
					snackPrice -= (snackPrices[element]);
				else
					snackPrice = (snackPrices[indexSnack]) - snackPrice;
		}
	}


	/*
	 * CheckOutButtonListener - Calculates total and displays it to user
	 */

	public class CheckOutButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			//Money formatting and variables
			money = new DecimalFormat("#,##0.00");
			total = (moviePrice + snackPrice + drinkPrice);
			DefaultListModel<Object> receiptItems = (receiptDFM);
			double totalMoviePrice = (moviePrice);
			double totalDrinkPrice = (drinkPrice);
			double totalSnackPrice = (snackPrice);
			String okText = "Pay with cash";

			//Change the text for the OptionPane
			UIManager.put("OptionPane.okButtonText", okText);

			//Receipt information
			String receiptMessage = ("Total:£" + (money.format(total) + "\nMovie Prices:£" + (money.format(totalMoviePrice)) +
					"\nDrink Prices:£" + (money.format(totalDrinkPrice)) + "\nSnack Prices:£" + (money.format(totalSnackPrice)) 
					+ "\nWhat you have selected to buy:" + receiptItems));


			//Check if there is anything in cart or not
			if (receiptDFM != null){
				JOptionPane.showMessageDialog(new JFrame(),
						(receiptMessage), "Receipt",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(new JFrame(),
						("Please select at least one item to buy"), "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static void main(String[]args) throws IOException{
		new CinemaGUI();
	}
}