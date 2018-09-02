package hello

import utest._

object HelloTests extends TestSuite {
  val tests = Tests{
    'world - {
      assert("monde" == sys.env("WORLD"))
    }
  }
}
