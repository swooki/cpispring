package ch4.factory.pizza.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ch4.factory.pizza.ChicagoPizzaStore;
import ch4.factory.pizza.NYPizzaStore;
import ch4.factory.pizza.Pizza;
import ch4.factory.pizza.PizzaStore;

public class PizzaTest {

	@Test
	public void test() {
		PizzaStore nyStore = new NYPizzaStore();
		PizzaStore chicagoStore = new ChicagoPizzaStore();
		
		Pizza pizza = nyStore.orderPizza("cheese");
		System.out.println("Ethan ordered a " + pizza.getName() + "\n");
		
		pizza = chicagoStore.orderPizza("cheese");
		System.out.println("Joel ordered a " + pizza.getName() + "\n");
	}
}
