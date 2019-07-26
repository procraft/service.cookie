import scala.concurrent.ExecutionContext

import akka.actor.ActorSystem

import io.grpc.ServerBuilder

import libicraft.{{cookiecutter.service_package}}.{{cookiecutter.service_camel}}Grpc
import controllers.GrpcHandler
import utils.Logging


/**
 * gRPC server initializer
 */
class GrpcServer(
  actorSystem: ActorSystem,
  config: {{cookiecutter.service_camel}}Config,
  grpcHandler: GrpcHandler,
)(implicit ec: ExecutionContext) extends Logging {

  val server = ServerBuilder.forPort(config.grpc.port)
    .addService({{cookiecutter.service_camel}}Grpc.bindService(grpcHandler, ec))
    .build

  // immediately start the server
  start()

  def start(): Unit = {
    server.start()
    println(s"gRPC server started, listening on ${config.grpc.port}")

    actorSystem.registerOnTermination {
      System.err.println("*** shutting down gRPC server since actor system is shutting down")
      stop()
    }
  }

  private def stop(): Unit = {
    server.shutdown()
  }
}
