# Slotmachine
This project would implement a slotmachine game. Throughout a web interface you will receive three cards and if they are equal you will win

## State-of-the-art implementation
### the slotmachine
The slotmachine is implemented by a State Pattern. There are four states and every state can execute one or more operations to make the slotmachine change to a new state.

![Alt text](/slotmachineStateDiagram.png?raw=true "Slotmachine State Diagram")

How you can seen from the above picture, the edges are the operation that a state can execute. 

The real game is done in the GAME state. It has injected a Croupier object which knows the game rules. Actual croupier implementation can give you three random cards from a sixteen-card pack.
If the croupier give you three equal cards you win. You will have three change to win.

![Alt text](/slotmachineClassDiagram.png?raw=true "Slotmachine Class Diagram")

### the controller
The controller is implemented like a REST service. It exposes the method to make the slotmachine change state. It implements a Template Pattern to execute the operation.
![Alt text](/templateControllerClassDiagram.png?raw=true "Controller Template Class Diagram")


## Techonlogy
The project uses Spring Core, Spring Boot, Rest API with Spring MVC

## To-do List



