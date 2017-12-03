package org.snapscript.tree.operation;

import org.snapscript.core.Evaluation;
import org.snapscript.tree.math.NumericOperator;

public interface CalculationPart {
   Evaluation getEvaluation();
   NumericOperator getOperator();
}