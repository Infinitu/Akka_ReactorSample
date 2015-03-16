package com.example

import akka.actor.Props
import akka.routing._
import com.example.Server.Start

/**
 * Created by infinitu on 15. 3. 2..
 */
object Application {
  def main(arg:Array[String]){
    val system = Server.actorSystem
    val server = system.actorOf(Props[Server],"server")


    
    server ! Start(9090)
  }
}
