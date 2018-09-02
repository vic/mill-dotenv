package hello

object Main extends App {
  val world = sys.env("WORLD")
  println(s"hola ${world}")
  assert(world == "mundo")
}
