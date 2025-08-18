package mill.dotenv

import utest._

object DotEnvModuleTests extends TestSuite {
  val tests = Tests {

    test("should remove quotes from value") - {
      assert_env(
        "HELLO",
        "WORLD",
        """
HELLO="WORLD"
"""
      )
    }

    test("should allow dot as key name") - {
      assert_env(
        "HELL.O",
        "WORLD",
        """
HELL.O=WORLD
"""
      )
    }

    test("should allow new lines on value") - {
      assert_env(
        "HELLO",
        "BEAUTIFUL\nWORLD",
        """
HELLO="BEAUTIFUL
WORLD"
"""
      )
    }

    test("should ignore commented values") - {
      val env = DotEnvModule.parse("#FOO=BAR")
      assert(None == env.get("FOO"))
    }

  }

  def assert_env(key: String, expected: String, source: String) = {
    val env = DotEnvModule.parse(source)
    assert(expected == env.get(key).get)
  }
}
