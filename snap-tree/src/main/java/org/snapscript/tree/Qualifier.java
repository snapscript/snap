package org.snapscript.tree;

public interface Qualifier {
   String[] getSegments();
   String getQualifier();
   String getLocation();
   String getTarget();
   String getName();
}