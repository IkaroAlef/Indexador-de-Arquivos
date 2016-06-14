import scala.io.Source
import java.io.File
import java.io.IOException

object LerDiretorio {
  
  def lerArquivo(path: String){
    for (line <- Source.fromFile( path ).getLines()) {
      println("--- Conteúdo ---")
      // Do something with line
      println(line)
    }
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
  
  def walk(path: String){
    // Loop through each line in the file
    for (f <- getListOfFiles( path )) {
      if ( f.isDirectory() ) {
          walk( f.getAbsolutePath() )
          println( "Dir:" + f.getAbsoluteFile() )
      }
      else {
          println( "File:" + f.getAbsoluteFile() )
          // Para ler cada arquivo
          try{
          	var c = f.getCanonicalPath()
          	lerArquivo( c )
          } catch {
            case e : IOException => println("exception caught: " + e)
          }
          
      }
    }
  }
  
  def main(args: Array[String]) {
    
    println("Informe o caminho do Diretório:")
    var path = scala.io.StdIn.readLine()
    walk(path)
    
  }
  
}