package gameworld.service;

import java.util.Random;

/**
 * RandomGenerator for generating and storing random numbers. This will help in unit tests.
 */
public class RandomGenerator {
  private Random random;
  private int[] randomUserInputs;
  private int index;

  /**
   * Constructor for RandomGenerator class used for random number generation.
   */
  public RandomGenerator() {
    random = new Random();
    randomUserInputs = new int[]{};
    index = 0;
  }

  /**
   * Constructor for random generator for test classes,
   * which accepts integer array which is used instead of random values.
   * This constructor is mostly used by test cases.
   * @param input random array with preset integer values.
   */
  public RandomGenerator(int... input) {
    random = new Random();
    randomUserInputs = input;
    index = 0;
  }

  /**
   * Get RandomInteger function to get random integer from sequential input of randomInt.
   * @param range upper bound of the random number.
   * @return Returns random integer or sequential integer.
   */
  public int getRandomInteger(int range) {
    if (randomUserInputs == null || randomUserInputs.length == 0) {
      int randomNumber = random.nextInt(range);
      if (randomNumber == 0) {
        return randomNumber + 1;
      }
      return randomNumber;
    } else {
      return randomUserInputs[index++];
    }
  }
}
