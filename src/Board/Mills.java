package Board;

import java.util.ArrayList;



/* Manages the Mills for a game */
public class Mills {


	Board board;


	//Dtores all of the mills
	ArrayList<Position[]> millArray = new ArrayList<Position[]>();



	public Mills(Board board){
		this.board = board;


	}

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

	/* Recreates the mills list */
	public ArrayList<Position[]> getMills(){
		millArray = new ArrayList<Position[]>();

		for (Position pos : board.getPositions()){

			try { //East / West
			if (pos.getWestNeighbour().getPiece().getOwner() == pos.getEastNeighbour().getPiece().getOwner() && 
			pos.getEastNeighbour().getPiece().getOwner() == pos.getPiece().getOwner()){
				Position[] mill = {pos.getWestNeighbour(),pos,pos.getEastNeighbour()};
				millArray.add(mill);
			}


			} catch (Exception NullPointerException) {}

			try { //North / South
				if (pos.getNorthNeighbour().getPiece().getOwner() == pos.getSouthNeighbour().getPiece().getOwner() && 
				pos.getSouthNeighbour().getPiece().getOwner() == pos.getPiece().getOwner()){
					Position[] mill = {pos.getNorthNeighbour(),pos,pos.getSouthNeighbour()};
					millArray.add(mill);
				}
	
	
				} catch (Exception NullPointerException) {}

		}



		return millArray;
	}


}