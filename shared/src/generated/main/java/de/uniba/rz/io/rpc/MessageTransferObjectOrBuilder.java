// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: ticketManagement.proto

package de.uniba.rz.io.rpc;

public interface MessageTransferObjectOrBuilder extends
    // @@protoc_insertion_point(interface_extends:MessageTransferObject)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.TicketTransferObject ticket = 1;</code>
   */
  boolean hasTicket();
  /**
   * <code>.TicketTransferObject ticket = 1;</code>
   */
  de.uniba.rz.io.rpc.TicketTransferObject getTicket();
  /**
   * <code>.TicketTransferObject ticket = 1;</code>
   */
  de.uniba.rz.io.rpc.TicketTransferObjectOrBuilder getTicketOrBuilder();

  /**
   * <code>.ListOfTicketTransferObject response = 2;</code>
   */
  boolean hasResponse();
  /**
   * <code>.ListOfTicketTransferObject response = 2;</code>
   */
  de.uniba.rz.io.rpc.ListOfTicketTransferObject getResponse();
  /**
   * <code>.ListOfTicketTransferObject response = 2;</code>
   */
  de.uniba.rz.io.rpc.ListOfTicketTransferObjectOrBuilder getResponseOrBuilder();

  /**
   * <code>string operation = 3;</code>
   */
  java.lang.String getOperation();
  /**
   * <code>string operation = 3;</code>
   */
  com.google.protobuf.ByteString
      getOperationBytes();
}
