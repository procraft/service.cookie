package db

import slick.jdbc.PostgresProfile

import com.github.tminglei.slickpg.PgDate2Support

import models.AppleKind


/**
 * Slick pg-profile with custom type-mappers
 * Should be used as `import CdsPostgresProfile.api._` instead of `import slick.jdbc.PostgresProfile.api._`
 */
trait {{cookiecutter.service_camel}}PostgresProfile extends PostgresProfile with PgEnumeratumSupport with PgDate2Support {
  override val api = new API {}

  // this trait declaration is mandatory, otherwise doesn't work in scala 2.12
  trait API extends super.API with DateTimeImplicits {
    implicit val engineKindMapper = createEnumeratumJdbcType(AppleKind)
  }
}

/**
 * Workaround for the scala 2.12 bug
 * @see https://github.com/tminglei/slick-pg/issues/367#issuecomment-360303102
 */
object {{cookiecutter.service_camel}}PostgresProfile extends {{cookiecutter.service_camel}}PostgresProfile
