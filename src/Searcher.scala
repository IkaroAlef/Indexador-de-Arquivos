import scala.util.control.Breaks._
import scala.collection.mutable.ListBuffer
import java.io.File

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
    var f = new File("index.db")
    if( !(f.exists()) ) {
      val db = new DB()
      println("Criando a estrutura do BD")
      db.startTabela()
      
      println("Informe o caminho do Diretório:")
      var path = scala.io.StdIn.readLine()
      
      println("Salvando a indexação no BD")
      LerDiretorio.walk(path).toList.foreach { e => 
        { val (p, n, l) = e
          db.inserirUm(p, n, l)
        }
      }
      
    }
    
    val db = new DB()
    val listaBufDb = db.listarTodos()
    val listaDb = listaBufDb.toList  // Lista fixa, temp. APAGAR SE NÃO FOR USAR
    
    //listaDb.foreach { e => println(e) }
    //LerDiretorio.walk(path).toList.foreach { e => println(e) }
    
    while (op!=0){ 
      println("\nQue busca deseja realizar? \n 1- Palavra Exata \n 2- Palavra contém a pesquisa \n 3- Pesquisa uma ou mais palavras \n 0- Sair")
      op = scala.io.StdIn.readInt() 
      op match{ ////menu para o tipo de pesquisa
        case 1 => 
            print("Digite a palavra exata: ")
            busca = scala.io.StdIn.readLine()
            search(listaBufDb,busca).toList.foreach { e => println(e) }
        case 2 =>
            print("Digite a palavra ou parte dela: ")
            busca = scala.io.StdIn.readLine()
            searchCont(listaBufDb,busca).toList.foreach { e => println(e) }
        case 3 =>
            print("Digite as palavras: ")
            busca = scala.io.StdIn.readLine()
            searchOr(listaBufDb,busca).toList.foreach { e => println(e) }
        case 0 => 
      }
    }
    
  }
}