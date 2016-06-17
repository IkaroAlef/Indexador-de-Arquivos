
import scala.collection.mutable.ListBuffer

object Searcher {
  
  def search(table: ListBuffer[Any], busca: String): ListBuffer[Any] = {
      table.filter{ case (_,e,_) => e.equals(busca) }
  }
  
  def main(args: Array[String]) {
    println("Informe o caminho do Diretório:")
    var path = scala.io.StdIn.readLine()
    LerDiretorio.walk(path).toList.foreach { e => println(e) }
    println("O que deseja buscar?")
    var busca = scala.io.StdIn.readLine()
    search(LerDiretorio.walk(path),busca).toList.foreach { e => println(e) }
  }
}