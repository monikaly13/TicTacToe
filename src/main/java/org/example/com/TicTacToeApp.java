package org.example.com;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.shape.Line;

import static com.almasb.fxgl.dsl.FXGL.*;

public class TicTacToeApp extends GameApplication {

    private int xWins = 0;
    private int oWins = 0;
    private int ties = 0;

    private char[][] boardData = new char[3][3];
    private Button[][] gridButtons = new Button[3][3];
    private char currentPlayer = 'X';

    private VBox startBox;
    private HBox scoreBox;
    private GridPane gameGrid;
    private Button backButton;
    private Label messageOverlay;
    private VBox gameEndDialog;
    private Button playAgainBtn;
    private Button backToHomeBtn;
    private Label gameEndMessage;

    private Label xLabel;
    private Label oLabel;
    private Label tieLabel;
    private Line winningLine;
    private Label scoreLabel;  // Add at class level

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Tic Tac Toe");
        settings.setWidth(600);
        settings.setHeight(600);
    }



    @Override
    protected void initUI() {

        getGameScene().setBackgroundColor(Color.web("#0F0F0F"));

        // Instead of displaying scores in a separate area:
        Label xWinsLabel = new Label("X Wins: " + xWins);
        Label oWinsLabel = new Label("O Wins: " + oWins);
        Label tiesLabel = new Label("Ties: " + ties);

    // PUT IT IN THE GRID TITLE AREA:
        Label scoreTitle = new Label("X Wins: " + xWins + " | O Wins: " + oWins + " | Ties: " + ties);
        scoreTitle.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
    // Add this label ABOVE your gridButtons, not in a separate pane

        // Start page - Modern v0 style
        Label title = new Label("Tic Tac Toe");
        title.setFont(new Font("Segoe UI", 60));
        title.setTextFill(Color.web("#FFFFFF"));
        title.setStyle("-fx-font-weight: bold;");

        Label subtitle = new Label("Start Tic Tac Toe");
        subtitle.setFont(new Font("Segoe UI", 16));
        subtitle.setTextFill(Color.web("#A0A0A0"));

        Button startBtn = new Button("Start Game");
        startBtn.setPrefSize(220, 50);
        startBtn.setStyle("-fx-background-color: #3B82F6; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-padding: 10; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;");

        Button exitBtn = new Button("Exit");
        exitBtn.setPrefSize(220, 50);
        exitBtn.setStyle("-fx-background-color: #EF4444; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-padding: 10; -fx-border-radius: 8; -fx-background-radius: 8; -fx-cursor: hand;");



        startBox = new VBox(30, title, subtitle, startBtn, exitBtn);
        startBox.setAlignment(Pos.CENTER);
        startBox.layoutXProperty().bind(
                getGameScene().getRoot().widthProperty().subtract(startBox.widthProperty()).divide(2)
        );
        startBox.layoutYProperty().bind(
                getGameScene().getRoot().heightProperty().subtract(startBox.heightProperty()).divide(2)
        );
        startBox.setStyle("-fx-padding: 40;");

        getGameScene().addUINode(startBox);

        startBtn.setOnAction(e -> startGame());
        exitBtn.setOnAction(e -> getGameController().exit());

        scoreLabel = new Label("X Wins: " + xWins + " | O Wins: " + oWins + " | Ties: " + ties);
        scoreLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
// Add to gameGrid BEFORE the button grid
        // Prepare game UI - Score display
        xLabel = new Label("X Wins: 0");
        xLabel.setFont(new Font("Segoe UI", 16));
        xLabel.setTextFill(Color.web("#FFFFFF"));
        xLabel.setStyle("-fx-font-weight: bold;");

        oLabel = new Label("O Wins: 0");
        oLabel.setFont(new Font("Segoe UI", 16));
        oLabel.setTextFill(Color.web("#FFFFFF"));
        oLabel.setStyle("-fx-font-weight: bold;");

        tieLabel = new Label("Ties: 0");
        tieLabel.setFont(new Font("Segoe UI", 16));
        tieLabel.setTextFill(Color.web("#A0A0A0"));
        tieLabel.setStyle("-fx-font-weight: bold;");

        scoreBox = new HBox(50, xLabel, oLabel, tieLabel);
        scoreBox.setAlignment(Pos.CENTER);
        scoreBox.layoutXProperty().bind(
                getGameScene().getRoot().widthProperty().subtract(scoreBox.widthProperty()).divide(2)
        );
        scoreBox.setLayoutY(20);
        scoreBox.setStyle("-fx-padding: 20; -fx-background-color: #1A1A1A; -fx-border-color: #404040; -fx-border-width: 2;");

        // Game grid with border
        gameGrid = new GridPane();
        gameGrid.setHgap(2);
        gameGrid.setVgap(2);
        gameGrid.layoutXProperty().bind(
                getGameScene().getRoot().widthProperty().subtract(gameGrid.widthProperty()).divide(2)
        );
        gameGrid.layoutYProperty().bind(
                getGameScene().getRoot().heightProperty().subtract(gameGrid.heightProperty()).divide(2)
        );
        gameGrid.setStyle(
                "-fx-padding: 4;" +
                        "-fx-background-color: #ece8e8;" +
                        "-fx-border-color: #FFFFFF;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 4;" +
                        "-fx-background-radius: 4;"
        );

        // Grid buttons with borders
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button("");
                btn.setPrefSize(100, 100);
                btn.setStyle(
                        "-fx-background-color: #150909;" +
                                "-fx-font-size: 48;" +
                                "-fx-text-fill: #ece8e8;" +
                                "-fx-font-weight: bold;" +
                                "-fx-cursor: hand;" +
                                "-fx-padding: 0;" +
                                "-fx-border-color: #FFFFFF;" +
                                "-fx-border-width: 1;" +
                                "-fx-border-radius: 0;" +
                                "-fx-background-radius: 0;"
                );
                int row = i, col = j;
                btn.setOnAction(e -> handleClick(row, col));
                gridButtons[i][j] = btn;
                gameGrid.add(btn, j, i);
            }
        }

        backButton = new Button("Back");
        backButton.setPrefSize(120, 45);
        backButton.setStyle("-fx-background-color: #404040; -fx-text-fill: #FFFFFF; -fx-font-size: 14; -fx-font-weight: bold; -fx-border-radius: 0; -fx-background-radius: 0; -fx-cursor: hand; -fx-border-width: 0;");
        backButton.setLayoutX(240);
        backButton.setLayoutY(530);
        backButton.setOnAction(e -> handleBack());

        messageOverlay = new Label();
        messageOverlay.setFont(new Font("Segoe UI", 44));
        messageOverlay.setTextFill(Color.web("#FFFFFF"));
        messageOverlay.setStyle("-fx-font-weight: bold;");
        // Make overlay cover the grid
        // Position message above the grid instead of covering it
        messageOverlay.prefWidthProperty().bind(gameGrid.widthProperty());

        messageOverlay.layoutXProperty().bind(gameGrid.layoutXProperty());

