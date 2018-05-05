package cz.cvut.fel.pjv.JavaFXGUI;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.Tile;
import cz.cvut.fel.pjv.engine.pieces.Piece;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    private static Tile sourceTile;
    private static Tile destinationTile;
    private static Piece movedPiece;
    private static GUIBoard GUIBoard;
    private static Board engineBoard;

    public static void main(String[] args) {
        launch(args);
    }

    static void redrawBoard(Board engineBoard) {
        GUIBoard.clearBoard();
        engineBoard.recalculate();
        GUIBoard.setBoard(engineBoard);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chess");
        primaryStage.getIcons().add( new Image("images/other/icon.png") );
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,600,500);
        primaryStage.setScene(scene);
        scene.getStylesheets().add("stylesheet.css");
        GUIBoard = new GUIBoard();
        engineBoard = new Board();
        GUIBoard.setBoard(engineBoard);
        root.setCenter(GUIBoard);
        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menubar = new MenuBar();
        Menu gameMenu = new Menu("Game");
        menubar.getMenus().add(gameMenu);
        MenuItem menuItemQuit = new MenuItem("Quit");
        menuItemQuit.setOnAction(e -> onQuit());
        gameMenu.getItems().add(menuItemQuit);
        return menubar;
    }

    private void onQuit() {
        Platform.exit();
        System.exit(0);
    }

    public static GUIBoard getGUIBoard() {
        return GUIBoard;
    }

    public static void setGUIBoard(GUIBoard GUIBoard) {
        Main.GUIBoard = GUIBoard;
    }

    static Tile getSourceTile() {
        return sourceTile;
    }

    static void setSourceTile(Tile sourceTile) {
        Main.sourceTile = sourceTile;
    }

    static Piece getMovedPiece() {
        return movedPiece;
    }

    static void setDestinationTile(Tile destinationTile) {
        Main.destinationTile = destinationTile;
    }

    static Tile getDestinationTile() {
        return destinationTile;

    }

    public static Board getEngineBoard() {
        return engineBoard;
    }

    public static void setEngineBoard(Board engineBoard) {
        Main.engineBoard = engineBoard;
    }

    static void setMovedPiece(Piece movedPiece) {
        Main.movedPiece = movedPiece;
    }
}
