package GamePlay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import GamePlay.Board.Direction;

public class BoardPanel extends JPanel {
	public enum pictureType {
		DEFAULTE, PARLAMENT
	};

	private JLabel[][] gameBoard;
	private Timer timer;
	private final int N;

	private final int CELL_SIZE_VAL;// TODO move to Animation picture?
	private final int JUMP;

	private Vector<AnimatedPicture> animatedPictures;

	private BufferedImage[] _images;
	private ImageIcon _emptyIcon;

	private Board _board;

	public BoardPanel(int N, Board board) {

		this._board = board;
		int[][] value = board.getValues();

		CELL_SIZE_VAL = 104;
		JUMP = 8;

		this.N = N;
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.gray.brighter());
		this.setBorder(new LineBorder(Color.darkGray, 2));
		_emptyIcon = new ImageIcon("./Images/blocks/block_0.gif");
		try {
			_images = loadImages(pictureType.DEFAULTE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		animatedPictures = new Vector<AnimatedPicture>();

		GridBagConstraints c = new GridBagConstraints();

		gameBoard = new JLabel[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				gameBoard[i][j] = new JLabel();
				gameBoard[i][j].setBorder(new LineBorder(Color.gray, 1));
				gameBoard[i][j].setIcon(_emptyIcon);
				c.gridx = j;
				c.gridy = i;
				c.insets = new Insets(1, 1, 1, 1);
				add(gameBoard[i][j], c);
			}
		}

		System.out.println("create timer!*!");// TODO delete
		timer = new Timer(2, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				animate();
			}// actionPreformed

		});// timer

		System.out.println("timer start!!");// TODO delete
		timer.start();
	}

	public void animationStep(Direction dir, int[][] boardChange, int[][] values) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (boardChange[i][j] > 0) {

					// create animated picture and save it;
					animatedPictures.add(new AnimatedPicture(gameBoard[i][j]
							.getX(), gameBoard[i][j].getY(), dir,
							boardChange[i][j], (ImageIcon) gameBoard[i][j]
									.getIcon()));

					gameBoard[i][j].setIcon(_emptyIcon);

				}// if
			}// for j
		}// for i

	}// animation step

	// TODO delete test
	private void test_animatedPictuersStatus() {
		for (AnimatedPicture pic : animatedPictures) {
			System.out.println(pic);
		}
	}

	/*
	 * Operates the animation over the animated pictures
	 */
	private void animate() {

		boolean needToMove = false;
		for (AnimatedPicture pic : animatedPictures) {
			needToMove = pic.needToMove();
			// System.out.println("need to move: " + needToMove);//TODO delete
			if (needToMove) {
				pic.move();
			}
		}

		if (!needToMove) {
			animatedPictures.clear();
			// System.out.println("update board");//TODO delete
			updateGameBoard(_board.getValues());
		}

		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		for (AnimatedPicture pic : animatedPictures) {
			pic.paint(this, g2d);
		}

		if (animatedPictures.size() > 0)
			System.out.println("animation pics: " + animatedPictures.size());

	}

	public void animationMerge(Direction dir, int[][] boardChange,
			int[][] values) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (boardChange[i][j] == -1) {

					// create animated picture and save it;
					animatedPictures.add(new AnimatedPicture(gameBoard[i][j]
							.getX(), gameBoard[i][j].getY(), dir, 1,
							(ImageIcon) gameBoard[i][j].getIcon()));

					gameBoard[i][j].setIcon(_emptyIcon);

				}// if
			}// for j
		}// for i

		// TODO delete
		Board.print(boardChange);
		System.out.println("animation merge pics: " + animatedPictures.size());
	}// Animation merge

	private void animationInsert(int[][] values) {
		// TODO animation
	}

	public void updateGameBoard(int[][] values) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {

				gameBoard[i][j].setIcon(new ImageIcon(
						_images[(values[i][j] == 0 ? 0 : (int) (Math
								.log(values[i][j]) / (Math.log(2))))]));
			}
		}
	}

	private BufferedImage[] loadImages(pictureType pt) throws IOException {
		// TODO load by file;

		BufferedImage[] images;
		switch (pt) {
		case DEFAULTE:
			images = new BufferedImage[11];
			for (int i = 0; i < images.length; i++) {
				// get the index of the image
				int index = (int) (i == 0 ? 0 : Math.pow(2, i));
				// try to load the pictures
				images[i] = ImageIO.read(new File("./Images/blocks/block_"
						+ index + ".gif"));
			}
			_emptyIcon = new ImageIcon(images[0]);
			break;
		case PARLAMENT:
			images = new BufferedImage[12];
			for (int i = 0; i < images.length; i++) {
				// get the index of the image
				int index = (int) (i == 0 ? 0 : Math.pow(2, i));
				// try to load the pictures
				images[i] = ImageIO.read(new File("./Images/parlament/parlament_"
						+ index + ".gif"));
			}
			_emptyIcon = new ImageIcon(images[0]);
			break;
		default:
			images = null;
			break;
		}

		return images;
	}
	
	public void reloadPicturs(pictureType pt){
		try {
			_images = loadImages(pt);
			updateGameBoard(_board.getValues());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setBoard(Board bord) {
		this._board = bord;
	}
}
