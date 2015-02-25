package com.circuitsofimagination.markovcahin

object MarkovChainGrapher {
  
  def header(title: String):String = {
    """digraph markov_cahin{
          rankdir=LR;
          label = "%s"
          node [shape = circle];

    """.format(title);
  }

  def footer():String = {
    "}";
  }

  def graphvizulize[S](title: String, chain: MarkovChain[S]):String = {
    val states = chain.states;
    var allTransitions = List[String]();
    for(state <- states) {
        allTransitions = chain.transitionsFor(state).map(tup => transitionToString(state, tup._2, tup._1)) ++ allTransitions
    }
    header(title) + allTransitions.foldLeft("")((a, b) => a+b) + footer();
  }

  def transitionToString[S](initialState: S, probability: Double, nextState: S) = {
    "\"%s\" -> \"%s\" [ label = \"%3.2f\" ];\n".format(initialState.toString, nextState.toString, probability);
  }
}
// vim: set ts=4 sw=4 et:
