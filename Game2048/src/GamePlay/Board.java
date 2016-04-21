package GamePlay;

import java.util.Vector;

public class Board {

	private int[][] _values;
	private int _score;
	private final int CHANGED = 1;
	private final int DELETED = -1;

	public enum Direction {
		RIGHT, LEFT, UP, DOWN
	};

	private final int N;

	public Board(int size) {
		N = size;
		_values = new int[N][N];
	}

	public int[][] getValues() {
		return _values;
	}

	public int getScore() {
		return _score;
	}
	
	public boolean isMove(int[][] change){
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(change[i][j]!=0)return true;
			}
			}
		return false;
	}


	/**
	 * 
	 * @param dir
	 *            direction of the step - north,south,east,west
	 * @return
	 */
	public int[][] step(Direction dir) {
		int size = _values.length - 1;
		int[][] moves = new int[N][N];
		int countStep;
		switch (dir) {
		case RIGHT:
			for (int i = 0; i <= size; i++) {
				for (int j = size; j >= 0; j--) {
					countStep = 0;
					if (_values[i][j] != 0) {
						int k = j;
						while ((k + 1) <= size && _values[i][k + 1] == 0) {
							_values[i][k + 1] = _values[i][k];
							_values[i][k] = 0;
							countStep++;
							k++;
						}
						moves[i][j] = countStep;
					}
				}
			}
			break;

		case LEFT:
			for (int i = 0; i <= size; i++) {
				for (int j = 0; j <= size; j++) {
					countStep = 0;
					if (_values[i][j] != 0) {
						int k = j;
						while ((k - 1) >= 0 && _values[i][k - 1] == 0) {
							_values[i][k - 1] = _values[i][k];
							_values[i][k] = 0;
							countStep++;
							k--;
						}// while
						moves[i][j] = countStep;
					}// if
				}// for j
			}// for i
			break;

		case UP:
			for (int i = 0; i <= size; i++) {
				for (int j = 0; j <= size; j++) {
					countStep = 0;
					if (_values[i][j] != 0) {
						int k = i;
						while ((k - 1) >= 0 && _values[k - 1][j] == 0) {
							_values[k - 1][j] = _values[k][j];
							_values[k][j] = 0;
							countStep++;
							k--;
						}
						moves[i][j] = countStep;
					}
				}
			}
			break;

		case DOWN:
			for (int i = size; i >= 0; i--) {
				for (int j = 0; j <= size; j++) {
					countStep = 0;
					if (_values[i][j] != 0) {
						int k = i;
						while ((k + 1) <= size && _values[k + 1][j] == 0) {
							_values[k + 1][j] = _values[k][j];
							_values[k][j] = 0;
							countStep++;
							k++;
						}
						moves[i][j] = countStep;
					}// if
				}// for
			}// for
			break;
		}// s
		return moves;
	}

	/**
	 * unit all cells that can be together
	 * 
	 * @param dir
	 *            direction of the step - north,south,east,west
	 * @return board of cells that changed: 1, cells to delete: -1, cells with
	 *         no change: 0
	 */
	public int[][] Unify(Direction dir) {
		// TODO:

		int[][] boardChanged = new int[N][N];

		switch (dir) {
		case LEFT:
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (!isEdge(i, j, dir)
							&& _values[i][j - 1] == _values[i][j]
							&& _values[i][j] != 0) {
						_values[i][j - 1] += _values[i][j];
						_values[i][j] = 0;
						_score += _values[i][j - 1];
						boardChanged[i][j] = DELETED;
						boardChanged[i][j - 1] = CHANGED;
					}
				}
			}

			break;

		case RIGHT:
			for (int i = 0; i < N; i++) {
				for (int j = N; j >= 0; j--) {
					if (!isEdge(i, j, dir)
							&& _values[i][j] == _values[i][j + 1]
							&& _values[i][j] != 0) {
						_values[i][j + 1] += _values[i][j];
						_values[i][j] = 0;
						_score += _values[i][j + 1];
						boardChanged[i][j] = DELETED;
						boardChanged[i][j + 1] = CHANGED;
					}
				}
			}
			break;
		case UP:
			for (int j = 0; j < N; j++) {
				for (int i = 0; i < N; i++) {
					if (!isEdge(i, j, dir)
							&& _values[i - 1][j] == _values[i][j]
							&& _values[i][j] != 0) {
						_values[i - 1][j] += _values[i][j];
						_values[i][j] = 0;
						_score += _values[i - 1][j];
						boardChanged[i][j] = DELETED;
						boardChanged[i - 1][j] = CHANGED;
					}
				}
			}
			break;
		case DOWN:
			for (int j = 0; j < N; j++) {
				for (int i = N; i >= 0; i--) {
					if (!isEdge(i, j, dir)
							&& _values[i + 1][j] == _values[i][j]
							&& _values[i][j] != 0) {
						_values[i + 1][j] += _values[i][j];
						_values[i][j] = 0;
						_score += _values[i + 1][j];
						boardChanged[i][j] = DELETED;
						boardChanged[i + 1][j] = CHANGED;
					}
				}
			}
			break;

		}
		return boardChanged;
	}

	/**
	 * checks if the current indexes at the edge of the board
	 * 
	 * @param i
	 * @param j
	 * @param dir
	 * @return
	 */
	private boolean isEdge(int i, int j, Direction dir) {
		switch (dir) {
		case LEFT:
			return j <= 0;

		case RIGHT:
			return j >= N - 1;

		case UP:
			return i <= 0;

		case DOWN:
			return i >= N - 1;

		}
		return false;
	}

	/**
	 * insert cell with value 2 in chance of 0.75 and cell of 4 in 0.25
	 */
	public void randomInsert() {
		Vector<Vector<Integer>> emptyCell = new Vector<Vector<Integer>>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (_values[i][j] == 0) {
					emptyCell.add(new Vector<Integer>());
					emptyCell.lastElement().add(new Integer(j));
					emptyCell.lastElement().add(new Integer(i));

				}
			}
		}
		double random = Math.random();
		int cellEmpty = (int) (random * (emptyCell.size()));
		if (emptyCell.size() != 0) {
			if (random > 0.25)
				_values[emptyCell.elementAt(cellEmpty).elementAt(1).intValue()][emptyCell
						.elementAt(cellEmpty).elementAt(0).intValue()] = 2;
			else {
				_values[emptyCell.elementAt(cellEmpty).elementAt(1).intValue()][emptyCell
						.elementAt(cellEmpty).elementAt(0).intValue()] = 4;
			}
		}
	}

	/**
	 * @return true if moves left to do
	 */
	public boolean hasMoves() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (_values[i][j] == 0)
					return true;
				else if (!isEdge(i, j, Direction.UP)
						&& _values[i - 1][j] == _values[i][j])
					return true;
				else if (!isEdge(i, j, Direction.DOWN)
						&& _values[i + 1][j] == _values[i][j])
					return true;
				else if (!isEdge(i, j, Direction.LEFT)
						&& _values[i][j - 1] == _values[i][j])
					return true;
				else if (!isEdge(i, j, Direction.RIGHT)
						&& _values[i][j + 1] == _values[i][j])
					return true;
			}
		}
		return false;
	}

	// return true if in the board have the value 2048
	public boolean hasWon() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (_values[i][j] == 2048)
					return true;
			}
		}
		return false;
	}

	public static void print(int[][] arr) {
		for (int i = 0; i < 4; i++) {
			System.out.println();
			for (int j = 0; j < 4; j++) {
				System.out.print(arr[i][j] + " ");
			}
		}
	}
}
