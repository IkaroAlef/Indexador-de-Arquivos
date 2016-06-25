import scala.io.Source
import java.io.File
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern
import scala.collection.mutable.ListBuffer
import java.net.URLConnection

import org.apache.commons.logging.impl.Log4JLogger
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.apache.pdfbox.text.PDFTextStripperByArea

import com.drew.imaging.ImageMetadataReader
import com.drew.imaging.ImageProcessingException
import com.drew.metadata.Directory
import com.drew.metadata.Metadata
import com.drew.metadata.Tag
import java.util.Calendar

object LerDiretorio {

  def lerArquivo(path: String): ListBuffer[Any] = {
    var n = 1
    val lista = ListBuffer.empty[Any]
    val pattern = Pattern.compile("\\w+")

    for (line <- Source.fromFile(path).getLines()) {
      //println("--- Conteúdo ---")

      val matcher = pattern.matcher(line)
      // Do something with line
      //println(line + "|Linha:"+n )
      while (matcher.find()) {
        // Concatena a lista
        lista += ((path, matcher.group(), n))
        //println( path + "|" + matcher.group() + "|L:" + n)

      }
      n += 1
    }
    lista
  }

  def lerPdf(path: String): ListBuffer[Any] = {
    // Ref.: http://stackoverflow.com/questions/4784825/how-to-read-pdf-files-using-java
    val lista = ListBuffer.empty[Any]
    try {
      var document = PDDocument.load(new File(path))
      document.getClass();

      if (!document.isEncrypted()) {
        var stripper = new PDFTextStripperByArea()
        stripper.setSortByPosition(true)
        var Tstripper = new PDFTextStripper()
        var st = Tstripper.getText(document)
        val pattern = Pattern.compile("\\w+")
        var n = 1
        // Função de alta ordem + Função Lambda (anônima)
        var stF = st.split("\n").foreach {
          x =>
            {
              val matcher = pattern.matcher(x)
              while (matcher.find()) {
                // Concatena a lista
                lista += ((path, matcher.group(), n))
              }
              n += 1
            }
        }
      }
    } catch {
      case e: Exception => println("exception caught: " + e)
    }
    lista
  } // Fim do Ler PDF

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
    for (f <- getListOfFiles(path)) {
      if (f.isDirectory()) {
        lista += ((f.getCanonicalPath(), f.getName(), 0))
        lista ++= walk(f.getAbsolutePath())

      } else {
        // Adiciona o nome do arquivo na lista de tuplas
        lista += ((f.getCanonicalPath(), f.getName(), 0))

        // Para ler cada arquivo
        try {
          var fType = URLConnection.guessContentTypeFromName(f.getAbsolutePath())
          var c = f.getCanonicalPath()
          // Para ler cada arquivo, se for PDF
          if (fType.equals("application/pdf")) {
            lista ++= lerPdf(c)
          }

          if (fType.contains("text")) {
            lista ++= lerArquivo(c)
          }

        } catch {
          case e: IOException => println("exception caught: " + e)
        }

      }
    }

    lista
  }

  def main(args: Array[String]) {

    println("Informe o caminho do Diretório:")
    var path = scala.io.StdIn.readLine()
    
    val start: Long = System.currentTimeMillis

    println("Preparando Banco de Dados")
    val db = new DB()
    db.startTabela()

    //walk(path).toList.foreach { e => println(e) }
    //println ( walk(path).toList )
    // Após montar a lista a mesma é guardada no banco de dados
    /*
     * Ref.: http://www.artima.com/forums/flat.jsp?forum=283&thread=243570
    walk(path).toList.foreach { e => 
      { val (p, n, l) = e
        println("Path: "+p+"| Name: "+n+"| Line:"+l)
      } 
    }
    */
    println("Salvando o Banco de Dados")

//    walk(path).toList.foreach { e => 
//      { val (p, n, l) = e
//        db.inserirUm(p, n, l)
//      }
//    }
    
    db.inserirListBuffer( walk(path) )
    
    val end: Long = System.currentTimeMillis
    println( (end - start) + " total milliseconds")
    
  }

}