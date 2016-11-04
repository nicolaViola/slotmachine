# Slotmachine
This project implements a slotmachine game. Through a web interface you will receive three cards and if they are equal you will win

## State-of-the-art
### slotmachine implementation
The slotmachine is implemented by a State Pattern. There are four states and every state can execute one or more operations to make the slotmachine change to a new state.
When the slotmachine starts, it is in a NoCoin state.

![Alt text](/document/slotmachineStateDiagram.png?raw=true "Slotmachine State Diagram")

As you can see from the above picture, the edges are the operation that a state can execute. 

The real game is played in the GAME state. A Croupier object, which knows the rules of the game, is injected into the GAME state. This croupier implementation can give you three random cards from a sixteen-card pack.
If the croupier gives you three equal cards you will win. Each new player will have three chances to win.

![Alt text](/document/slotmachineClassDiagram.png?raw=true "Slotmachine Class Diagram")


### controller implementation
The controller is implemented like a Spring REST service. It exposes the methods to make the slotmachine change state. It implements a Template Pattern to execute the operation.
![Alt text](/document/templateControllerClassDiagram.png?raw=true "Controller Template Class Diagram")

### code
The code is made up of four components:
- Spring and swagger configurator
- Controller which is implememnted by a Spring Rest service
- Model which belongs to all of the components like slotemachine, states and the croupier object.
- junit: models and controller developed using junit test.

## Build
Use the following to build/startup backend:
```
$ git clone https://github.com/nicolaViola/slotmachine.git
$ cd slotmachine
$ mvn spring-boot:run
```
then access the Testing and documentation  Swagger application through ```http://localhost:8080/swagger-ui.html ```

### testing and documentation
The Rest service is documented by Swagger (http://swagger.io/) so you can also test it. You can try it following this url: ```http://localhost:8080/swagger-ui.html ```
You will have an endpoints list. A lot of those come from Spring Boot Actuator but the " **machine-controller : Machine Controller** " endpoint will enable you to test the game.
Every method matches with the operation of the game. 

As the default state is the NoCoin state, you have to:
*	/insertCoin operation. You can see the new state (Ready) in the ResponseBody.  
*	/startToPlay operation. You can see the new state (Game) in the ResponseBody.  
*	/shoot operation. You can see the new state (Game, NoCoin or Win) and the result of the game, the three cards, in the ResponsBody.
	* If you are in the Win state you can hit the /disponeMoney operation. You can see the new state (NoCoin) in the ResponseBody.
	* otherwise you are able to repeat the operation of the current state.

## Used Techonlogy
The project uses Spring Core, Spring Boot, Rest API with Spring MVC, Swagger

## To-do List
 - Add Spring data MongoDb to record the games (using Fongo)
 - GUI