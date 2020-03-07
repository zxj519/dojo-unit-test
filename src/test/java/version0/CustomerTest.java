package version0;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CustomerTest {

  @Test
  public void should_pay_2_earn_1_point_when_rent_a_regular_movie_for_2_days() {
    //given
    Rental rental = new Rental(new Movie("复仇者联盟", Movie.REGULAR), 2);
    Customer customer = new Customer("科比");

    //when
    customer.addRental(rental);

    //then
    assertEquals("Rental Record for 科比\n" +
        "\t复仇者联盟\t2.0\n" +
        "Amount owed is 2.0\n" +
        "You earned 1 frequent renter points", customer.statement());
  }

}
