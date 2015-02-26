package test.scala

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter

import com.circuitsofimagination.markovcahin._

class MarkovChainSpec extends FunSpec with BeforeAndAfter {

  var chain: MarkovChain[String] = _

  before {
    chain = new MarkovChain[String]()
  }

  describe("A Markov Chain") {
    it("Should have a probability of zero for any state if it is empty") {
      assert(chain.transitionProbability("fake", "fake2") === 0)
    }

    it("Should have a probability of one if it has only one transition which is the transition we're asking about") {
      chain = chain.addTransition("one", "two")
      assert(chain.transitionProbability("one", "two") === 1)
    }

    it("Should have a probability of zero for any transition it hasn't been trained on") {
      chain = chain.addTransition("one", "two")
      chain = chain.addTransition("two", "three")
      assert(chain.transitionProbability("one", "three") === 0)
    }

    it("Should produce the correct probability for any transition it has been trained on") {
      chain = chain.addTransition("one", "one");
      chain = chain.addTransition("one", "two");
      chain = chain.addTransition("one", "three");
      chain = chain.addTransition("one", "four");
      assert(chain.transitionProbability("one", "one") === 0.25)
    }

    it("Should generate the graph") {
      chain = chain.addTransition("one", "one");
      chain = chain.addTransition("one", "two");
      chain = chain.addTransition("one", "three");
      chain = chain.addTransition("one", "four");
      println(MarkovChainGrapher.graphvizulize("Test Graph: ", chain))
    }
  }
}
// vim: set ts=4 sw=4 et:
