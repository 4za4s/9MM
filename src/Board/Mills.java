package Board;

import java.util.ArrayList;



/* Manages the Mills for a game */
public class Mills {

	//Dtores all of the mills
	ArrayList<Position[]> millArray = new ArrayList<Position[]>();

	public boolean isInMill(Position pos){
		//Scan though the whole 
		for (Position[] posLst : millArray) {
			for (Position posVar : posLst) {
				if (posVar == pos){
					return true;
				}
			}
		}
		return false;
	}

	public void addMill(Position[] mill){
		millArray.add(mill);
	}

	public void removeMill(Position pos){
		for (Position[] posLst : millArray) {
			for (Position posVar : posLst) {
				if (posVar == pos){
					millArray.remove(posLst);
					break;
				}
			}
		}
	}

	public Position[] getMill(Position pos){
		for (Position[] posLst : millArray) {
			for (Position posVar : posLst) {
				if (posVar == pos){
					return posLst;
				}
			}
		}
		return null;
	}

	public ArrayList<Position[]> getMills(){
		return millArray;
	}

	public Position[] verticalMill(Position pos){
		Position north = pos.getNorthNeighbour();
		Position south = pos.getSouthNeighbour();
		if (north == null && south == null){
			return null;
		}
		return null;
	}

	/* Recreates the mills list */
// 	public ArrayList<Position[]> getMills(){
// 		millArray = new ArrayList<Position[]>();

// 		for (Position pos : board.getPositions()){

// 			try { //East / West
// 			if (pos.getWestNeighbour().getPiece().getOwner() == pos.getEastNeighbour().getPiece().getOwner() && 
// 			pos.getEastNeighbour().getPiece().getOwner() == pos.getPiece().getOwner()){
// 				Position[] mill = {pos.getWestNeighbour(),pos,pos.getEastNeighbour()};
// 				millArray.add(mill);
// 			}


// 			} catch (Exception NullPointerException) {}

// 			try { //North / South
// 				if (pos.getNorthNeighbour().getPiece().getOwner() == pos.getSouthNeighbour().getPiece().getOwner() && 
// 				pos.getSouthNeighbour().getPiece().getOwner() == pos.getPiece().getOwner()){
// 					Position[] mill = {pos.getNorthNeighbour(),pos,pos.getSouthNeighbour()};
// 					millArray.add(mill);
// 				}
	
	
// 				} catch (Exception NullPointerException) {}

// 		}

// 		return millArray;
// 	}


}