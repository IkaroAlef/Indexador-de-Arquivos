

object Tuplas {
  
  def main(args: Array[String]){
    val Lista = List(("a",1),("b",2))
    
    //println(Lista)
    Lista.foreach( e => println(e._2 +" | "+e._1) )
    
  }
  
}