# Tron Multiplayer Project

## Overview
This project involves studying the performance of Maxn and Paranoid algorithms for decision-making in the Tron multiplayer game. The goal is to evaluate the effectiveness of different game algorithms through statistical analysis in various game configurations.

## Team
- A. Catherine ATTY
- Sékou DOUMBOUYA
- Manne Emile KITSOUKOU
- Amirath Fara OROU-GUIDOU

## Supervisor
- LEHEMBRE Etienne

## Date
- April 7, 2023

## Project Structure
The project is organized using the MVC (Model-View-Controller) architecture.

### Model
Contains classes representing game elements such as players and the game grid.

### View
Responsible for the graphical display of the game and user interactions.

### Core
Includes executable classes to run the game.

### Algorithms
Contains adversarial search algorithms and heuristics.

## Key Algorithms
### Maxn
An adversarial search algorithm used for multi-player games.

### Paranoid
Another adversarial search algorithm that assumes all other players are collaborating against the current player.

## Heuristics
### OpenSpace
Calculates the number of empty cells surrounding a player.

### GASLAP (Go As Long As Possible)
Determines the maximum distance a player can move in a given direction without obstacles.

### Voronoï
Divides the game space into regions based on proximity to players, used to determine influence zones.

### Checker
Uses a checkerboard pattern to optimize the influence zones of players.

## Experimental Setup
### Test Cases
Evaluated different algorithm configurations in a three-player game setting.

### Methodology
Repeated each experiment multiple times to ensure reliable results.

### Results
Increasing the search depth improves performance up to a point, after which it decreases due to computational complexity.

## Conclusion
The Paranoid algorithm shows competitive performance in multiplayer settings, sometimes surpassing the Maxn algorithm.

## Future Work
Future research could explore the performance of these algorithms in other multiplayer games like Tron.

## How to Run

### Prerequisites
- Java JDK (version 8 or higher)
- Git
- An IDE such as IntelliJ IDEA or Eclipse (optional)

### Installation

1. **Clone the repository**:
   ```sh
   git clone git@github.com:VotreNomUtilisateur/TronGame.git
   cd TronGame

### Compile
   ```sh
   ant compile
   ant run
