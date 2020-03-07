package version0;

import java.util.Enumeration;
import java.util.Vector;

import static java.lang.String.valueOf;

class Customer {
  private String name;
  private Vector rentals = new Vector();

  Customer(String name) {
    this.name = name;
  }

  String statement() {
    double totalAmount = 0;
    int frequentRenterPoints = 0;
    Enumeration rentals = this.rentals.elements();
    String result = "Rental Record for " + getName() + "\n";
    while (rentals.hasMoreElements()) {
      double thisAmount = 0;
      Rental each = (Rental) rentals.nextElement();

      // determine amount for each line
      switch (each.getMovie().getPriceCode()) {
        case Movie.REGULAR:
          thisAmount += 2;
          if (each.getDaysRented() > 2)
            thisAmount += (each.getDaysRented() - 2) * 1.5;
          break;
        case Movie.NEW_RELEASE:
          thisAmount += each.getDaysRented() * 3;
          break;
        case Movie.CHILDREN:
          thisAmount += 1.5;
          if (each.getDaysRented() > 3)
            thisAmount += (each.getDaysRented() - 3) * 1.5;
          break;
      }

      // add frequent renter points
      frequentRenterPoints++;
      // add bonus for a two day new release rental
      if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) &&
          each.getDaysRented() > 1) frequentRenterPoints++;

      //show figures for this rental
      result += "\t" + each.getMovie().getTitle() + "\t" +
          valueOf(thisAmount) + "\n";
      totalAmount += thisAmount;
    }
    //add footer lines
    result += "Amount owed is " + valueOf(totalAmount) + "\n";
    result += "You earned " + valueOf(frequentRenterPoints) +
        " frequent renter points";
    return result;
  }

  void addRental(Rental arg) {
    rentals.addElement(arg);
  }

  private String getName() {
    return name;
  }
}
