// Die.java
// Date created: 3/25/2017
// Author: Roger Wang
// NetID: rw794

package rw794;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Die extends JPanel implements MouseListener
{
	boolean pressed = true;
	int val;
	int x, y;
	int gap;
	int r;

	// test
	// public static void main(String args[])
	// {
	// JFrame driver = new JFrame();
	// driver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// driver.setLayout(new FlowLayout());
	// driver.add(new Die(100));
	// driver.setSize(200, 200);
	// driver.setVisible(true);
	// }

	public Die(int s)
	{
		this.setPreferredSize(new Dimension(s, s));
		this.setBackground(Color.gray);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		this.addMouseListener(this);
		// val is the value of dices
		// I divide the dice showing into 3*3 grid.
		// x is the drawing position of first column
		// y is the drawing position of first row
		// gap is the distance between the grid
		// r is the radius of dots
		val = 6;
		x = 5 + s / 20;
		y = 5 + s / 20;
		gap = (s - 10) / 3;
		r = s / 5;
	}
	
	@Override
	public void setEnabled(boolean f)
	{
		if (f)
		{
			setBackground(Color.white);
			pressed = false;
		}
		else
		{
			setBackground(Color.gray);
			pressed = true;
		}
	}

	public void setVal(int n)
	{
		val = n;
		repaint();
	}

	public int getVal()
	{
		return val;
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		// dice dot color is red
		g.setColor(Color.red);
		// draw dices according to dice value
		switch (val)
		{
		case 1:
			g.fillOval(x + gap, y + gap, r, r);
			break;
		case 2:
			g.fillOval(x, y, r, r);
			g.fillOval(x + gap * 2, y + gap * 2, r, r);
			break;
		case 3:
			g.fillOval(x, y, r, r);
			g.fillOval(x + gap, y + gap, r, r);
			g.fillOval(x + gap * 2, y + gap * 2, r, r);
			break;
		case 4:
			g.fillOval(x, y, r, r);
			g.fillOval(x + gap * 2, y, r, r);
			g.fillOval(x, y + gap * 2, r, r);
			g.fillOval(x + gap * 2, y + gap * 2, r, r);
			break;
		case 5:
			g.fillOval(x, y, r, r);
			g.fillOval(x + gap * 2, y, r, r);
			g.fillOval(x, y + gap * 2, r, r);
			g.fillOval(x + gap * 2, y + gap * 2, r, r);
			g.fillOval(x + gap, y + gap, r, r);
			break;
		case 6:
			g.fillOval(x, y, r, r);
			g.fillOval(x + gap * 2, y, r, r);
			g.fillOval(x, y + gap * 2, r, r);
			g.fillOval(x + gap * 2, y + gap * 2, r, r);
			g.fillOval(x, y + gap, r, r);
			g.fillOval(x + gap * 2, y + gap, r, r);
			break;
		}
	}
	
	@Override
	public boolean isEnabled()
	{
		return !pressed;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
		setBackground(Color.yellow);
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		setEnabled(!pressed);// change color back
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
