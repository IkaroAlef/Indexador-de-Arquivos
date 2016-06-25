import com.sun.org.apache.xalan.internal.xsltc.compiler.ForEach
import scala.util.matching.Regex


object OlaMundo {
  def main(args: Array[String]){
        val x = "ikaro alef  ufrpe"
        var list = x.split("\\s+").toList
        println(list)
        val a = "ikaro"
        //val pattern = new Regex("ikaro|alef")
        println(list.exists(a.contains))      
  }
}