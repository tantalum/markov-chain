package main.scala

class MarkovChain[S](transitionMap: Map[S, MarkovTransitionSet[S]]) {

  def this() = this(Map[S, MarkovTransitionSet[S]]())

  def addTransition(prevState: S, nextState: S) = {
    val transitions = 
      if (transitionMap.contains(prevState)) transitionMap(prevState);
      else new MarkovTransitionSet[S]()
          
    val newTransitions = transitions.addTransition(nextState);
    new MarkovChain(transitionMap.updated(prevState, newTransitions));
  }

  def states() = transitionMap.keys

  def transitionProbability(state1: S, state2: S) = {
    transitionMap.get(state1) match {
      case Some(transitionSet) => transitionSet.probabilityFor(state2)
      case None => 0
    }
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

  def totalCount() = {
    val counts = transitionCounter.values
    counts.foldLeft(0)((a, b) => a+b)
  }

  def probabilityFor(state: S) = {
    countFor(state).toFloat/this.totalCount
  }

}
// vim: set ts=4 sw=4 et:
