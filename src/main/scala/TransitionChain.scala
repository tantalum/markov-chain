package main.scala

import com.circuitsofimagination.markovcahin._
import java.io._
import scala.io.BufferedSource
import scala.io.Source
import scala.util.parsing.combinator._
import scala.util.matching.Regex

class Transition(prev: String, next: String)

object TransitionChain extends App {

  def usage {
    // TODO: Implement this
    println("Usage: ")
  }

  override def main(args: Array[String]) {
    var chain: MarkovChain[String] = new MarkovChain[String]()

    // Train the chain by reading lines from the source
    try {
      var inputStream: InputStream = System.in
      if(args.length == 1) {
        inputStream = new FileInputStream(args(0))
      }
      val inputSource : Source = new BufferedSource(inputStream)
      var states: List[String] = null
      for(line <- inputSource.getLines()) {
        states = TransitionParser.parseTransitions(line)
        chain = ((chain, null:String) /: states)((prev, nextState:String) => prev match {
                case (c, null) => (c, nextState) // If we are on the first state there is no transition
                case (c, prevState) => (c.addTransition(prevState, nextState), nextState)
            }
        ) match { case (c, _) => c }
      }

    } catch {
      case exc: IOException => {
        scala.sys.error("IO Error: " + exc.getMessage())
      }
    }

    val dot: String = MarkovChainGrapher.graphvizulize("Markov Chain", chain)
    print(dot);
  }
}

object TransitionParser extends RegexParsers {
  override def skipWhitespace = true
  private def word : Parser[String] = regex(new Regex("[a-zA-Z0-9\\.]*"))
  //TODO: End chain at newline
  private def transition : Parser[List[String]] = repsep(word, "->") 
  def parseTransitions(src: String): List[String] = parseAll(transition, src) match {
    case Success(result, _) => result
    case failure: NoSuccess => scala.sys.error("Error parsing chain data: "+failure.msg)
  }
}

// vim: set ts=4 sw=4 et:
