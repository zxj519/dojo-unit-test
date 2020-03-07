package version0;

class Rental {
  private Movie movie;
  private int daysRented;

  Rental(Movie movie, int daysRented) {
    this.movie = movie;
    this.daysRented = daysRented;
  }

  Movie getMovie() {
    return movie;
  }

  int getDaysRented() {
    return daysRented;
  }
}
