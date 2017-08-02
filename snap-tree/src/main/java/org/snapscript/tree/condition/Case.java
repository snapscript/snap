package org.snapscript.tree.condition;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Statement;

public interface Case {
   Evaluation getEvaluation();
   Statement getStatement();
}