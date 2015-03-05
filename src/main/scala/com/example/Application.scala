package com.example

import akka.actor.Props
import com.example.Server.Start

/**
 * Created by infinitu on 15. 3. 2..
 */
object Application {
  def main(arg:Array[String]){
    val system = Server.actorSystem
    val server = system.actorOf(Props[Server])
    server ! Start(9090)
  }
}
