package GamePlay;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import GamePlay.Board.Direction;

public class AnimatedPicture {
	private int _x, _y, _dx, _dy, _xStop, _yStop;
	private BufferedImage image;

	private final int CELL_SIZE_VAL;
	private final int JUMP;

	public AnimatedPicture(int x, int y, Direction dir, int step) {
		CELL_SIZE_VAL = 104;
		JUMP = 8;

		_x = x;
		_y = y;

		switch (dir) {
		case RIGHT:
			_dx = JUMP;
			_dy = 0;
			_xStop = calcXstop(x, step, 1);
			_yStop = calcXstop(y, step, 0);
			break;

		case LEFT:
			_dx = -JUMP;
			_dy = 0;
			_xStop = calcXstop(x, step, -1);
			_yStop = calcYstop(y, step, 0);
			break;

		case UP:
			_dx = 0;
			_dy = -JUMP;
			_xStop = calcXstop(x, step, 0);
			_yStop = calcYstop(y, step, -1);
			break;

		case DOWN:
			_dx = 0;
			_dy = JUMP;
			_xStop = calcXstop(x, step, 0);
			_yStop = calcYstop(y, step, 1);
			break;
		}// switch
	}// init

	public AnimatedPicture(int x, int y, Direction dir, int step,
			BufferedImage image) {
		this(x, y, dir, step);
		this.image = image;
	}

	public AnimatedPicture(int x, int y, Direction dir, int step, ImageIcon icon) {
		this(x, y, dir, step);
		this.image = (BufferedImage) icon.getImage();
	}

	private int calcXstop(int xValue, int numStep, int dir) {
		return xValue + CELL_SIZE_VAL * numStep * dir;
	}

	private int calcYstop(int yValue, int numStep, int dir) {
		return yValue + CELL_SIZE_VAL * numStep * dir;
	}

	/*
	 * return true if the picture left space to move
	 */
	// TODO check if x stop and y stop can't be negative
	public boolean needToMove() {
		if (_dx > 0 & _x < _xStop)
			return true;
		if (_dx < 0 & _x > _xStop)
			return true;
		if (_dy > 0 & _y < _yStop)
			return true;
		if (_dy < 0 & _y > _yStop)
			return true;
		return false;
	}

	public void move() {
		_x += _dx;
		_y += _dy;
	}

	public void paint(BoardPanel boardPanel, Graphics2D g2d) {
		 ImageIcon icon = new ImageIcon(image);
		 icon.paintIcon(boardPanel, g2d, _x, _y);
//		g2d.drawImage(image, _x, _dy, boardPanel);
//		System.out.println(this);//TODO delete
	}

	@Override
	public String toString() {
		return "AnimatedPicture [_x=" + _x + ", _y=" + _y + ", _dx=" + _dx
				+ ", _dy=" + _dy + ", _xStop=" + _xStop + ", _yStop=" + _yStop
				+ ", image=" + (image != null) + "]";
	}

}
