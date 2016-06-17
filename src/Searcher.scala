import scala.util.control.Breaks._
import scala.collection.mutable.ListBuffer

object Searcher {
  
  def search(table: ListBuffer[Any], busca: String): ListBuffer[Any] = { //busca exata
    // filtra a tabela de indexação pela busca: 
    //se o 2 elemento da tupla for exatamente igual a busca, 
    //então essa tupla entra na lista de retorno
    table.filter{ case (_,e,_) => e.equals(busca) } 
  }
  
  def searchCont(table: ListBuffer[Any], busca: String): ListBuffer[Any] = { //busca a palavra por parte ou total dela
     // filtra a tabela de indexação pela busca: 
    //se o 2 elemento da tupla contém a busca (ex.: ikaro contem ik), 
    //então essa tupla entra na lista de retorno
      table.filter{ case (_,e,_) => e.asInstanceOf[String].contains(busca) }
  }
  
  def searchOr(table: ListBuffer[Any], busca: String): ListBuffer[Any] = { //busca varias palavras de uma vez, se contem pelo menos uma delas,
    // filtra a tabela de indexação pela busca: 
    //se a busca contém o 2º elemento da tupla (ex.: busca = ikaro alef, essa busca contém ikaro, que nesse caso, "ikaro" está na tabela de indexação) 
    //então essa tupla entra na lista de retorno
   table.filter{ case (_,e,_) => busca.contains(e.asInstanceOf[String]) }
  }
  
  def main(args: Array[String]) {
    var op = 1 //operação do menu 
    var busca = ""
    println("Informe o caminho do Diretório:")
    var path = scala.io.StdIn.readLine()
    LerDiretorio.walk(path).toList.foreach { e => println(e) }
    while (op!=0){ 
      println("\nQue busca deseja realizar? \n 1- Palavra Exata \n 2- Palavra contém a pesquisa \n 3- Pesquisa uma ou mais palavras \n 0- Sair")
      op = scala.io.StdIn.readInt() 
      op match{ ////menu para o tipo de pesquisa
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