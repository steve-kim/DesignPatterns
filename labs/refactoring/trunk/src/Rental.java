
class Rental {
    private Movie _movie;
	private int _daysRented;
    public Rental(Movie movie, DateRange range) {
        _movie = movie;
        _daysRented = (int)((range.end.getTime() - range.start.getTime()) / (1000 * 60 * 60 * 24));
    }
    public int getDaysRented() {
        return _daysRented;
    }

    public String getTitle() {
        return _movie.getTitle();
    }
    
    public int getPriceCode() {
        return _movie.getPriceCode();
    }
}
