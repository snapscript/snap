package org.snapscript.core;

import org.snapscript.core.result.Result;

public interface TypeBody {
   void allocate(Scope scope, Type type) throws Exception; // static stuff   
   Result execute(Scope scope, Type type) throws Exception; // instance stuff
}

