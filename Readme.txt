
Game of 6-stone Kalah.

#### API know how

Web Service is deployed on local( embedded in spring boot).

How to build:
1)Checkout or download code from github.
2) Import as amaven project in eclipse.
3) Do a maven build with goal "clean install"

How to run:
1) Right click on project and go to run as-> run configuration.
2) Under maven build,create a run configuration with goal "spring-boot:run"
3) Application will be started

To create a game from postman:
1) Select method as POST and header as "Content-Type:application/json"
2) Request url: http://localhost:8080/games
 SSL security should be off in postman

To make a move:
1) Select method as PUT and header as "Content-Type:application/json"
2) Request url: http://localhost:8080/games/{gameId}/pits/{pitId}
 SSL security should be off in postman

