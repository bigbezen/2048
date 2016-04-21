package GamePlay;

import javax.swing.*;
import javax.xml.ws.handler.MessageContext.Scope;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import GamePlay.Board.Direction;
import GamePlay.BoardPanel.pictureType;

public class Game extends JFrame implements KeyListener, ActionListener {

	private BoardPanel _boardPanel;
	private Board _board;
	private int _score;
	private HeadlinePanel _headline;
	private GameOver _over;
	private winFrame _win;
	private boolean won;
	private Records _records;
	private JMenuBar _menuBar;
	private JMenuItem newGame;
	private JMenuItem exit;
	private JMenuItem topScore;
	private JMenuItem color;
	private JMenuItem parlament;

	private final int N = 4;

	public Game() {
		super("2048 - Idan And Matan");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		this._board = new Board(N);
		_board.randomInsert();
		_board.randomInsert();
		_boardPanel = new BoardPanel(N, _board);

		try {
			_records = new Records();
			_records.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Cannot load files of database, please re-install the software",
							"File Missing Error", JOptionPane.ERROR_MESSAGE);
			this.dispose();
		} catch (DataBaseSabotageException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Cannot load files correctly of database, please re-install the software or delete the file \' Records.txt \'",
							"Data Corrupt", JOptionPane.ERROR_MESSAGE);
			this.dispose();
		}
		
		won = false;
		this._win = new winFrame(this);
		this._over = new GameOver(this);
		_menuBar = new JMenuBar();

		JMenu tMenu = new JMenu("Options");
		newGame = new JMenuItem("New Game");
		newGame.addActionListener(this);
		tMenu.add(newGame);
		topScore = new JMenuItem("Top Scores");
		topScore.addActionListener(this);
		tMenu.add(topScore);

		JMenu setting=new JMenu("Settings");
		color=new JMenuItem("Color");
		color.addActionListener(this);
		parlament=new JMenuItem("parlament");
		parlament.addActionListener(this);
		setting.add(color);
		setting.add(parlament);
		tMenu.add(setting);
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		tMenu.add(exit);
		_menuBar.add(tMenu);

		tMenu = new JMenu("About");
		_menuBar.add(tMenu);
		tMenu.add(new JMenuItem("this game create and Designed by Idan & Matan"));
		this.getContentPane().add(_menuBar, BorderLayout.BEFORE_FIRST_LINE);

		_score = 0;

		_headline = new HeadlinePanel();
		this.getContentPane().add(_headline, BorderLayout.CENTER);

		JPanel center = new JPanel(new GridBagLayout());
		GridBagConstraints tConst = new GridBagConstraints();
		tConst.anchor = GridBagConstraints.NORTHWEST;
		tConst.gridy = 0;
		tConst.gridx = 0;
		tConst.weightx = 0.0;
		tConst.weighty = 0.0;
		tConst.fill = GridBagConstraints.HORIZONTAL;

		tConst.gridy = -1;
		center.add(_boardPanel, tConst);
		this.getContentPane().add(center, BorderLayout.AFTER_LAST_LINE);

		// this.setPreferredSize(new Dimension(400,400));
		this.pack();
		Dimension minimumSize = this.getPreferredSize();
		this.setMinimumSize(minimumSize);
		this.addKeyListener(this);
		this.setFocusable(true);
		this.setVisible(true);
	}

	// TODO: DEAL WITH THE SCORE ISSUE

	public int get_score() {
		return _score;
	}

	public void set_score(int _score) {
		this._score = _score;
	}

	private void gameOver(int score) {
		_over.setScore(_score);
		_over.setVisible(true);

	}

	private void win(int score) {
		_win.setVisible(true);
		_win.setScore(_score);
		won = true;
	}

	public void restart() {
		won = false;
		this._board = new Board(N);
		_board.randomInsert();
		_board.randomInsert();
		_score = 0;
		_boardPanel.setBoard(_board);
		_boardPanel.updateGameBoard(_board.getValues());
	}

	// return true if the move done and false if not
	private void move(Direction direction) {

		boolean change;
		int[][] changes = _board.step(direction);
		change = _board.isMove(changes);

		_boardPanel.animationStep(direction, changes, _board.getValues());

		changes = _board.Unify(direction);
		if (!change)
			change = _board.isMove(changes);
		_boardPanel.animationMerge(direction, changes, _board.getValues());

		changes = _board.step(direction);
		if (!change)
			change = _board.isMove(changes);
		_boardPanel.animationStep(direction, changes, _board.getValues());

		set_score(_board.getScore());
		_headline.setScore(_score);

		if (change) {
			_board.randomInsert();
		}
		if (_board.hasWon())
			win(_score);

	}

	public void keyPressed(KeyEvent e) {
		Direction dir = null;
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			dir = Direction.LEFT;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			dir = Direction.RIGHT;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			dir = Direction.UP;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			dir = Direction.DOWN;
		}
		// TODO game over
		if (_board.hasMoves() == false)
			gameOver(_score);
		else if (dir != null) {
			move(dir);
			// TODO win
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == _over.getRestart()) {
			restart();
			_over.dispose();
			_over = new GameOver(this);

		} else if (e.getSource() == _win.getRestart()) {
			restart();
			_win.dispose();
			_win = new winFrame(this);

		} else if (e.getSource() == _over.getSave()) {
			if (_over.checkName()) {
				User user = new User(_over.getName(), _score);
				 _records.insertUser(user);
				restart();
				_over.dispose();
				_over = new GameOver(this);
			} else
				JOptionPane.showMessageDialog(_over,
						"Invalid expression please check your name!",
						"Inane error", JOptionPane.ERROR_MESSAGE);
		} else if (e.getSource() == _win.getSave()) {
			if (_win.checkName()) {
				User user = new User(_win.getName(), _score);
				_records.insertUser(user);
				restart();
				_win.dispose();
				_win = new winFrame(this);
			} else
				JOptionPane.showMessageDialog(_over,
						"Invalid expression please check your name!",
						"Inane error", JOptionPane.ERROR_MESSAGE);
		} else if (e.getSource() == newGame) {
			restart();
		} else if (e.getSource() == exit) {
			this.dispose();
		} else if (e.getSource() == topScore) {
			try {
				_records.update();
			} catch (IOException | DataBaseSabotageException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			_records.setVisible(true);
		}else if (e.getSource() == color) {
			ImageIcon icon=new ImageIcon("./Images/headline.gif");
			_headline.setHeadlineImage(icon);
			_boardPanel.reloadPicturs(pictureType.DEFAULTE);
		} else if (e.getSource() == parlament) {
			ImageIcon icon=new ImageIcon("./Images/headline_parlament.gif");
			_headline.setHeadlineImage(icon);
			_boardPanel.reloadPicturs(pictureType.PARLAMENT);
			this.repaint();
		}
		this.requestFocusInWindow();
	}

	public static void main(String[] args) {
		// TODO: deal with JFrame regular screen issues
		// TODO: active the game

		Game game = new Game();
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
