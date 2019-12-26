import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

// Brian Blakely for CPS 132 University of Dayton 

@SuppressWarnings("serial")
public class TicTacToe extends JPanel implements ActionListener {
    // A Vector of JButton objects that form the tic-tac-toe board.
    private Vector<JButton> buttons;
    
    // Text fields to display X wins, O wins, and drawsField
    private JTextField xWinsField, oWinsField, drawsField;
    
    // Button to request a new game;
    private JButton newGameBtn;
    
    // If xTurn is true, the next move will be player X. If xTurn is false, then 
    // the next move will be player O.
    private boolean xTurn;
    
    // Field to be used to track the number of moves. Can be useful in determining a draw.
    private int movesTaken;
    
    // Fields to keep track of the number of wins and draws.
    private int xWins, oWins, draws;

    public TicTacToe() {
        int i;
        JButton btn;

        // Arrange the GUI components.
        setPreferredSize (new Dimension (551, 446));

        // Bottom panel wil track wins and losses.
        JPanel bottomPanel = new JPanel(new FlowLayout());
        setLayout (new BorderLayout());
        bottomPanel.add(new JLabel("    X wins:"));
        xWinsField = new JTextField(3);
        bottomPanel.add(xWinsField);
        bottomPanel.add(new JLabel("    O wins:"));
        oWinsField = new JTextField(3);
        bottomPanel.add(oWinsField);
        bottomPanel.add(new JLabel("    draws:"));
        drawsField = new JTextField(3);
        bottomPanel.add(drawsField);
        add(bottomPanel,"South");
        xWinsField.setEditable(false);
        oWinsField.setEditable(false);
        drawsField.setEditable(false);
        
        // New game button will be between the top and bottom panels
        newGameBtn = new JButton("New Game");
        newGameBtn.setFont(new Font("Arial", Font.BOLD, 24));
        newGameBtn.setForeground(Color.BLUE);
        newGameBtn.addActionListener(this);
        add(newGameBtn,"Center");

        // Top panel will hold the game buttons.
        JPanel topPanel = new JPanel(new GridLayout(3,3));
        
        // Create the Vector object to hold the buttons.
        buttons = new Vector<JButton>();
        
        // Create, initialize, and add each button.
        // Initially, the buttons have a label indicating their position.
        // (btn0 is labeled )
        for (i=0; i<9; i++)
        {
            btn = new JButton(""+i);
            btn.setFont(new Font("Arial", Font.BOLD, 96));
            btn.addActionListener(this);
            topPanel.add(btn);
            buttons.add(btn);
        }
        add(topPanel,"North");
        
        // Player X makes the first move.
        xTurn = true;
        // Initially, no moves have been taken.
        movesTaken = 0;
        // Initially no one has won any games.
        xWins = oWins = draws = 0;
        updateWinsDisplay();
        }
        
    /**
     * Display the current values of xWinsField, oWinsField, and drawsField in the
     * appropriate fields.
     */
    private void updateWinsDisplay()
    {
        xWinsField.setText(""+xWins);
        oWinsField.setText(""+oWins);
        drawsField.setText(""+draws);
    }
    
    /**
     * A button has been pushed.
     */
    public void actionPerformed(ActionEvent evt)
    {
        JButton button;
        String text;
        // The variable button will be a reference to the button that has 
        // been pushed. Note that this may be one of the nine buttons of the
        // tic-tac-toe board, or it may be the "New Game" button.
        button = (JButton)evt.getSource();
        text = button.getText();
        
        if (!text.equals("X") && !text.equals("O") && !text.equals("New Game"))
        {
            // Process a move.
            if (xTurn)          // X player turn
            {
                button.setText("X");
                button.setForeground(Color.blue);       // challenge part
            }
            else                // O player turn
            {
                button.setText("O");
                button.setForeground(Color.red);        // challenge part
            }
            // Check to see if move caused a win.
            if (checkWin())     // Has the game been won?
            {
                if (xTurn)      // If so, credit the correct player.
                    xWins++;
                else
                    oWins++;
                gameOver();
            }
            xTurn = !xTurn;
            movesTaken++;
            if (!checkWin() && movesTaken == 9)
            {
                draws++;
                gameOver();
            }
        }
        if (text.equals("New Game"))
        {
            startNewGame();
        }
    }
    
    /**
     * A game has ended. Do the following:
     * 
     *    - disable each button
     *    - update the display of wins and draws
     */
    public void gameOver()
    {
        int i;
        JButton btn;
        
        // Loop for each button.
        for (i=0; i<9; i++)
        {
            btn = buttons.get(i);
            btn.setEnabled(false);       // enable the button
        }
        updateWinsDisplay();
    }
    
    /**
     * Start a new game. This consists of:
     * 
     *    - enabling each button
     *    - re-setting the text on each button
     *    - resetting movesTaken to 0
     */
    public void startNewGame()
    {
        int i;
        JButton btn;
        
        // Loop for each button.
        for (i=0; i<9; i++)
        {
            btn = buttons.get(i);
            btn.setText(""+i);              // set the text
            btn.setEnabled(true);           // enable the button
            btn.setForeground(Color.black); // challenge part
        }
        movesTaken = 0;
    }
    
    
    /**
     * Given three button positions (in the vector "buttons"), return true if all three
     * of the buttons contain the same text. Otherwise return false.
     */
    public boolean allSame(int p1, int p2, int p3)
    {
        JButton btn;
        String text1, text2, text3;
        
        // Get the text from each of the three buttons.
        btn = buttons.get(p1);
        text1 = btn.getText();
        btn = buttons.get(p2);
        text2 = btn.getText();
        btn = buttons.get(p3);
        text3 = btn.getText();
        // Return true only is all three buttons are true.
        return (text1.equals(text2) && text2.equals(text3));
    }
    
    /**
     * Check all eight possible wins (3 rows, three columns, two diagonals). Return
     * true as soon as any one is found to be a win. Otherwise return false.
     */
    public boolean checkWin()
    {
        // Return true if any of the possible wins are true.
        return (allSame(0,1,2) || allSame(3,4,5) || allSame(6,7,8) || allSame(0,3,6) ||
                allSame(1,4,7) || allSame(2,5,8) || allSame(0,4,8) || allSame(6,4,2));
    }
    
     /**
     * Wait for approximately the number of milliseconds provided 
     * before doing anything else. 
     */
    public static void wait_a_little(int milliseconds) 
    {
        try {Thread.sleep(milliseconds);}
        catch (Exception e) {}
    }
    
    public static void main (String[] args) {
        JFrame frame = new JFrame ("MyPanel");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new TicTacToe());
        frame.pack();
        frame.setVisible (true);
    }
}
