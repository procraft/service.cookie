import play.api.ApplicationLoader.Context
import play.api.db.Database
import play.api.db.evolutions.EvolutionsComponents
import play.api.db.slick.evolutions.SlickEvolutionsComponents
import play.api.db.slick.{DbName, SlickComponents}
import play.api.{Application, ApplicationLoader, BuiltInComponentsFromContext, LoggerConfigurator, NoHttpFiltersComponents}

import com.softwaremill.macwire._

import router.Routes
import controllers.GrpcHandler
import db.{AppleRepo, {{cookiecutter.service_camel}}PostgresProfile}


/**
 * Main application entry point
 */
class {{cookiecutter.service_camel}}Loader extends ApplicationLoader {
  def load(context: Context): Application = {
    // must be before everything
    // see https://www.playframework.com/documentation/2.7.x/ScalaCompileTimeDependencyInjection#Configuring-Logging
    LoggerConfigurator(context.environment.classLoader).foreach {
      _.configure(context.environment, context.initialConfiguration, Map.empty)
    }

    val components = new AppComponents(context)

    // this will actually run the database migrations on startup
    components.applicationEvolutions

    // init app entry points and schedules
    components.grpcServer

    // play
    components.application
  }
}

/**
 * Compile-time DI configuration using MacWire lib
 * @see https://www.playframework.com/documentation/2.7.x/ScalaCompileTimeDependencyInjection#Compile-Time-Dependency-Injection
 *      https://github.com/softwaremill/macwire
 */
class AppComponents(context: Context)
  extends BuiltInComponentsFromContext(context)
    with NoHttpFiltersComponents
    with SlickComponents
    with SlickEvolutionsComponents
    with EvolutionsComponents {

  private implicit def as = actorSystem

  val x: Database = dbApi.database("default")

  private lazy val db: {{cookiecutter.service_camel}}PostgresProfile.api.Database =
    slickApi.dbConfig[{{cookiecutter.service_camel}}PostgresProfile](DbName("default")).db

  private lazy val appleManagerConfig = wire[{{cookiecutter.service_camel}}ConfigProvider].get

  // grpc server
  private lazy val grpcHandler = wire[GrpcHandler]
  lazy val grpcServer = wire[GrpcServer]

  // repos
  private lazy val appleRepo = wire[AppleRepo]

  // logic

  // play
  lazy val router = {
    val routePrefix: String = "/"
    wire[Routes]
  }
}
