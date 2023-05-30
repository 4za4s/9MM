package Board;

import java.util.ArrayList;

import Player.Player;



/**
 * Manages the Mills for a game 
 */
public class Mills {

	
	private ArrayList<Position[]> millArray = new ArrayList<Position[]>(); //Stores all of the mills

	/**
	 * Works out if a players has a piece is in a mill
	 * @param player placer to check
	 * @return if player has a piece in a mill
	 */
	public boolean hasPieceInMill(Player player){
		for (Position[] posLst : millArray){
			if (posLst[0].getPiece().getOwner() == player){
				return true;
			}

		}
		return false;
	}

	/**
	 * Works out if a players has a piece NOT in a mill
	 * @param player placer to check
	 * @return if a player has a piece not in a mill
	 */
	public boolean playerHasPieceNotInMill(Player player){

		ArrayList<Piece> pieces = player.getPieces();
		for (Piece piece : pieces) {
			
			if (piece.getPosition() != null && !isInMill(piece) ){
				return true;
			}
		}

		return false;

	}

	/**
	 * If a piece is in a mill
	 * @param piece piece to check
	 * @return if the piece is in a mill
	 */
	public boolean isInMill(Piece piece){
		//Scan though all mills to check
		for (Position[] posLst : millArray) {
			for (Position posVar : posLst) {
				if (posVar.getPiece() == piece){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Adds a mill
	 * @param mill mill to add
	 * @return if the mill was successfully added
	 */
	public boolean addMill(Position[] mill){
		if (mill != null && mill.length == 3){
			millArray.add(mill);
			return true;
		}
		return false;
	}

	/**
	 * Removes mills that include a position
	 * @param pos position to check
	 */
	public void removeMill(Position pos){
		for (int i = 0; i < millArray.size(); i++) {
			for (int j = 0; j < millArray.get(i).length; j++) {
				if (millArray.get(i)[j] == pos){
					millArray.remove(i);
					removeMill(pos);
					return;
				}
			}
		}
	}



	/**
	 * Get all of the mills
	 * @return list of mills
	 */
	public ArrayList<Position[]> getMills(){
		return millArray;
	}


	/**
	 * Creates mills if the piece is in a mill
	 * @param piece piece to check
	 * @return number of mills created
	 */
	public int createMill(Piece piece){
		int millsCreated = 0;
		if (piece.getPosition() == null){
			return millsCreated;
		}

		Position pos = piece.getPosition();

		if (addMill(horizontalMill(piece))){
			millsCreated++;
		} 
		if (addMill(verticalMill(piece))){
			millsCreated++;
		} 
		if (pos.getWestNeighbour() != null && addMill(horizontalMill(pos.getWestNeighbour().getPiece()))){
			millsCreated++;
		} 
		if (pos.getEastNeighbour() != null && addMill(horizontalMill(pos.getEastNeighbour().getPiece()))){
			millsCreated++;
		} 
		if (pos.getNorthNeighbour() != null && addMill(verticalMill(pos.getNorthNeighbour().getPiece()))){
			millsCreated++;
		} 
		if (pos.getSouthNeighbour() != null && addMill(verticalMill(pos.getSouthNeighbour().getPiece()))){
			millsCreated++;
		} 
		return millsCreated;
	}


	/**
	 * Makes a horizontal mill
	 * @param piece piece to make mill around
	 * @return the new mill if the piece creates a mil
	 */
	private Position[] horizontalMill(Piece piece){
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

	/**
	 * Makes a vertical mill
	 * @param piece piece to make mill around
	 * @return the new mill if the piece creates a mil
	 */
	private Position[] verticalMill(Piece piece){
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