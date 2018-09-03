package com.mystudy.swing01;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

class ImagePanel extends JPanel{
	private Image img;
	
	public ImagePanel(Image img) {
		this.img = img;
		setSize(new Dimension(img.getWidth(null),img.getHeight(null))); //img 최대값
		setPreferredSize(new Dimension(img.getWidth(null),img.getHeight(null)));
		setLayout(null);	
	}
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}