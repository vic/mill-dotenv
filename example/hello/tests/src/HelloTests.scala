package hello

import utest._

object HelloTests extends TestSuite {
  val tests = Tests {
    test("world") - {
      assert("monde" == sys.env("WORLD"))
    }
  }
}
