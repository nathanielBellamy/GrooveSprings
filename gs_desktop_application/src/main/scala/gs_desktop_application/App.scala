package gs_desktop_application

object App {

  def simonSays(x : Array[String]): String = x.foldLeft("Simon Says: ")((a,b) => a + " " + b)

  @main def main(): Unit =
    val instructions: Array[String] = Array("How", "now,", "brown", "cow?")
    println(simonSays(instructions))

}
