package com.example

import akka.actor.{Props, Actor, ActorSystem}
import akka.routing.{RoundRobinRoutingLogic, Router, ActorRefRoutee}
import akka.util.ByteString
import com.example.TcpHandler.Command

/**
 * Created by infinitu on 15. 3. 2..
 */
object Dispatcher extends {
  val dispatcherRouter = {
    val routees = Vector.fill(100) {
      val r = Server.actorSystem.actorOf(Props[Dispatcher])
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }
}
class Dispatcher extends Actor{
  import akka.io.Tcp._
  
  implicit def string2ByteString(str:String):ByteString = ByteString(str)
  
  override def receive = {
    case Command("0x1001",commandSender, data)=> //Say Hello
      commandSender ! Write("Hello world!")
    case Command("0x2001",commandSender, data)=> //Echo
      commandSender ! Write(data)
      commandSender ! Close
  }
}
