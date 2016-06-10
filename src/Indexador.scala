import scala.util.matching.Regex
import java.io.File
import scala.collection.JavaConversions._

class Registro(path: String, content: String){
  var this.path = path
  var this.content = content
  var id = 0
  
  def getPath() =  this.path
  def getContent() =  this.content
  def setId(n: Int) = { this.id = n }
  
  override def toString(): String = {
    var S = ""
    S = S + "ID: "+ this.id + " | "
    S = S + "Path: "+ this.path +" | "
    S = S + "Content: "+ this.content +"\n"
    return S
  }
}

object Indexador {

  var P = "C:\\Users\\Ikaro\\Documents\\Exemplo"
  
  def getFileTree(f: File): Stream[File] =
        f #:: (if (f.isDirectory) f.listFiles().toStream.flatMap(getFileTree) 
               else Stream.empty)
               
//  def listador(lista: Stream[File]): Array[Registro] =  {
//    
//  }
         
  def listador(lista: String): String =  {
    return lista
  }

    
  def main(args: Array[String]){
    //getFileTree(new File("C:\\Users\\Ikaro\\Documents\\Exemplo")).foreach(println)
    var lista = getFileTree(new File(P))
    
    // Pensar em uma solução
    //lista.foreach { x => ??? }
      
    //println(Indexador.listador("T"))
    var r = new Registro("C:\\User\\Ikaro", "Alef")
    r.setId(2)
    println ( r.toString() )
    
    
  }

}