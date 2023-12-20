### README for the Final Project ECE 3330 Spring 2022 Repository

#### Repository Overview
This repository, located at [SamNicklez/finalprojectece3330spring2022](https://github.com/SamNicklez/finalprojectece3330spring2022)

#### Project Description
The coding project is a "WordleTournament" game with the following features:

- A 5-round game based on the popular Wordle game by NYT, with the first 3 rounds being "easier" and the last 2 rounds "hard".
- Word difficulty is determined by a machine learning-created database, with points awarded based on the word's complexity and the number of attempts taken to guess it.
- Users must log in with a unique username that only contains lowercase letters; this username is used for tracking lifetime statistics such as scores and win streaks.
- The game adheres to the standard Wordle rules and includes a GUI for inputting letters using an on-screen or computer keyboard. The letters change color if they exist in the word, similar to the original game.
- All user-inputted words are verified against a word bank, and invalid attempts are rejected.
- Messages are displayed after each round, and if the word is not guessed within 6 attempts, the score will be 0.
- An all-time leaderboard is available without login, displaying the top 5 users with the highest total scores.
- A daily leaderboard shows the top 5 scores of the current day and past days but requires a username to access.
- A player lookup feature allows users to search for other players' total points, current win streak, etc.

- **Client and Server Architecture**: Files like [`Client.java`](https://github.com/SamNicklez/finalprojectece3330spring2022/blob/main/src/Client.java) and [`Server.java`](https://github.com/SamNicklez/finalprojectece3330spring2022/blob/main/src/Server.java) suggest a networked application with client-server communication.
- **Wordle Game Implementation**: Files such as [`WordleModel.java`](https://github.com/SamNicklez/finalprojectece3330spring2022/blob/main/src/WordleModel.java) and [`WordleController.java`](https://github.com/SamNicklez/finalprojectece3330spring2022/blob/main/src/WordleController.java)
- **GUI Controllers**: Several controller files like [`AllTimeLeaderboardController.java`](https://github.com/SamNicklez/finalprojectece3330spring2022/blob/main/src/AllTimeLeaderboardController.java) and [`LeaderboardController.java`](https://github.com/SamNicklez/finalprojectece3330spring2022/blob/main/src/LeaderboardController.java) shows the user interface with features like leaderboards.
- **FXML Layouts**: FXML files such as [`WordleMain.fxml`](https://github.com/SamNicklez/finalprojectece3330spring2022/blob/main/src/fxml/WordleMain.fxml) are used for defining the GUI layout in JavaFX.

#### Getting Started

1. Clone the repository.
2. Open the project in an IDE that supports Java, like IntelliJ IDEA or Eclipse.
3. Resolve any dependencies, possibly related to JavaFX for GUI components.
4. Run the main classes (`ClientDriver.java` and `ServerDriver.java`) to start the application.

#### Documentation
This repository includes several PDF documents and a PowerPoint presentation, which also contains developer documentation, an executive summary, and a user guide. These documents can provide more context and detailed information about the project.

- [Developer Documentation](https://github.com/SamNicklez/finalprojectece3330spring2022/blob/main/SWDTeam5DeveloperDocumentation.pdf)
- [Executive Summary](https://github.com/SamNicklez/finalprojectece3330spring2022/blob/main/SWDTeam5ExecutiveSummary.pdf)
- [User Guide](https://github.com/SamNicklez/finalprojectece3330spring2022/blob/main/SWDTeam5UserGuide.pdf)
- [Finalist Presentation](https://github.com/SamNicklez/finalprojectece3330spring2022/blob/main/Finalist%20Presentation.pptx)
