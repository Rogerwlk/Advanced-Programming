// Yahtzee.java
// Date created: 3/25/2017
// Author: Roger Wang
// NetID: rw794

package rw794;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Yahtzee layout is boarderLayout
// left side scoreBoard, center rollBoard
public class Yahtzee extends JFrame implements ActionListener, MouseListener
{
	JPanel scoreBoard = new JPanel();
	JPanel rollBoard = new JPanel();
	
	// set the font for all letters
	Font theFont = new Font("Courier", Font.BOLD, 20);
	
	// variables relating to scoreBoard
	// scoreBoard is 18*1 for 18 different scores
	
	// Each panel is 2*1. First line text, second line button.
	JPanel[] scorePanels = new JPanel[18];
	
	// score buttons for 18 score panels
	JButton[] scoreButtons = new JButton[18];
	// scores for each button
	int[] scores = new int[18];
	// record if the score button has been chosen
	boolean[] scorePressed = new boolean[18];

	// variables relating to rollBoard
	// rollBoard is 6*1. 5 rows for 5 dices. Last row for roll button
	
	// roll button
	JButton rollButton;

	// 5 dices
	Die[] dices = new Die[5];

	// variables relating to game procedure
	
	// number of rolls for each round
	int rollTimes;

	// number of pressed buttons
	// These five buttons are assumed to have been pressed:
	// uppersum, bonus, uppertotal, lowertotal, grandtotal
	// so the round is initialized to 5
	int round = 5;

	// six counters for the frequency of dice number i
	// Because the dice number range is 1-6,
	// there are six counters with index 1-6.
	int[] freq = new int[7];

	// counting the number of dices has been pressed
	int dicePressed;
	
	public static void main(String[] args)
	{
		new Yahtzee();
	}

	public Yahtzee()
	{
		initializeScore();
		initializeDice();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Yahtzee");
		setLayout(new BorderLayout());

		add(scoreBoard, BorderLayout.WEST);

		add(rollBoard, BorderLayout.CENTER);

		setSize(new Dimension(700, 1000));
		setVisible(true);
	}

	// initialize variables relating scoreBoard
	private void initializeScore()
	{
		scoreBoard.setLayout(new GridLayout(18, 1));

		for (int i = 0; i < 18; i++)
		{
			scores[i] = 0;
			scorePressed[i] = false;
			scorePanels[i] = new JPanel();
			scorePanels[i].setLayout(new BorderLayout());
			scoreButtons[i] = new JButton("0");
			scoreButtons[i].setFont(theFont);
			// disable all score buttons when the game starts
			scoreButtons[i].setEnabled(false);
			scoreButtons[i].addActionListener(this);
		}
		// The score buttons below do not need to be pressed
		scorePressed[6] = true;// uppersum
		scorePressed[7] = true;// bonus
		scorePressed[8] = true;// uppertotal
		scorePressed[16] = true;// lowertotal
		scorePressed[17] = true;// grandtotal

		// add hints for score buttons
		JLabel[] labels = new JLabel[18];
		labels[0] = new JLabel("Ones: Total of Ones");
		labels[1] = new JLabel("Twos: Total of Twos");
		labels[2] = new JLabel("Threes: Total of Threes");
		labels[3] = new JLabel("Fours: Total of Fours");
		labels[4] = new JLabel("Fives: Total of Fives");
		labels[5] = new JLabel("Sixes: Total of Sixes");
		labels[6] = new JLabel("Uppersum: Sum of the above");
		labels[7] = new JLabel("Bonus: If UpperSum > 62");
		labels[8] = new JLabel("Total: Total of upper section");
		labels[9] = new JLabel("Three of a kind");
		labels[10] = new JLabel("Four of a kind");
		labels[11] = new JLabel("Full House: Three of a kind + a pair");
		labels[12] = new JLabel("Small Straight: Sequence of 4");
		labels[13] = new JLabel("Large Straight: Sequence of 5");
		labels[14] = new JLabel("Yahtzee: Five of a kind");
		labels[15] = new JLabel("Chance: Total of all dices");
		labels[16] = new JLabel("Total: Total of lower section");
		labels[17] = new JLabel("Grand Total");
		
		for (int i = 0; i < 18; i++)
		{
			labels[i].setFont(theFont);
			scorePanels[i].add(labels[i], BorderLayout.CENTER);
			// add score buttons to each score panel
			scorePanels[i].add(scoreButtons[i], BorderLayout.EAST);
			// add each score panel to score board
			scoreBoard.add(scorePanels[i]);
		}
	}

