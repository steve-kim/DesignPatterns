
public class NewReleasePrice extends Price {
	public int getPriceCode() {
		return Movie.NEW_RELEASE;
	}

	@Override
	protected double getCharge(int daysRented) {
		double thisAmount = 0;

	    thisAmount += daysRented * 3;

		return thisAmount;
	}

	@Override
	public int getFrequentRenterPoints(Rental rental) {
		if (rental.getDaysRented() > 1)
			return 2;
		else
			return 1;
	}
}
