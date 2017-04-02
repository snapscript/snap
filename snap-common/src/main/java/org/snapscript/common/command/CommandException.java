package org.snapscript.common.command;

public class CommandException extends Exception {

   public CommandException(String message) {
      super(message);
   }

   public CommandException(String message, Throwable cause) {
      super(message, cause);
   }

}
