import java.util.List;
import  java.util.ArrayList;


public class ChessGame {
    private ChessBoard board;
    private boolean whiteTurn = true;
    private Position selectedPosition;

    public ChessGame(){
        this.board = new ChessBoard();
    }
    public boolean makeMove(Position start, Position end){
        Piece movingPiece = board.getPiece(start.getRow(), start.getColumn());
        if(movingPiece == null || movingPiece.getColor() != (whiteTurn ? PieceColor.WHITE : PieceColor.BLACK)){
            return false;
        }
        if(movingPiece.isValidMove(end, board.getBoard())){
            board.movePiece(start, end);
            whiteTurn = !whiteTurn;
            return true;
        }
        return false;
    }

    public boolean isInCheck(PieceColor kingColor) {
        Position kingPosition = findKingPosition(kingColor);
        for (int row = 0; row < board.getBoard().length; row++){
            for(int col = 0; col < board.getBoard()[row].length; col++){
                Piece piece = board.getPiece(row, col);
                if(piece != null && piece.getColor() != kingColor){
                    if(piece.isValidMove(kingPosition, board.getBoard())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Position findKingPosition(PieceColor color) {
        for(int row = 0; row < board.getBoard().length; row++){
            for(int col = 0; col <board.getBoard()[row].length; col++){
                Piece piece = board.getPiece(row, col);
                if( piece instanceof King &&  piece.getColor() == color){
                    return new Position(row, col);
                }
            }
        }
        throw new RuntimeException("King not found, which should never happen.");
    }

    public boolean isCheckmate(PieceColor kingColor) {
        if(!isInCheck(kingColor)) {
            return false;
        }
        Position kingPosition = findKingPosition(kingColor);
        King king = (King) board.getPiece(kingPosition.getRow(), kingPosition.getColumn());
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for(int colOffset = -1; colOffset <=1; colOffset++){
                if(rowOffset == 0 && colOffset == 0){
                    continue;
                }
                Position newPosition = new Position(kingPosition.getRow() + rowOffset, kingPosition.getColumn() + colOffset);
                if(isPositionOnBoard(newPosition) && king.isValidMove(newPosition, board.getBoard()) && !wouldBeInCheckAfterMove(kingColor, kingPosition, newPosition)) {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isPositionOnBoard(Position position) {
        return position.getRow() >= 0 && position.getRow() < board.getBoard().length && position.getColumn() >= 0 && position.getColumn() < board.getBoard()[0].length;
    }
    private boolean wouldBeInCheckAfterMove(PieceColor kingColor, Position from, Position to){
        Piece temp = board.getPiece(to.getRow(), to.getColumn());
        board.setPiece(to.getRow(), to.getColumn(), board.getPiece(from.getRow(), from.getColumn()));
        board.setPiece(from.getRow(), from.getColumn(), null);

        boolean inCheck = isInCheck(kingColor);

        board.setPiece(from.getRow(), from.getColumn(), board.getPiece(to.getRow(), to.getColumn()));
        board.setPiece(to.getRow(), to.getColumn(), temp);
        return inCheck;
    }

    public Piece[][] getBoard() {
        return board;
    }
    public Piece getPiece(int row, int column) {
        return board[row][column];
    }
    public void setPiece(int row, int column, Piece piece) {
        board[row][column] = piece;
        if(piece != null) {
            piece.setPosition(new Position(row, column));
        }
    }
    //doubt from here
    public ChessBoard getBoard(){
        return this.board;
    }
    public void resetGame() {
        this.board = new ChessBoard();
        this.whiteTurn = true;
    }
    public PieceColor getCurrentPlayerColor(){
        return whiteTurn ? PieceColor.WHITE : PieceColor.BLACK;
    }
    public boolean isPieceSelected() {
        return selectedPosition != null;
    }
    public boolean handleSquareSelection(int row, int col){
        if(selectedPosition == null) {
            Piece selectedPiece = board.getPiece(row, col);
            if (selectedPiece != null && selectedPiece.getColor() == (whiteTurn ? PieceColor.WHITE : PieceColor.BLACK)) {
                selectedPosition = new Position(row, col);
                return false;
            }
        }else{
                boolean moveMade = makeMove(selectedPosition, new Position(row, col));
                selectedPosition = null;
                return moveMade;
            }
            return false;
        }
}