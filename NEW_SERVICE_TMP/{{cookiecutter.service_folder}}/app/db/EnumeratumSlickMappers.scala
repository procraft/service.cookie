package db

import scala.reflect.ClassTag

import slick.jdbc.PostgresProfile.api._
import slick.jdbc.{PositionedParameters, PositionedResult, PostgresProfile, SetParameter, GetResult ⇒ GR}

import com.github.tminglei.slickpg.PgEnumSupport
import enumeratum.{Enum, EnumEntry}


/**
 * DRY helper to easily define Slick column-type mappers for Enumeratum
 * Works only with VARCHAR db type, for Postgres Enum type support see `PgEnumeratumSupport`
 * Usage:
 * ```
 * object ColumnImplicits {
 *   implicit val fooMapper = EnumeratumSlickMappers(Foo).simple
 * }
 * import ColumnImplicits._
 * ```
 */
class EnumeratumSlickMappers[T <: EnumEntry : ClassTag](enum: Enum[T]) {
  implicit lazy val simple = MappedColumnType.base[T, String](
    value ⇒ value.entryName,
    string ⇒ enum.withName(string)
  )

  implicit val getResult: GR[T] = new GR[T] {
    override def apply(positionedResult: PositionedResult): T = enum.withName(positionedResult.nextString())
  }

  implicit val optionGetResult: GR[Option[T]] = new GR[Option[T]] {
    override def apply(positionedResult: PositionedResult): Option[T] =
      positionedResult.nextStringOption().flatMap(enum.withNameOption)
  }

  implicit val setParameter: SetParameter[T] = new SetParameter[T] {
    override def apply(value: T, positionedParameter: PositionedParameters): Unit =
      positionedParameter.setString(value.toString)
  }

  implicit val optionSetParameter: SetParameter[Option[T]] = new SetParameter[Option[T]] {
    override def apply(value: Option[T], positionedParameter: PositionedParameters): Unit =
      positionedParameter.setStringOption(value.map(v ⇒ v.entryName))
  }
}

object EnumeratumSlickMappers {
  def apply[T <: EnumEntry : ClassTag](enum: Enum[T]) = new EnumeratumSlickMappers(enum)
}

/**
 * Support for postgres enum type mapping using slick-pg helpers
 */
trait PgEnumeratumSupport extends PgEnumSupport {
  driver: PostgresProfile ⇒

  def createEnumeratumJdbcType[T <: EnumEntry : ClassTag](enum: Enum[T]) =
    createEnumJdbcType[T](
      getEnumeratumNameString(enum),
      _.entryName, string ⇒ enum.withName(string),
      true
    )

  def createEnumeratumListJdbcType[T <: EnumEntry : ClassTag](enum: Enum[T]) =
    createEnumListJdbcType[T](
      getEnumeratumNameString(enum),
      _.entryName, string ⇒ enum.withName(string),
      true
    )

  private def getEnumeratumNameString(enum: Enum[_]): String = {
    val className = enum.getClass.toString
    className.substring(0, className.length - 1).split("\\$").last,
  }
}
