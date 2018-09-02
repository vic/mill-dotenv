package mill.dotenv

import mill._
import scalalib._
import ammonite.ops._
import ImplicitWd._

object DotEnvModule {

  def parse(pathRef: PathRef):Map[String,String] = {
    parse(read!(pathRef.path))
  }

  def parse(source: String): Map[String, String] = LINE_REGEX.findAllMatchIn(source)
    .map(keyValue => (keyValue.group(1), unescapeCharacters(removeQuotes(keyValue.group(2)))))
    .toMap

  private def removeQuotes(value: String): String = {
    value.trim match {
      case quoted if quoted.startsWith("'") && quoted.endsWith("'") => quoted.substring(1, quoted.length - 1)
      case quoted if quoted.startsWith("\"") && quoted.endsWith("\"") => quoted.substring(1, quoted.length - 1)
      case unquoted => unquoted
    }
  }

  private def unescapeCharacters(value: String): String = {
    value.replaceAll("""\\([^$])""", "$1")
  }

  // shamefuly copied from SbtDotenv
  // https://github.com/mefellows/sbt-dotenv/blob/master/src/main/scala/au/com/onegeek/sbtdotenv/SbtDotenv.scala

  private val LINE_REGEX =
    """(?xms)
       (?:^|\A)           # start of line
       \s*                # leading whitespace
       (?:export\s+)?     # export (optional)
       (                  # start variable name (captured)
         [a-zA-Z_]          # single alphabetic or underscore character
         [a-zA-Z0-9_.-]*    # zero or more alphnumeric, underscore, period or hyphen
       )                  # end variable name (captured)
       (?:\s*=\s*?)       # assignment with whitespace
       (                  # start variable value (captured)
         '(?:\\'|[^'])*'    # single quoted variable
         |                  # or
         "(?:\\"|[^"])*"    # double quoted variable
         |                  # or
         [^\#\r\n]*         # unquoted variable
       )                  # end variable value (captured)
       \s*                # trailing whitespace
       (?:                # start trailing comment (optional)
         \#                 # begin comment
         (?:(?!$).)*        # any character up to end-of-line
       )?                 # end trailing comment (optional)
       (?:$|\z)           # end of line
    """.r


}

trait DotEnvModule extends JavaModule {

  def dotenvSources = T.sources { pwd / ".env" }

  def dotenv = T.input {
    dotenvSources().map(DotEnvModule.parse).foldLeft(Map[String,String]()) { _ ++ _ }
  }

  override def forkEnv = super.forkEnv() ++ dotenv().seq

}
