package db

import scala.concurrent.{ExecutionContext, Future}

import slick.model

import db.{{cookiecutter.service_camel}}PostgresProfile.api._
import models.{Apple, AppleKind}
import utils.NotFoundException


class Apples(tableTag: Tag) extends Table[Apple](tableTag, "ads") {
  def * = (color, kind, id) <> (Apple.tupled, Apple.unapply)

  def color = column[String]("color")
  def kind = column[AppleKind]("kind")
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
}

object Apples extends TableQuery(new Apples(_)) {
  val findById = this.findBy(_.id)

  def apply(id: Int)(implicit ec: ExecutionContext) = {
    findById(id).result.headOption.map(_.getOrElse(throw NotFoundException("Apple", id)))
  }

  def withObj = this returning this

  def createDba(apple: Apple) = this.withObj += apple
}

class AppleRepo(db: Database)(implicit val ec: ExecutionContext) {

  def findById(id: Int): Future[Option[Apple]] = db.run(
    Apples.findById(id).result.headOption
  )
}
