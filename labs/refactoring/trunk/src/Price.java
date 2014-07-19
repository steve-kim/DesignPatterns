public abstract class Price {

	public Price() {
		super();
	}

	public abstract int getPriceCode();

	protected abstract double getCharge(int daysRented);

	public int getFrequentRenterPoints(Rental rental) {
		return 1;
	}

}