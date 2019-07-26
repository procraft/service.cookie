package controllers

import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}

import libicraft.{{cookiecutter.service_package}}.{AppleData, {{cookiecutter.service_camel}}Grpc, FindAppleRequest, FindAppleResponse}
import db.{{cookiecutter.service_camel}}PostgresProfile.api._
import db.AppleRepo
import models.Apple


/**
 * Implementation of gRPC server
 */
class GrpcHandler @Inject() (
  appleRepo: AppleRepo,
  db: Database,
)(implicit ec: ExecutionContext) extends {{cookiecutter.service_camel}}Grpc.{{cookiecutter.service_camel}} {

  import GrpcHandler._

  def findApple(request: FindAppleRequest): Future[FindAppleResponse] = {
    appleRepo.findById(request.id).map(appleOpt ⇒ FindAppleResponse(appleOpt.map(getAppleData)))
  }

}

object GrpcHandler {
  private def getAppleData(apple: Apple) = {
    AppleData(
      color = apple.color,
      id = apple.id,
    )
  }
}
