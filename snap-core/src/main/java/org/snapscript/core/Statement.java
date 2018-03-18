package org.snapscript.core;

public abstract class Statement {
   
   public void create(Scope scope) throws Exception {}
   public void define(Scope scope) throws Exception {}
   public abstract Execution compile(Scope scope) throws Exception;
}