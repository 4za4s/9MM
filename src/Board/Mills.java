package Board;

import java.util.ArrayList;



/* Manages the Mills for a game */
public class Mills {

	//Stores all of the mills
	ArrayList<Position[]> millArray = new ArrayList<Position[]>();

	public boolean hasPieceInMill(Player player){
		for (Position[] posLst : millArray){
			if (posLst[0].getPiece().getOwner() == player){
				return true;
			}

		}
		return false;
	}

	public boolean playerHasPieceNotInMill(Player player){

		ArrayList<Piece> pieces = player.getPieces();
		for (Piece piece : pieces) {
			System.out.println(piece.getPosition() != null);
			
			if (piece.getPosition() != null && !isInMill(piece.getPosition()) ){
				return true;
			}
		}

		return false;

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

	public boolean addMill(Position[] mill){
		if (mill != null && mill.length == 3){
			millArray.add(mill);
			return true;
		}
		return false;
	}

	public void removeMill(Position pos){
		for (int i = 0; i < millArray.size(); i++) {
			for (int j = 0; j < millArray.get(i).length; j++) {
				if (millArray.get(i)[j] == pos){
					millArray.remove(i);
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

	public int createMill(Piece piece){
		int mills = 0;
		if (piece.getPosition() == null){
			return mills;
		}

		Position pos = piece.getPosition();

		if (addMill(horizontalMill(piece))){
			mills++;
		} 
		if (addMill(verticalMill(piece))){
			mills++;
		} 
		if (pos.getWestNeighbour() != null && addMill(horizontalMill(pos.getWestNeighbour().getPiece()))){
			mills++;
		} 
		if (pos.getEastNeighbour() != null && addMill(horizontalMill(pos.getEastNeighbour().getPiece()))){
			mills++;
		} 
		if (pos.getNorthNeighbour() != null && addMill(verticalMill(pos.getNorthNeighbour().getPiece()))){
			mills++;
		} 
		if (pos.getSouthNeighbour() != null && addMill(verticalMill(pos.getSouthNeighbour().getPiece()))){
			mills++;
		} 
		return mills;
	}

	public Position[] horizontalMill(Piece piece){
		if (piece == null || piece.getPosition() == null){
			return null;
		}
		Player owner = piece.getOwner();
		Position pos = piece.getPosition();

		try { 
			Player west = pos.getWestNeighbour().getPiece().getOwner();
			Player east = pos.getEastNeighbour().getPiece().getOwner();
			if (west == east && east == owner){
				Position[] mill = {pos.getWestNeighbour(),pos,pos.getEastNeighbour()};
				return mill;
			}
		} catch (Exception NullPointerException) {
		}
		return null;
	}	

	public Position[] verticalMill(Piece piece){
		if (piece == null || piece.getPosition() == null){
			return null;
		}
		Player owner = piece.getOwner();
		Position pos = piece.getPosition();

		try { 
			Player north = pos.getNorthNeighbour().getPiece().getOwner();
			Player south = pos.getSouthNeighbour().getPiece().getOwner();
			if (north == south && south == owner){
				Position[] mill = {pos.getNorthNeighbour(),pos,pos.getSouthNeighbour()};
				return mill;
			}
		} catch (Exception NullPointerException) {
		}
		return null;
	}	
}