import scala.util.control.Breaks._
import scala.collection.mutable.ListBuffer

object Searcher {
  
  def search(table: ListBuffer[Any], busca: String): ListBuffer[Any] = {
      table.filter{ case (_,e,_) => e.equals(busca) }
  }
  
  def searchCont(table: ListBuffer[Any], busca: String): ListBuffer[Any] = {
      table.filter{ case (_,e,_) => e.asInstanceOf[String].contains(busca) }
  }
  
  def searchOr(table: ListBuffer[Any], busca: String): ListBuffer[Any] = {
   table.filter{ case (_,e,_) => busca.contains(e.asInstanceOf[String]) }
  }
  
  def main(args: Array[String]) {
    var op = 1
    var busca = ""
    println("Informe o caminho do Diretório:")
    var path = "C:\\Users\\Ikaro\\Documents\\Exemplo"
    LerDiretorio.walk(path).toList.foreach { e => println(e) }
    while (op!=0){
      println("\nQue busca deseja realizar? \n 1- Palavra Exata \n 2- Palavra contém a pesquisa \n 3- Pesquisa uma ou outra palavra \n 0- Sair")
      op = scala.io.StdIn.readInt()
      op match{
        case 1 => 
            print("Digite a palavra exata: ")
            busca = scala.io.StdIn.readLine()
            search(LerDiretorio.walk(path),busca).toList.foreach { e => println(e) }
        case 2 =>
            print("Digite a palavra ou parte dela: ")
            busca = scala.io.StdIn.readLine()
            searchCont(LerDiretorio.walk(path),busca).toList.foreach { e => println(e) }
        case 3 =>
            print("Digite as palavras: ")
            busca = scala.io.StdIn.readLine()
            searchOr(LerDiretorio.walk(path),busca).toList.foreach { e => println(e) }
        case 0 => 
      }
    }
  }
}