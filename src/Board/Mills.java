package Board;

public class Mills {

    //Checking if Mills happened
	public boolean checkMills(Position pos) {
		boolean isInMillWestAndEastNeighbour = false; 
		boolean isInMillNorthAndSouthNeighbour = false;
		try {
			isInMillWestAndEastNeighbour = pos.getWestNeighbour().getPiece().getOwner() == pos.getEastNeighbour().getPiece().getOwner() && pos.getEastNeighbour().getPiece().getOwner() == pos.getPiece().getOwner();
			

		} catch (Exception noValueError) { // (not sure what the error we want to catch is actually called)
   	 	//Do nothing

		}
		
		try { isInMillNorthAndSouthNeighbour = pos.getNorthNeighbour().getPiece().getOwner() == pos.getSouthNeighbour().getPiece().getOwner() && pos.getSouthNeighbour().getPiece().getOwner() == pos.getPiece().getOwner();

		} catch (Exception error) {
			//Do nothing
		}
		
		return isInMillWestAndEastNeighbour || isInMillNorthAndSouthNeighbour;

	}
}
