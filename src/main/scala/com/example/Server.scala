package com.example

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorSystem, Props}
import akka.io.Inet.SocketOption
import akka.io.Tcp._
import akka.io.{IO, Tcp}

import scala.collection.immutable

/**
 * Created by infinitu on 15. 3. 2..
 */
object Server {

  def actorSystem = ActorSystem("server")

  case class Start(port: Int,
                   backlog: Int = 100,
                   options: immutable.Traversable[SocketOption] = Nil,
                   pullMode: Boolean = false)

  case class Stop()

}
class Server extends Actor{
  import com.example.Server._
  import context.system
  val io = IO(Tcp)


  override def receive= {
    case Start(port, backlog, options, pullMode) =>
      io ! Bind(self, new InetSocketAddress(port), backlog, options, pullMode)
      context become started
  }
  
  def started:Receive ={
    case Stop()=>
      //stop
    case c @ Connected(remote, local) ⇒
      val handler = context.actorOf(Props[TcpHandler])
      val connection = sender
      connection ! Register(handler)
  }
}
