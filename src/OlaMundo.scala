

object OlaMundo {
  def main(args: Array[String]){
    println("Ola mundo!")
    var num1 = 5
      var num2 = 10
      var texto = "A soma é: "
      println(texto + (num1 + num2))
      
      var s = new StringBuffer
      s.append("Olá!");
      s.append("Vamos aprender Scala?")
      println(s);
  }
}