	// initialize variables relating to rollBoard
	private void initializeDice()
	{
		dicePressed = 5;
		rollBoard.setLayout(new GridLayout(6, 1));
		rollBoard.setBackground(Color.green);
		for (int i = 0; i < 5; i++)
		{
			// size of dice is 100
			dices[i] = new Die(100);

			// when the game starts all dice buttons are disabled
			dices[i].setEnabled(false);
			dices[i].addMouseListener(this);
			// put dices into a panel
			JPanel temp = new JPanel();
			temp.setBackground(Color.green);
			temp.add(dices[i]);
			// add panel into the board
			rollBoard.add(temp);
		}
		// initialize roll button and times
		rollTimes = 0;
		rollButton = new JButton("Roll " + rollTimes);
		rollButton.addActionListener(this);
		rollButton.setFont(theFont);
		// when the game starts, only roll button is enabled.
		rollBoard.add(rollButton);
	}

	// called when the user press the roll button
	private void roll()
	{
		Random ran = new Random();

		// after first rolling, all diceButtons are enabled and no dice pressed
		if (rollTimes == 0)
		{
			for (int i = 0; i < 5; i++)
			{
				dices[i].setEnabled(true);
			}
			dicePressed = 0;
			
		}
		// not disabled button means the user want to roll those dices again
		// disabled button means the user has chosen those dices
		// for every enabled diceButton, assign a new random number as rolling
		for (int i = 0; i < 5; i++)
		{
			if (dices[i].isEnabled())
			{
				dices[i].setVal(ran.nextInt(6) + 1);
			}
		}

		// update the scoreBoard for each rolling
		updateScore();

		// increment roll times
		rollTimes++;
		// update text to show the times the player has rolled.
		rollButton.setText("Roll " + rollTimes);

		// if the user has rolled three times, disabled the roll button
		// so the user can't roll anymore
		if (rollTimes == 3)
		{
			rollButton.setEnabled(false);
		}
	}

	// update the frequency for each dice number using 6 counters
	private void updateFrequency()
	{
		// range of dice number is 1-6
		// each counter record the frequency of each dice number
		for (int i = 1; i < 7; i++)
		{
			freq[i] = 0;
		}
		
		// check all five dices
		for (int i = 0; i < 5; i++)
		{
			freq[dices[i].getVal()]++;
		}
	}

	// update the scoreBoard
	private void updateScore()
	{
		updateFrequency();
		// total of 18 score buttons
		for (int i = 0; i < 18; i++)
		{
			// only update the score button
			// if it hasn't been chosen by previous rounds
			if (!scorePressed[i])
			{
				switch (i)
				{
				case 0:// aces
					scores[0] = freq[1] * 1;
					break;
				case 1:// twos
					scores[1] = freq[2] * 2;
					break;
				case 2:// threes
					scores[2] = freq[3] * 3;
					break;
				case 3:// fours
					scores[3] = freq[4] * 4;
					break;
				case 4:// fives
					scores[4] = freq[5] * 5;
					break;
				case 5:// sixes
					scores[5] = freq[6] * 6;
					break;
				case 9: // three of a kind
					scores[9] = 0;
					for (int j = 1; j < 7; j++)
					{
						// check frequency
						if (freq[j] >= 3)
						{
							// sum of all dices
							scores[9] = dices[0].getVal() + dices[1].getVal() + dices[2].getVal() + dices[3].getVal()
									+ dices[4].getVal();
							break;
						}
					}
					break;
				case 10: // four of a kind
					scores[10] = 0;
					for (int j = 1; j < 7; j++)
					{
						// check frequency
						if (freq[j] >= 4)
						{
							// sum of all dices
							scores[10] = dices[0].getVal() + dices[1].getVal() + dices[2].getVal() + dices[3].getVal()
									+ dices[4].getVal();
							break;
						}
					}
					break;
				case 11:// full house
					boolean threes = false, twos = false;
					for (int j = 1; j < 7; j++)
					{
						if (freq[j] == 3)
						{
							threes = true;
						}
						else if (freq[j] == 2)
						{
							twos = true;
						}
					}
					if (threes && twos)
						scores[11] = 25;
					else
						scores[11] = 0;
					break;
				case 12:// small straight
					// 1-2-3-4 or 2-3-4-5 or 3-4-5-6
					if ((freq[1] >= 1 && freq[2] >= 1 && freq[3] >= 1 && freq[4] >= 1)
							|| (freq[5] >= 1 && freq[2] >= 1 && freq[3] >= 1 && freq[4] >= 1)
							|| (freq[5] >= 1 && freq[6] >= 1 && freq[3] >= 1 && freq[4] >= 1))
					{
						scores[12] = 30;
					}
					else
					{
						scores[12] = 0;
					}
					break;
				case 13:// large straight
					// 1-2-3-4-5 or 2-3-4-5-6
					if ((freq[1] >= 1 && freq[2] >= 1 && freq[3] >= 1 && freq[4] >= 1 && freq[5] >= 1)
							|| (freq[2] >= 1 && freq[3] >= 1 && freq[4] >= 1 && freq[5] >= 1 && freq[6] >= 1))
					{
						scores[13] = 40;
					}
					else
					{
						scores[13] = 0;
					}
					break;
				case 14:// yahtzee
					scores[14] = 0;
					for (int j = 1; j < 7; j++)
					{
						// check frequency
						if (freq[j] == 5)
						{
							scores[14] = 50;
							break;
						}
					}
					break;
				case 15:// chance
					// sum of all dices
					scores[15] = dices[0].getVal() + dices[1].getVal() + dices[2].getVal() + dices[3].getVal()
							+ dices[4].getVal();
					break;
				}

				scoreButtons[i].setText("" + scores[i]);
			}
		}
	}