// Move the message above the grid
        messageOverlay.layoutYProperty().bind(
                gameGrid.layoutYProperty().subtract(40)
        );

        messageOverlay.setAlignment(Pos.BOTTOM_CENTER);
        messageOverlay.setStyle("-fx-border-radius: 5; -fx-background-radius: 5; -fx-text-fill: white; -fx-font-size: 22; -fx-font-weight: bold; -fx-border-width: 0; -fx-border-color: #FFFFFF;");
        messageOverlay.setVisible(false);

        // Game End Dialog
        gameEndMessage = new Label();
        gameEndMessage.setFont(new Font("Segoe UI", 28));
        gameEndMessage.setTextFill(Color.web("#FFFFFF"));
        gameEndMessage.setStyle("-fx-font-weight: bold;");

        Button resumeBtn = new Button("Resume Game");
        resumeBtn.setPrefSize(150, 50);
        resumeBtn.setStyle("-fx-background-color: #2563EB; -fx-text-fill: #FFFFFF; -fx-font-size: 14; -fx-font-weight: bold; -fx-border-radius: 0; -fx-background-radius: 0; -fx-cursor: hand; -fx-border-width: 0;");
        resumeBtn.setOnAction(e -> handleResumeGame());

        backToHomeBtn = new Button("Back to Home");
        backToHomeBtn.setPrefSize(150, 50);
        backToHomeBtn.setStyle("-fx-background-color: #666666; -fx-text-fill: #FFFFFF; -fx-font-size: 14; -fx-font-weight: bold; -fx-border-radius: 0; -fx-background-radius: 0; -fx-cursor: hand; -fx-border-width: 0;");
        backToHomeBtn.setOnAction(e -> handleBackToHome());

        HBox dialogButtons = new HBox(20, resumeBtn, backToHomeBtn);
        dialogButtons.setAlignment(Pos.CENTER);

        gameEndDialog = new VBox(30, gameEndMessage, dialogButtons);
        gameEndDialog.setAlignment(Pos.CENTER);
        gameEndDialog.layoutXProperty().bind(
                getGameScene().getRoot().widthProperty().subtract(gameEndDialog.widthProperty()).divide(2)
        );
        gameEndDialog.layoutYProperty().bind(
                getGameScene().getRoot().heightProperty().subtract(gameEndDialog.heightProperty()).divide(2)
        );
        gameEndDialog.setPrefSize(330, 240);
        gameEndDialog.setStyle("-fx-background-color: ; -fx-border-radius: 0; -fx-background-radius: 0; -fx-padding: 40; -fx-border-color: #FFFFFF; -fx-border-width: 2;");
        gameEndDialog.setVisible(false);
    }

    private void startGame() {
        getGameScene().removeUINode(startBox);
        getGameScene().addUINode(scoreBox);
        getGameScene().addUINode(gameGrid);
        getGameScene().addUINode(messageOverlay);
        getGameScene().addUINode(gameEndDialog);
        getGameScene().addUINode(backButton);

        resetScoresForNewSession();
        resetBoard();
        messageOverlay.setVisible(false);
        gameEndDialog.setVisible(false);
        updateScores();
    }

    private void resetBoard() {
        currentPlayer = 'X';
        if (winningLine != null) {
            getGameScene().removeUINode(winningLine);
            winningLine = null;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardData[i][j] = '\0';
                gridButtons[i][j].setText("");
            }
        }
    }

    private void handleClick(int row, int col) {


        boardData[row][col] = currentPlayer;
        gridButtons[row][col].setText(String.valueOf(currentPlayer));
        if (checkWin(currentPlayer)) {
            if (currentPlayer == 'X') xWins++;
            else oWins++;
            updateScores();
            String winMessage = currentPlayer == 'X' ? "X Wins!" : "O Wins!";
            showMessage(winMessage);
            return;
        }
        if (isFull()) {
            ties++;
            updateScores();
            showMessage("It's a Tie!");
            return;
        }
        currentPlayer = currentPlayer == 'X' ? 'O' : 'X';
    }

    private void drawWinningLine(int startRow, int startCol, int endRow, int endCol, Boolean isHorizontal) {
        // Calculate pixel coordinates of the cells
        double cellSize = 100; // Button size
        double gap = 2;        // Grid gap
        double startX = gameGrid.getLayoutX() + startCol * (cellSize + gap) + cellSize / 2 + 4;
        double startY = gameGrid.getLayoutY() + startRow * (cellSize + gap) + cellSize / 2 + 4;
        double endX = gameGrid.getLayoutX() + endCol * (cellSize + gap) + cellSize / 2 + 4;
        double endY = gameGrid.getLayoutY() + endRow * (cellSize + gap) + cellSize / 2 + 4;

        // Remove previous line if exists
        if (winningLine != null) {
            getGameScene().removeUINode(winningLine);
        }

        // Create new line
        winningLine = new Line(startX, startY, endX, endY);
        winningLine.setStroke(Color.web("White"));  // Green line
        winningLine.setStrokeWidth(5);

        getGameScene().addUINode(winningLine);
    }

    private boolean checkWin(char p) {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (boardData[i][0] == p && boardData[i][1] == p && boardData[i][2] == p) {
                drawWinningLine(i, 0, i, 2, true);  // true = horizontal
                return true;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (boardData[0][i] == p && boardData[1][i] == p && boardData[2][i] == p) {
                drawWinningLine(0, i, 2, i, false); // false = vertical
                return true;
            }
        }

        // Check diagonal (top-left to bottom-right)
        if (boardData[0][0] == p && boardData[1][1] == p && boardData[2][2] == p) {
            drawWinningLine(0, 0, 2, 2, null);     // null = diagonal
            return true;
        }

        // Check anti-diagonal (top-right to bottom-left)
        if (boardData[0][2] == p && boardData[1][1] == p && boardData[2][0] == p) {
            drawWinningLine(0, 2, 2, 0, null);
            return true;
        }

        return false;
    }

    private boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardData[i][j] == '\0') return false;
            }
        }
        return true;
    }


    private void updateScores() {
        xLabel.setText("X Wins: " + xWins);
        oLabel.setText("O Wins: " + oWins);
        tieLabel.setText("Ties: " + ties);
    }

    private void showMessage(String text) {
        messageOverlay.setText(text);
        messageOverlay.setVisible(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            messageOverlay.setVisible(false);
            resetBoard();
        });
        pause.play();
    }

    private void handleResumeGame() {
        gameEndDialog.setVisible(false);
    }

    private void handleBackToHome() {
        gameEndDialog.setVisible(false);
        returnToHome();
    }

    private void handleBack() {
        gameEndMessage.setText("Leave the Game?");
        gameEndDialog.setVisible(true);
    }

    private void returnToHome() {
        messageOverlay.setVisible(false);
        gameEndDialog.setVisible(false);

        getGameScene().removeUINode(scoreBox);
        getGameScene().removeUINode(gameGrid);
        getGameScene().removeUINode(messageOverlay);
        getGameScene().removeUINode(gameEndDialog);
        getGameScene().removeUINode(backButton);

        getGameScene().addUINode(startBox);
    }


    private void resetScoresForNewSession() {
        xWins = 0;
        oWins = 0;
        ties = 0;
        updateScores();
    }

    public static void main(String[] args) {
        launch(args);
    }
}