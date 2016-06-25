import java.sql.Connection
import java.sql.Statement
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.ResultSetMetaData
import java.sql.SQLException
import java.sql.DatabaseMetaData

import scala.collection.mutable.ListBuffer

class DB(){
  Class.forName("org.sqlite.JDBC")
	var c = DriverManager.getConnection("jdbc:sqlite:index.db");
  
  def criarTabela(): Unit = {
		// Criar a tabela
		try {
			var stmt = c.createStatement()
			var sql = "CREATE TABLE IF NOT EXISTS tuplas " + 
						    "(path	TEXT	NOT NULL," +
		            " name	TEXT	NOT NULL," +
		            "line Int NOT NULL)"
			stmt.executeUpdate(sql)
			stmt.close()
			//println("Table created successfully")
		} catch {
		  case e : Exception => println("exception caught: " + e)
		}
  }
  
  
  def limparTabela(): Unit = {
    // Limpar a tabela
		try {
			var stmt = c.createStatement()
			var sql = "DELETE FROM tuplas;"
			stmt.executeUpdate(sql)
			stmt.close()
			//println("Clear done successfully")
		} catch {
		  case e : Exception => println("exception caught: " + e)
		}
  }
  
  def startTabela(): Unit = {
    this.criarTabela()
    this.limparTabela()
  }
  
  def inserirUm(path: Any, name: Any, line: Any): Unit = {
    // Inserir um registro na tabela
		try {
			var stmt = c.createStatement()
			var sql = "INSERT INTO tuplas (path,name,line) "+
			          "VALUES('"+path+"','"+name+"','"+line+"');"
			stmt.executeUpdate(sql)
			stmt.close()
			//println("Insert done successfully")
		} catch {
		  case e : Exception => println("exception caught: " + e)
		}
  }
  
  def inserirListBuffer(listaBuf: ListBuffer[Any]): Unit = {
    // Inserir um registro na tabela
		try {
			var stmt = c.createStatement()
			var beg = "BEGIN;"
			stmt.executeUpdate(beg)
			
			var sql = "INSERT INTO tuplas (path,name,line) VALUES"
			listaBuf.toList.foreach { e => 
                { val (p, n, l) = e
                  sql += "('"+p+"','"+n+"','"+l+"'),"
                }
			}
			sql = sql.dropRight(1)
			sql += ";"
			//println("Comando SQL:\n"+sql)
			stmt.executeUpdate(sql)
			
			var comm = "COMMIT;"
			stmt.executeUpdate(comm)
			
			stmt.close()
			//println("Insert done successfully")
		} catch {
		  case e : Exception => println("exception caught: " + e)
		}
  }
 
  def listarTodos(): ListBuffer[Any] = {
    val lista = ListBuffer.empty[Any]
    try {
      var stmt = c.createStatement()
			var rs = stmt.executeQuery( "SELECT path,name,line FROM tuplas;" );
			while ( rs.next() ) {
			  lista += ( ( rs.getString("path"), rs.getString("name"), rs.getInt("line") ) )
      }
			rs.close();
			stmt.close()
    } catch {
		  case e : Exception => println("exception caught: " + e)
		}
    lista
  }
  
}
