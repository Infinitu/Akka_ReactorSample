package com.example

import akka.actor.{Actor, ActorRef, Props}
import akka.util.ByteString

/**
 * Created by infinitu on 15. 3. 2..
 */
object TcpHandler{
  
  case class Command(header:String, sender:ActorRef, data:ByteString)
  
}

class TcpHandler extends Actor{
  import akka.io.Tcp._
  import com.example.TcpHandler._
  
  override def receive = {
    case Received(data) ⇒
      val header = new String(data.slice(0,6).toArray)
      val command = Command(header, sender(), data.drop(7))
      Dispatcher.dispatcherRouter.route(command,sender())
    case PeerClosed     ⇒ context stop self
  }
}
