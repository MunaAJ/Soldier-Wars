package fighters;

import framework.BattleField;

public class BasicSoldier {
	public final static int INITIAL_HEALTH = 10;  

	public final static int ARMOR = 20;    

	public final static int STRENGTH = 30;   

	public final static int SKILL = 40;   

	public final BattleField grid;  

	public int row, col; 

	public int health;  

	public final int team; 

	public final int ENEMY;

	public final static int UP = 0;
	public final static int RIGHT = 1;
	public final static int DOWN = 2;
	public final static int LEFT = 3;
	public final static int UP_AND_RIGHT = 4;
	public final static int DOWN_AND_RIGHT = 5;
	public final static int DOWN_AND_LEFT = 6;
	public final static int UP_AND_LEFT = 7;
	public final static int NEUTRAL = -1;

	public BasicSoldier(BattleField gridIn, int teamIn, int rowIn, int colIn) {
		this.grid = gridIn;
		this.team = teamIn;
		this.row = rowIn; 
		this.col = colIn;
		this.health = INITIAL_HEALTH;
		if (this.team == BattleField.BLUE_TEAM) {
			ENEMY = BattleField.RED_TEAM;	
		}	else {
			ENEMY = BattleField.BLUE_TEAM;
		}
	}

	public boolean canMove()  {
		if (grid.get(row, col + 1) == BattleField.EMPTY || grid.get
			(row + 1, col) == BattleField.EMPTY || grid.get
			(row - 1, col) == BattleField.EMPTY|| grid.get(row, col - 1)
			== BattleField.EMPTY) {
			return true; 
		}	else {
			return false;
		}
	}

	public int numberOfEnemiesRemaining()  {
		int count = 0;
		if (team == BattleField.RED_TEAM) {
			for (int row1 = 0; row1 < grid.getRows(); row1++) {
				for (int col1 = 0; col1 < grid.getCols(); col1++) {
					if (grid.get(row1,col1) == BattleField.BLUE_TEAM) {
						count += 1;
					}
				}
			}
		}
		if (team == BattleField.BLUE_TEAM) {
			for (int row1 = 0; row1 < grid.getRows(); row1++) {
				for (int col1 = 0; col1 < grid.getCols(); col1++) {
					if (grid.get(row1,col1) == BattleField.RED_TEAM) {
						count += 1;
					}
				}
			}	
		}
		return count;
	}

	public int getDistance(int destinationRow, int destinationCol) {
		int moves = 0;
		this.grid.get(row,col);
		if (destinationRow >= row) {
			for (int counter = row; counter < destinationRow; counter++) {
				moves += 1;
			}
		}
		if (destinationRow <= row) {
			for (int counter = destinationRow; counter < row; counter++) {
				moves += 1;
			}
		}
		if (destinationCol >= col) {
			for (int counter = col; counter < destinationCol; counter++) {
				moves += 1;
			}
		}
		if (destinationCol <= col) {
			for (int counter = destinationCol; counter < col; counter++) {
				moves += 1;
			}
		}
		return moves;
	}

	public int getDirection(int destinationRow, int destinationCol) {
		int value = 0;
		this.grid.get(row,col);
		if (destinationRow > row && destinationCol == col ) {
			value = DOWN;
		}
		if (destinationRow < row && destinationCol == col) {
			value = UP;
		}
		if (destinationRow == row && destinationCol < col) {
			value = LEFT;
		}
		if (destinationRow == row && destinationCol > col) {
			value = RIGHT;
		}
		if (destinationRow < row && destinationCol > col) {
			value = UP_AND_RIGHT;
		}
		if (destinationRow > row && destinationCol > col) {
			value = DOWN_AND_RIGHT;
		}
		if (destinationRow > row && destinationCol < col) {
			value = DOWN_AND_LEFT;
		}
		if (destinationRow < row && destinationCol < col) {
			value = UP_AND_LEFT;
		}
		if (destinationRow == row && destinationCol == col) {
			value = NEUTRAL;
		}
		return value;
	}

	public int numberOfFriendsRemaining()  {
		int count = 0;
		if (team == BattleField.RED_TEAM) {
			for (int row1 = 0; row1 < grid.getRows(); row1++) {
				for (int col1 = 0; col1 < grid.getCols(); col1++) {
					if (grid.get(row1,col1) == BattleField.RED_TEAM) {
						count += 1;
					}
				}
			}
		}
		if (team == BattleField.BLUE_TEAM) {
			for (int row1 = 0; row1 < grid.getRows(); row1++) {
				for (int col1 = 0; col1 < grid.getCols(); col1++) {
					if (grid.get(row1,col1) == BattleField.BLUE_TEAM) {
						count += 1;
					}
				}
			}	
		}
		return count;
	}

	public int getDirectionOfNearestFriend() {
		int closeTeammate = (grid.getRows() * grid.getCols());
		int value = 0;
		if (team == BattleField.BLUE_TEAM) {
			for (int row1 = 0; row1 < grid.getRows(); row1++) {
				for (int col1 = 0; col1 < grid.getCols(); col1++) {
					if (grid.get(row1,col1) == BattleField.BLUE_TEAM && 
					getDistance(row1, col1) != this.getDistance(row, col)
					&& getDistance(row1,col1) <= closeTeammate) {
					closeTeammate = getDistance(row1, col1);
					value = getDirection(row1, col1); 
					}
				}
			}
		}
		if (team == BattleField.RED_TEAM) {
			for (int row1 = 0; row1 < grid.getRows(); row1++) {
				for (int col1 = 0; col1 < grid.getCols(); col1++) {
					if (grid.get(row1,col1) == BattleField.RED_TEAM && 
					getDistance(row1, col1) != this.getDistance(row, col) 
					&& getDistance(row1, col1) <= closeTeammate) {
					closeTeammate = getDistance(row1, col1);
					value = getDirection(row1, col1);
					}
				}
			}
		}
		if (numberOfFriendsRemaining() == 1) {
			value = NEUTRAL;
		}
		return value;
	}

