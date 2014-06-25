package abstractfactory;

public class VeggiePizza extends Pizza {
    PizzaIngredientFactory ingredientFactory;

	public VeggiePizza(PizzaIngredientFactory ingredientFactory) {
		// TODO - add required code
        this.ingredientFactory = ingredientFactory;
	}
 
	void prepare() {
		// TODO - implement method
        System.out.println("Preparing " + name);
        dough = ingredientFactory.createDough();
        sauce = ingredientFactory.createSauce();
        cheese = ingredientFactory.createCheese();
        clam = ingredientFactory.createClam();
	}
}
