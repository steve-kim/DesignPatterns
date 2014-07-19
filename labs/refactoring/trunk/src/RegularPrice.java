
public class RegularPrice extends Price {
	public int getPriceCode() {
		return Movie.REGULAR;
	}

	@Override
	protected double getCharge(int daysRented) {
		double thisAmount = 0;

	    thisAmount += 2;
	    if (daysRented > 2) {
	        thisAmount += (daysRented - 2) * 1.5;
	    }

		return thisAmount;
	}
}
