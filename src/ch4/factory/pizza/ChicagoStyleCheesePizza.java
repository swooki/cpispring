package ch4.factory.pizza;

public class ChicagoStyleCheesePizza extends Pizza {
	public ChicagoStyleCheesePizza() {
		name = "Chicago Style Sauce and Cheese Pizza";
		dough = "Extra Thick Crust Dough";
		sauce = "Plum Tomato Sauce";
		toppings.add("Shredded Mozzarella Cheese");
	}

	// The Chicago style pizza also override the cut() method so that the pieces
	// are cut into squares
	void cut() {
		System.out.println("Cutting the pizza into square slices");
	}
}
