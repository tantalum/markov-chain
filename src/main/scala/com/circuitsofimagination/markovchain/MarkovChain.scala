package com.circuitsofimagination.markovcahin

class MarkovChain[S](transitionMap: Map[S, MarkovTransitionSet[S]]) {

  /**
   * Default construcotrs creates an empty Markov Chain
   */
  def this() = this(Map[S, MarkovTransitionSet[S]]())

  def addTransition(prevState: S, nextState: S) = {
    val transitions = 
      if (transitionMap.contains(prevState)) transitionMap(prevState);
      else new MarkovTransitionSet[S]()
          
    val newTransitions = transitions.addTransition(nextState);
    new MarkovChain(transitionMap.updated(prevState, newTransitions));
  }

  // TODO: Define exactly what a trasition's probability is
  def transitionProbability(state1: S, state2: S) = {
    transitionMap.get(state1) match {
      case Some(transitionSet) => transitionSet.probabilityFor(state2)
      case None => 0
    }
  }

  def transitionsFor(state: S) = {
    transitionMap.get(state) match {
      case Some(transitionSet) => transitionSet.toList
      case None => List[(S, Double)]();
    }
  }

  def states() = {
    transitionMap.keys;
  }

}

class MarkovTransitionSet[S](transitionCounter: Map[S, Int]) {

  def this() = this(Map[S, Int]())

  def apply(state: S) = probabilityFor(state)

  def addTransition(state: S) = {
    val count = countFor(state);
    new MarkovTransitionSet[S](transitionCounter.updated(state, count+1));
  }

  def countFor(state: S) = { 
    if(transitionCounter.contains(state)) transitionCounter(state);
    else 0
  }

  def totalCount():Double = {
    val counts = transitionCounter.values
    counts.foldLeft(0)((a, b) => a+b).toDouble
  }

  def probabilityFor(state: S) = {
    countFor(state).toDouble/this.totalCount
  }

  def toList() = {
    transitionCounter.toList.map(tup => (tup._1, tup._2.toDouble/totalCount));
  }

}
// vim: set ts=4 sw=4 et:
