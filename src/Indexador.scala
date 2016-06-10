import scala.util.matching.Regex
import java.io.File

object Indexador {

  def recursiveListFiles(f: File, r: Regex): Array[File] = {
  val these = f.listFiles
  val good = these.filter(f => r.findFirstIn(f.getName).isDefined)
  good ++ these.filter(_.isDirectory).flatMap(recursiveListFiles(_,r))
  }
}