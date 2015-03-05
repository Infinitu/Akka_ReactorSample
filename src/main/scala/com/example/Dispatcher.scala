package com.example

import akka.actor.{Actor, ActorSystem}
import akka.util.ByteString
import com.example.TcpHandler.Command

/**
 * Created by infinitu on 15. 3. 2..
 */
object Dispatcher extends {
  def actorSystem = ActorSystem("dispatcher")
}
class Dispatcher extends Actor{
  import akka.io.Tcp._
  
  implicit def string2ByteString(str:String):ByteString = ByteString(str)
  
  override def receive = {
    case Command("0x1001",commandSender, data)=> //Say Hello
      commandSender ! Write("Hello world!")
    case Command("0x2001",commandSender, data)=> //Echo
      commandSender ! Write(data)
  }
}