	// input is the index of the dice button pressed
	// called when a dice button is pressed
	private void chooseDice(int n)
	{
		if (dices[n].isEnabled())
		{
			// disabled the diceButton as chosen
			dices[n].setEnabled(false);
			dicePressed++;
		}
		else
		{
			dices[n].setEnabled(true);
			dicePressed--;
		}

		if (dicePressed == 5)
		{
			// enable not chosen score buttons by previous rounds
			for (int i = 0; i < 18; i++)
			{
				if (!scorePressed[i])
				{
					scoreButtons[i].setEnabled(true);
				}
			}
		}
		else
		{
			for (int i = 0; i < 18; i++)
			{
				if (!scorePressed[i])
				{
					scoreButtons[i].setEnabled(false);
				}
			}
		}
	}

	// input is the index of the score button pressed
	// called when the user press a score button
	private void chooseScore(int n)
	{
		// disable all score buttons
		for (int i = 0; i < 18; i++)
		{
			scoreButtons[i].setEnabled(false);
		}
		// record the score button as chosen in the round
		scorePressed[n] = true;

		// update total score and bonus
		
		// upperSum for scores[6]
		scores[6] = 0;
		for (int i = 0; i < 6; i++)
		{
			if (scorePressed[i])
			{
				scores[6] += scores[i];
			}
		}
		scoreButtons[6].setText("" + scores[6]);

		// bonus for scores[7]
		if (scores[6] > 62)
			scores[7] = 35;
		else
			scores[7] = 0;

		scoreButtons[7].setText("" + scores[7]);
		
		// upper section total for scores[8]
		scores[8] = scores[6] + scores[7];
		scoreButtons[8].setText("" + scores[8]);
		
		// lowerSum for scores[16]
		scores[16] = 0;
		for (int i = 9; i < 16; i++)
		{
			if (scorePressed[i])
			{
				scores[16] += scores[i];
			}
		}
		scoreButtons[16].setText("" + scores[16]);

		// GrandTotal for scores[17]
		scores[17] = scores[8] + scores[16];
		scoreButtons[17].setText("" + scores[17]);

		// increment round
		round++;
		
		// game is not over
		if (round < 18)
		{
			// refresh rollTimes
			rollTimes = 0;
			// update text to show the times the player has rolled.
			rollButton.setText("Roll " + rollTimes);

			// enable rollButton
			rollButton.setEnabled(true);
		}
	}

	// perform action for any pressed buttons
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// press roll button
		if (e.getSource() == rollButton)
		{
			roll();
		}
		else if (e.getSource() == scoreButtons[0])// ones
		{
			chooseScore(0);
		}
		else if (e.getSource() == scoreButtons[1])// twos
		{
			chooseScore(1);
		}
		else if (e.getSource() == scoreButtons[2])// threes
		{
			chooseScore(2);
		}
		else if (e.getSource() == scoreButtons[3])// fours
		{
			chooseScore(3);
		}
		else if (e.getSource() == scoreButtons[4])// fives
		{
			chooseScore(4);
		}
		else if (e.getSource() == scoreButtons[5])// sixes
		{
			chooseScore(5);
		}
		else if (e.getSource() == scoreButtons[9])// three of a kind
		{
			chooseScore(9);
		}
		else if (e.getSource() == scoreButtons[10])// four of a kind
		{
			chooseScore(10);
		}
		else if (e.getSource() == scoreButtons[11])// full house
		{
			chooseScore(11);
		}
		else if (e.getSource() == scoreButtons[12])// small straight
		{
			chooseScore(12);
		}
		else if (e.getSource() == scoreButtons[13])// large straight
		{
			chooseScore(13);
		}
		else if (e.getSource() == scoreButtons[14])// yahtzee
		{
			chooseScore(14);
		}
		else if (e.getSource() == scoreButtons[15])// chance
		{
			chooseScore(15);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// Before you do any rolling, you can't choose any dices.
		if (e.getSource() == dices[0] && rollTimes > 0)
		{
			chooseDice(0);
		}
		else if (e.getSource() == dices[1] && rollTimes > 0)
		{
			chooseDice(1);
		}
		else if (e.getSource() == dices[2] && rollTimes > 0)
		{
			chooseDice(2);
		}
		else if (e.getSource() == dices[3] && rollTimes > 0)
		{
			chooseDice(3);
		}
		else if (e.getSource() == dices[4] && rollTimes > 0)
		{
			chooseDice(4);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}
}
