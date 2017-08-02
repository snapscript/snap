package org.snapscript.common.command;

public interface Command {
   Console execute(Environment environment) throws Exception;
}