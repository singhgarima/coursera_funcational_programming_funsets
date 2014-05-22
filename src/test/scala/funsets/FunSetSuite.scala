package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

    import FunSets._

  test("contains") {
    assert(contains((elem => elem < 0), -1))
    assert(!contains((elem => elem < 0), 1))
  }

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  test("singletonSet(1) contains 1") {
    new TestSets {
      assert(contains(s1, 1), "Singleton class should contain 1")
      assert(!contains(s1, 2), "Singleton class should not contain 2")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union should contain 1")
      assert(contains(s, 2), "Union should conatin 2")
      assert(!contains(s, 3), "Union should not contain 3")
    }
  }
  
  test("intersect contains comman elements") {
    new TestSets {
      val s = intersect(union(s1, s2), s1)
      assert(contains(s, 1), "Union should contain 1")
      assert(!contains(s, 2), "Union should not conatin 2")
      assert(!contains(s, 3), "Union should not contain 3")
    }
  }
  
  test("diff contains element in first set which are not in set 2") {
    new TestSets {
      val s = diff(union(s1, s2), s1)
      assert(!contains(s, 1), "Union should not contain 1")
      assert(contains(s, 2), "Union should conatin 2")
      assert(!contains(s, 3), "Union should not contain 3")
    }
  }
  
  test("filter removes element from set which dont satisfy the condition") {
    new TestSets {
      val s = filter((elem => elem > 0 & elem < 4), x => x < 3)
      assert(contains(s, 1), "Union should contain 1")
      assert(contains(s, 2), "Union should conatin 2")
      assert(!contains(s, 3), "Union should not contain 3")
      assert(!contains(s, 4), "Union should not contain 4")
    }
  }
  
  trait SetMultipleOfTen {
    val multipleOfTen = ((x: Int) => x % 10 == 0)
  }
  
  test("forall") {
    new SetMultipleOfTen {
    	assert(!forall(multipleOfTen, elem => elem % 6 == 0))
    	assert(forall(multipleOfTen, elem => elem % 2 == 0))
    }
  }
  
  test("exists") {
    new SetMultipleOfTen {
    	assert(exists(multipleOfTen, elem => elem % 6 == 0))
    	assert(!exists(multipleOfTen, elem => elem == 11))
    }
  }
  
  test("maps") {
    new SetMultipleOfTen {
      val mappedSet = map(multipleOfTen, (x => x * 2))
      assert(contains(mappedSet, 20))
      assert(!contains(mappedSet, 30))
    }
  }
  
  test("toString") {
    val multipleOfHundreds = ((x: Int) => x % 500 == 0)
    assert(FunSets.toString(multipleOfHundreds) === "{-1000,-500,0,500,1000}")
  }
}
