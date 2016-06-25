import scala.util.control.Breaks._
import scala.util.control._
import scala.collection.mutable.ListBuffer
import java.io.File

object Searcher {

  def search(table: ListBuffer[Any], busca: String): ListBuffer[Any] = {
    //list será uma list de cada palavra da busca, onde o token será espaços (usando Exp Reg)
    //checa se em list existe a palavra indexada
    val list = busca.split("\\s+")
    table.filter { case (_, e, _) => list.exists(e.asInstanceOf[String].contains) }
  }

  def main(args: Array[String]) {
    var op = 1
    var f = new File("index.db")
    val loop = new Breaks
    breakable {
      while (op != 0) {
        println("O que deseja fazer? \n 1 - Indexar um diretório \n 2 - Realizar uma busca \n 0 - Sair")
        op = scala.io.StdIn.readInt()
        op match {
          case 1 =>
            if (!(f.exists())) {
              val db = new DB()
              println("Criando a estrutura do BD")
              db.startTabela()
            }
            val db = new DB()
            db.limparTabela()
            println("Informe o caminho do Diretório:")
            var path = scala.io.StdIn.readLine()

            println("Salvando a indexação no BD, aguarde um momento...")
            LerDiretorio.walk(path).toList.foreach { e =>
              {
                val (p, n, l) = e
                db.inserirUm(p, n, l)
              }
            }
            println("Salvo com sucesso!")

          case 2 =>
            val db = new DB()
            val listaBufDb = db.listarTodos()
            print("O que deseja pesquisar? ")
            var busca = scala.io.StdIn.readLine()
            search(listaBufDb, busca).toList.foreach { e => println(e) }

          case 0 => break
          case everythingElse => println("Opção invalida")

          //listaDb.foreach { e => println(e) }
          //LerDiretorio.walk(path).toList.foreach { e => println(e) }
        }
      }
    }
  }
}