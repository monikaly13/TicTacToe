# Tic Tac Toe Game - FXGL & JavaFX

A modern 2D Tic Tac Toe game built with FXGL and JavaFX. This game supported two players gameplay with smooth UI transitions, persistent score tracking and user-friendly interface.

## Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Screenshots](#screenshots)
- [Installation & Setup](#installation--setup)
- [How to Run](#how-to-run)
- [Code Overview](#code-Overview)
- [Game Mechanics](#game-mechanics)
- [AI Disclosure](#ai-disclosure)
- [Release & Distribution](#release--distribution)
- [Group Members](#group-members)

---

## Features

### Core Gameplay
- **Two-Players Mode**: Play as X and O with automatic turn switching.
- **Win Detection**: Automatically detects win across rows, columns, and diagonals.
- **Tie Detection**: Identifies when the board is full with no winner.
- **Score Tracking**: Persistent win/tie tracking throughout the session.
- **Smooth Animations**: Message overlays and transitions for game events.

### UI/UX Features
- **Modern Dark Theme**: Minimalist dark interface (#0F0F0F) with blue accents (#3B82F6)
- **Home Screen**: Start game or exit with simple menu.
- **Score Display**: Updates winning or ties instantly during gameplay.
- **Game Messages**: 2-seconds overlay messages showing game results.
- **Dialog System**: Option to resume or return to home page after game completion.
- **Responsive Layout**: 600x600px window with properly aligned components.

### Technical Features
- **FXGL Integration**: Uses GameApplication framework for game lifecycle.
- **JavaFX Styling**: Custom CSS-like button and layout styling.
- **Event Handling**: Efficient click detection with early returns for invalid moves.
- **State Management**: Separate board data structure from UI representation.

---

## Project Structure

```
tic-tac-toe-game/
├── src/main/java/org/example/com/
│   ├── TicTacToeApp.java          # Main game application
│   ├── GameLogic.java              # Win/tie detection logic (enhanced)
│   └── AIPlayer.java               # AI opponent implementation (enhanced)
├── resources/
│   └── styles/                    
├── README.md                        # Project documentation
├── pom.xml                          # Maven configuration
├── .gitignore                       # Git ignore file
└── releases/
    └── tic-tac-toe-game-1.0.jar    # Runnable JAR release
```

---

## Screenshots

### Home Page
<p align="center">
  <img src="https://github.com/user-attachments/assets/b3b0c654-c21c-4f2a-9a7d-eb319c75613c" width="350">
</p>
<em>Clean home page with Start Game and Exit buttons</em>

### Gameplay
<p align="center">
  <img src="https://github.com/user-attachments/assets/b4a749c9-727c-476b-af79-9e2e4307c2c8" width="350">
</p>
<em>Active game with score tracking and playable grid</em>

### Game Over
<p align="center">
  <img src="https://github.com/user-attachments/assets/1203ec3e-ff14-4941-9d9c-67aa7ccc8309" width="350">
</p>
<em>Result overlay with win/tie message</em>

### Resume Dialog
<p align="center">
  <img src="https://github.com/user-attachments/assets/0b1cc4f3-20c7-4ea2-99fd-af77f15f725e" width="350">
</p>
<em>Dialog to resume game or return to home</em>


---

## Installation & Setup

### Prerequisites
- **Java 11 or higher**
- **Maven 3.6+**
- **Git**

### Step 1: Clone the Repository
```bash
git clone https://github.com/[your-username]/tic-tac-toe-game.git
cd tic-tac-toe-game
```

### Step 2: Install Dependencies
The project uses Maven. FXGL and JavaFX dependencies are defined in `pom.xml`:

```bash
mvn clean install
```

**Main Dependencies:**
- `com.almasb:fxgl-all:22.7` - FXGL Game Development Framework
- `org.openjfx:javafx-*:22.0.2` - JavaFX libraries (graphics, controls, etc.)
- `junit:junit:4.13.2` - Testing framework

### Step 3: Build the Project
```bash
mvn clean package
```

### Step 4: Run the Game
```bash
# Using Maven
mvn javafx:run

# Or Using the JAR File
java -jar target/tic-tac-toe-game-1.0.jar
```

---

## How to Run

1. **Start the Game**
   - Run the application and click "Start Game"
   - You'll see the 3x3 game board with score tracking

2. **Play**
   - Click on any empty cell to place your mark (X or O)
   - Players alternate automatically
   - Game ends when someone wins or the board fills up

3. **View Results**
   - A 2-seconds overlay shows the game result
   - Board automatically resets for the next game
   - Scores persist throughout the session

4. **Return to Home**
   - Click "Back" during gameplay
   - Choose "Back to Home" to return to start screen (scores persist)
   - Or click "Resume Game" to continue playing

5. **Exit**
   - Click "Exit" from the home screen to close the application

---

## Code Explanation

### Game Flow

#### 1. **Initialization** (`initSettings` & `initUI`)
```
GameApplication.init()
├── Set window properties (600x600)
├── Create Home Screen UI
├── Create Game UI Components
└── Initialize game state
```

The game uses FXGL's `GameApplication` framework which manages:
- Window configuration
- Scene management
- UI node layering
- Game lifecycle events

#### 2. **Home Screen**
The `startBox` VBox contains:
- Title label ("Tic Tac Toe")
- Subtitle
- Start button (triggers `startGame()`)
- Exit button (closes application)

#### 3. **Game Start** (`startGame()`)
When player click "Start Game":
1. Home screen UI is removed
2. Game UI is added (score box, grid, buttons)
3. Board state is initialized to empty
4. Scores are reset for the new session

#### 4. **Gameplay Loop** (`handleClick()`)
When a player clicks a cell:
```
Click Cell (row, col)
├── Check if cell is empty
├── Check if overlay message is visible (prevent clicks)
├── Place mark on board
├── Check for win condition
│  ├── If win: increment score, show message, return
│  └── Else: continue
├── Check for tie condition
│  ├── If tie: increment score, show message, return
│  └── Else: continue
└── Switch current player (X ↔ O)
```

#### 5. **Win Detection** (`checkWin()`)
Checks all winning conditions:
- **3 Rows**: `boardData[i][0] == boardData[i][1] == boardData[i][2]`
- **3 Columns**: `boardData[0][j] == boardData[1][j] == boardData[2][j]`
- **2 Diagonals**: Main diagonal and anti-diagonal

#### 6. **Message Display** (`showMessage()`)
When game ends:
1. Message overlay is made visible
2. `PauseTransition` waits 2 seconds
3. After delay: message hidden, board reset

#### 7. **Game State Management**
**Board Data**: `char[][] boardData[3][3]`
- `'\0'` (null char) = empty cell
- `'X'` = X's mark
- `'O'` = O's mark

**UI Buttons**: `Button[][] gridButtons[3][3]`
- Syncs with board data on each click
- Display is updated via `setText()`

### Key Classes & Methods

| Component | Purpose |
|-----------|---------|
| `TicTacToeApp` | Main game application class |
| `initSettings()` | Configure game window properties |
| `initUI()` | Build all UI components |
| `startGame()` | Transition from home to gameplay |
| `handleClick()` | Process player moves |
| `checkWin()` | Determine win conditions |
| `isFull()` | Check for tie condition |
| `updateScores()` | Refresh score display |
| `showMessage()` | Display result and reset board |
| `resetBoard()` | Clear game state |
| `handleBack()` | Show game end dialog |
| `returnToHome()` | Transition back to home screen |

---

## Game Mechanics

### Turn System
- Game starts with player **X**
- After each valid move, players alternate: X → O → X → ...
- Invalid moves (clicking occupied cells) don't change turns

### Win Conditions
A player wins by getting **3 of their marks** in:
- Any complete row
- Any complete column
- Either diagonal

### Tie Condition
- If all 9 cells are filled and no player has won, the game is a **tie**
- Scores are updated and board resets

### Score Persistence
- Scores persist throughout the **entire game session** (until application exits)
- Each round resets the board but keeps scores
- Returning to home and restarting doesn't reset scores

---

## Enhanced Features (v2.0+)

### Future Enhancements
The codebase is structured for easy expansion:

1. **Computer Opponent**
   - Minimax algorithm for unbeatable AI
   - Difficulty levels (Easy, Medium, Hard)

2. **Sound Effects**
   - Move placed sound
   - Win/tie celebration sounds
   - Background music option

3. **Animations**
   - Cell click animations
   - Score counter animations
   - Win celebration effects

4. **Additional Game Modes**
   - Single-player vs Computer
   - Leaderboard tracking
   - Difficulty settings

---

## AI Disclosure

**AI Usage**: This project was enhanced with guidance from AI assistants for:
- Code structure and architecture recommendations
- FXGL framework best practices
- UI/UX layout suggestions
- Documentation and README generation
- Potential enhancement strategies

**Human-Created**: 
- Core game logic implementation
- UI component styling and layout
- Game flow and event handling
- Testing and debugging

**No AI Code Generation**: All code was written by human, with AI providing architectural guidance only.

---

## Release & Distribution

### Building a Runnable JAR

1. **Create Assembly Configuration** (`pom.xml`):
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-assembly-plugin</artifactId>
    <version>3.3.0</version>
    <configuration>
        <archive>
            <manifest>
                <mainClass>org.example.com.TicTacToeApp</mainClass>
            </manifest>
        </archive>
        <descriptorRefs>
            <descriptorRef>jar-with-dependencies</descriptorRef>
        </descriptorRefs>
        <finalName>tic-tac-toe-game</finalName>
        <appendAssemblyId>false</appendAssemblyId>
    </configuration>
</plugin>
```

2. **Build JAR**:
```bash
mvn clean assembly:assembly
```

3. **Create GitHub Release**:
- Go to Releases → Create New Release
- Tag: `v1.0`
- Upload: `target/tic-tac-toe-game.jar`
- Include Windows/Mac/Linux launch instructions

4. **Run Released JAR**:
```bash
java -jar tic-tac-toe-game.jar
```

---

## Group Members

| Member | GitHub | Role |
|--------|--------|------|
| [Monika Ly] | [@monikaly13](https://github.com/monikaly13) | Game Logic & Testing
| [Pechvolak Heng] | [@Pech4-star](https://github.com/Pech4-star) | UI/UX & Styling 
| [Emi Sopheak] | [@ayyy-1e](https://github.com/ayyy-1e) | Core Features & Documentation 

---

## Getting Started for Contributors

1. **Clone & Setup**:
```bash
git clone https://github.com/[org]/tic-tac-toe-game.git
cd tic-tac-toe-game
mvn clean install
```

2. **Create Feature Branch**:
```bash
git checkout -b feature/your-feature-name
```

3. **Make Changes & Commit**:
```bash
git add .
git commit -m "feat: add new feature description"
git push origin feature/your-feature-name
```

4. **Submit Pull Request**:
- Describe changes in PR
- Link any related issues
- Wait for review and merge

---

## License

This project is licensed under the MIT License. See LICENSE file for details.

---

## Troubleshooting

### Issue: Game does not start
**Solution**: Ensure Java 11+ and JavaFX 22+ are installed:
```bash
java -version
```

### Issue: Maven dependency errors
**Solution**: Clear Maven cache and reinstall:
```bash
mvn clean
rm -rf ~/.m2/repository
mvn install
```

### Issue: JAR won't run
**Solution**: Make sure you're using the correct Java version:
```bash
java -version
java -jar target/tic-tac-toe-game.jar
```

---

## References

- [FXGL Documentation](https://github.com/AlmasB/FXGL)
- [JavaFX Documentation](https://openjfx.io/)
- [Maven Documentation](https://maven.apache.org/)

---

**Last Updated**: February 2026  
**Version**: 1.0  
**Status**: Ready for Production
