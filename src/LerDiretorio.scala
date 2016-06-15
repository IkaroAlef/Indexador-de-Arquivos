import scala.io.Source
import java.io.File
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern
import scala.collection.mutable.ListBuffer

object LerDiretorio {
  
  def lerArquivo(path: String): ListBuffer[Any] = {
    var n = 1
    val lista = ListBuffer.empty[Any]
    val pattern = Pattern.compile("\\w+")
    
    for ( line <- Source.fromFile( path ).getLines() ) {
      //println("--- Conteúdo ---")
      
		  val matcher = pattern.matcher(line)
      // Do something with line
      //println(line + "|Linha:"+n )
      while (matcher.find()) {
        // Concatena a lista
         lista += ( ( path, matcher.group(), n ) )
		    //println( path + "|" + matcher.group() + "|L:" + n)
        
		  }
      n += 1
    }
    
    // O retorno
    lista
  }
  
  def getListOfFiles(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      //d.listFiles.filter(_.isFile).toList
      d.listFiles.toList
    } else {
      List[File]()
    }
  }
  
  def walk(path: String): ListBuffer[Any] = {
    val lista = ListBuffer.empty[Any]
    // Loop through each line in the file
    for (f <- getListOfFiles( path )) {
      if ( f.isDirectory() ) {
          lista ++= walk( f.getAbsolutePath() )
          //println( "Dir:" + f.getAbsoluteFile() )
      }
      else {
          //println( "File:" + f.getAbsoluteFile() )
          // Para ler cada arquivo
          try{
          	var c = f.getCanonicalPath()
          	lista ++= lerArquivo( c )
          } catch {
            case e : IOException => println("exception caught: " + e)
          }
          
      }
    }
    
    lista
  }
  
  def main(args: Array[String]) {
    
    println("Informe o caminho do Diretório:")
    var path = scala.io.StdIn.readLine()
    walk(path).toList.foreach { e => println(e) }
    //println ( walk(path).toList )
   
  }
  
}