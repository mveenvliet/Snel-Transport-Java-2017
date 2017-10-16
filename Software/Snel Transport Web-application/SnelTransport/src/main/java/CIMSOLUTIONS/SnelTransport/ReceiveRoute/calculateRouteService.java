package CIMSOLUTIONS.SnelTransport.ReceiveRoute;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import CIMSOLUTIONS.Database.*;

public class calculateRouteService extends MySqlDB {

	// Functions:
	//	input: date
	//	get all orders for that date
	//	get all locations corresponding to those orders
	//	(1. run Christophides Algorithm to check if the route can be done)
	//	(2. divide the number of nodes)
	//	(	Rerun 1 -> 2 untill 1 is true)
	//	(3. check number of nodes each subset)
	//	(4.	blossem shrink if the number of nodes is more than 8)	
	//	run bruteforce TSP
	//	(5.	regrow your blossems)
	//	store route in the database
	//	send route (and number of trucs) back to the user
	
	
}
