import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ChessGameGUI extends JFrame{
    private final ChessSquareComponent[][] squares = new ChessSquareComponent[8][8];
    private final ChessGame game = new ChessGame();

    private final Map<Class<? extends Piece>, String> pieceUnicodeMap = new HashMap<>(){
        put(Pawn.class, "\u265F");
        put(Rook.class, "\u265C");
        put(Knight.class, "\u265E");
        put(Bishop.class, "\u265D");
        put(Queen.class, "\u265B");
        put(King.class, "\u265A");
    }
};

public ChessGameGUI(){
    setTitle("Chess");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridLayout(8,8));
    intializeBoard();
    addGameResetOption();
    pack();
    setVisible(true);
}
private void intializeBoard(){
    for (int row = 0; row < squares.length; row++){
        for(int col = 0; col < squares[row].length; col++){
            final int finalRow = row;
            final int finalCol = col;
            ChessSquareComponent square = new ChessSquareComponent(row, col);
            square.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    handleSquareClick(finalRow, finalCol);
                }
            });
            add(square);
            squares[row][col] = square;
        }
    }
    refreshBoard();
}
private void refreshBoard(){
    ChessBoard board = game.getBoard();
    for(int row = 0; row < 8; row++){
        for (int col = 0; col < 8; col++){
            Piece piece = board.getPiece(row, col);
            if (piece != null){
                String symbol = pieceUnicodeMap.get(piece.getClass());
                Color color = (piece.getColor() == PieceColor.WHITE) ? Color.WHITE : Color.BLACK;
                squares[row][col].setPieceSymbol(symbol, color);
            }
            else {
                squares[row][col].clearPieceSymbol();
            }
        }
    }
}
private void handleSquareClick(int row, int col){
    boolean moveResult = game.handleSquareSelection(row, col);
    clearHighlights();
    if(moveResult){
        refreshBoard();
        checkGameState();
        checkGameOver();
    } else if (game.isPieceSelected()) {
        highlightLegalMoves(new Position(row, col));
    }
    refreshBoard();
}
private void checkGameState() {
    PieceColor currentPlayer = game.getCurrentPlayerColor();
    boolean inCheck = game.isInCheck(currentPlayer);

    if (inCheck){
        JOptionPane.showMessageDialog(this, currentPlayer + " is in check!");
    }
}
public static void main(String[] args){
    SwingUtilities.invokeLater(ChesssGameGUI::new);
}
private void higlightLegalMoves(Position position){
    List<Position> legalMoves = game.getLegalMovesForPieceAt(position);
    for (Position move : legalMoves){
        squares[move.getRow()][move.getColumn()].setBackground(Color.GREEN);
    }
}
private void clearHighlights() {
    for(int row = 0; row < 8; row++){
        for(int col = 0; col < 8; col++){
            squares[row][col].setBackground((row+col) % 2 == 0 ? Color.LIGHT_GRAY : new Color(205, 133, 63));
        }
    }
}
private void addGameResetOption(){
    JMenuBar menuBar = new JMenuBar();
    JMenu gameMenu = new JMenu("Game");
    JMenuItem resetItem = new JMenuItem("Reset");
    resetItem.addMenuKeyListener(e -> resetGame());
    gameMenu.add(resetItem);
    menuBar.add(gameMenu);
    setJMenuBar(menuBar);
}
private void resetGame(){
    game.resetGame();
    refreshBoard();
}
private void checkGameOver(){
    if(game.isCheckmate(game.getCurrentPlayerColor())){
        int response = JOptionPane.showConfirmDialog(this, "Checkmate! You NOOB, would you like to play again and lose?", "Game over", JOptionPane.YES_NO_OPTION);
        if(response == JOptionPane.YES_OPTION){
            resetGame();
        }
        else {
            System.exit(0);
        }
    }
}