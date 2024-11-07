import javax.swing.text.Position;


public class ChessBoard {
    private Piece[][] board;

    public ChessBoard(){
        this.board = new Piece[8][8];
        setupPieces();
    }

    private void setupPieces(){
        //initial setup of pawns
        //place rooks
        board[0][0] = new Rook(PieceColor.BLACK, new Position(0,0));
        board[0][7] = new Rook(PieceColor.BLACK, new Position(0,7));
        board[7][0] = new Rook(PieceColor.WHITE, new Position(7,0));
        board[7][7] = new Rook(PieceColor.WHITE, new Position(7,7));

        //place knights
        board[0][1] = new Knight(PiceceColor.BLACK, new Position(0,1));
        board[0][6] = new Knight(PiceceColor.BLACK, new Position(0,6));
        board[7][1] = new Knight(PiceceColor.WHITE, new Position(7,1));
        board[7][6] = new Knight(PiceceColor.WHITE, new Position(7,6));

        //place bishops
        board[0][2] = new Bishop(PiceceColor.BLACK, new Position(0,2));
        board[0][5] = new Bishop(PiceceColor.BLACK, new Position(0,5));
        board[7][2] = new Bishop(PiceceColor.WHITE, new Position(7,2));
        board[7][5] = new Bishop(PiceceColor.WHITE, new Position(7,5));

        //place queens
        board[0][3] = new Queen(PiceceColor.BLACK, new Position(0,3));
        board[7][3] = new Queen(PiceceColor.WHITE, new Position(7,3));

        //place kings
        board[0][4] = new King(PiceceColor.BLACK, new Position(0,4));
        board[7][4] = new King(PiceceColor.WHITE, new Position(7,4));

        //place pawns with the help of for loop
        for(int i = 0; i < 8; 1++){
            board[1][i] = new Pawn(piceColor.BLACK, new Position(1,i));
            board[6][i] = new Pawn(piceColor.WHITE, new Position(6,i));
        }
        public void movePiece(Position start, Position end){
            if (board[start.getRow()][start.getColumn()] != null && board[start.getRow()][start.getColumn()].isValidMove(end, board)) {
                board[end.getRow()][end.getColumn()] = board[start.getRow()][start.getColumn()];
                board[end.getRow()][end.getColumn()].setPosition(end);
                board[start.getRow()][start.getColumn()] = null;
            }
        }
    }
}{
}
