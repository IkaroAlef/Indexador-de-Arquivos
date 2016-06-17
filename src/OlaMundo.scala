import com.sun.org.apache.xalan.internal.xsltc.compiler.ForEach


object OlaMundo {
  def main(args: Array[String]){
        val x = "ikaro alef ufrpe"
        var list = x.split(" ").toList
        println(list.contains("ikaro alef"))       

  }
}