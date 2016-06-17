
import scala.collection.mutable.ListBuffer

object Searcher {
  
  def search(table: ListBuffer[Any], busca: String): ListBuffer[Any] = {
      table.filter{ case (_,e,_) => e.equals(busca) }
  }
  
  def searchCont(table: ListBuffer[Any], busca: String): ListBuffer[Any] = {
      table.filter{ case (_,e,_) => e.asInstanceOf[String].contains(busca) }
  }
  
  def main(args: Array[String]) {
    println("Informe o caminho do Diretório:")
    var path = scala.io.StdIn.readLine()
    LerDiretorio.walk(path).toList.foreach { e => println(e) }
    println("Que busca deseja realizar? \n 1- Palavra Exata \n 2-Palavra contém a pesquisa ")
    var busca = scala.io.StdIn.readLine()
    searchCont(LerDiretorio.walk(path),busca).toList.foreach { e => println(e) }
  }
}