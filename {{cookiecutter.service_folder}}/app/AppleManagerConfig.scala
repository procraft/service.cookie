import play.api.Configuration

import com.typesafe.config.Config
import pureconfig.loadConfig
import pureconfig.generic.auto._


/**
 * Typesafe config (HOCON) configuration mapped to the convenient case class
 */
case class {{cookiecutter.service_camel}}Config(
  verbose: Boolean,
  grpc: GrpcConf,
)

case class GrpcConf(port: Int)

/**
 * Play Configuration to PureConfig configuration-conversion provider.
 * Have to use Play's Configuration wrapper to provide Play's convenient `PlayKeys.devSettings`
 *  configuration overriding feature in build.sbt
 */
class {{cookiecutter.service_camel}}ConfigProvider(config: Configuration) {
  def get: {{cookiecutter.service_camel}}Config = {
    loadConfig[{{cookiecutter.service_camel}}Config](config.get[Config]("libicraft.{{cookiecutter.service_folder}}")) match {
      case Right(conf) ⇒ conf
      case Left(error) ⇒
        throw new RuntimeException("Cannot read config file, errors:\n" + error.toList.mkString("\n"))
    }
  }
}