	public int countNearbyFriends(int radius) {
		int count = 0;
		if (this.team == BattleField.BLUE_TEAM) {
			for (int row1 = 0; row1 < grid.getRows(); row1++) {
				for (int col1 = 0; col1 < grid.getCols(); col1++) {
					if (grid.get(row1,col1) == BattleField.BLUE_TEAM && 
							getDistance(row1, col1) != 0 && 
							getDistance(row1, col1) <= radius ) {
						count += 1;
					}
				}
			}
		}
		if (this.team == BattleField.RED_TEAM) {
			for (int row1 = 0; row1 < grid.getRows(); row1++) {
				for (int col1 = 0; col1 < grid.getCols(); col1++) {
					if (grid.get(row1,col1) == BattleField.RED_TEAM && 
							getDistance(row1, col1) != 0 && 
							getDistance(row1, col1) <= radius ) {
						count += 1;
					}
				}
			}
		}
		return count;
	}

	public int getDirectionOfNearestEnemy(int radius) {
		int closeEnemy = radius;
		int value = 0;
		int count = 0;
		if (team == BattleField.BLUE_TEAM) {
			for (int row1 = 0; row1 < grid.getRows(); row1++) {
				for (int col1 = 0; col1 < grid.getCols(); col1++) {
					if (grid.get(row1,col1) == BattleField.RED_TEAM && 
							getDistance(row1, col1) <= closeEnemy && 
							grid.get(row1,col1) != BattleField.
							OUT_OF_BOUNDS) {
						value = getDirection(row1, col1); 
						count += 1;
						closeEnemy = getDistance(row1, col1);
					}
				}
			}
		}
		if (team == BattleField.RED_TEAM) {
			for (int row1 = 0; row1 < grid.getRows(); row1++) {
				for (int col1 = 0; col1 < grid.getCols(); col1++) {
					if (grid.get(row1,col1) == BattleField.BLUE_TEAM && 
							getDistance(row1, col1) <= closeEnemy && 
							grid.get(row1,col1) != BattleField.
							OUT_OF_BOUNDS) {
						value = getDirection(row1, col1); 
						count += 1;
						closeEnemy = getDistance(row1, col1);
					}
				}
			}
		}
		if (count == 0) {
			value = NEUTRAL;
		}
		return value;
	}

	public void performMyTurn() {
		if (grid.get(row, (col - 1)) == ENEMY && grid.get(row, (col - 1)) 
				!= BattleField.OUT_OF_BOUNDS) {
			grid.attack(row, (col - 1));
		}
		if (grid.get(row, (col + 1)) == ENEMY && grid.get(row, (col + 1)) 
				!= BattleField.OUT_OF_BOUNDS) {
			grid.attack(row, (col + 1));
		}	
		if (grid.get((row - 1), col) == ENEMY && grid.get((row - 1), col) 
				!= BattleField.OUT_OF_BOUNDS) {
			grid.attack((row - 1), col);
		}
		if (grid.get((row + 1), col) == ENEMY && grid.get((row + 1), col) 
				!= BattleField.OUT_OF_BOUNDS) {
			grid.attack((row + 1), col);
		} 



		if (grid.get((row + 1), col) == BattleField.EMPTY && grid.get(
				(row + 1), col) != BattleField.OUT_OF_BOUNDS) {
			row = row + 1;
			return;
		} 
		if (grid.get(row, (col - 1)) == BattleField.EMPTY && grid.get(row, 
				(col - 1)) != BattleField.OUT_OF_BOUNDS) {
			col = col - 1;
			return;
		}
		if (grid.get(row, (col + 1)) == BattleField.EMPTY && grid.get(row, 
				(col + 1)) != BattleField.OUT_OF_BOUNDS) {
			col = col + 1;
			return;
		}	
		if (grid.get((row - 1), col) == BattleField.EMPTY && grid.get(
				(row - 1), col) != BattleField.OUT_OF_BOUNDS) {
			row = row - 1;
			return;
		}



		if (grid.get((row + 1), col) == BattleField.OBSTACLE || grid.get
				((row + 1),col) == BattleField.OUT_OF_BOUNDS) {
			grid.get(row, col);
			return;
		} 	
		if (grid.get((row - 1), col) == BattleField.OBSTACLE || grid.get
				((row - 1), col) == BattleField.OUT_OF_BOUNDS) {
			grid.get(row, col);
			return;
		} 
		if (grid.get(row, (col + 1)) == BattleField.OBSTACLE || grid.get
				(row, (col + 1)) == BattleField.OUT_OF_BOUNDS) {
			grid.get(row, col);
			return;
		} 	
		if (grid.get(row, (col - 1)) == BattleField.OBSTACLE || grid.get
				(row, (col - 1)) == BattleField.OUT_OF_BOUNDS) {
			grid.get(row, col);
			return;
		} 	
	}
}