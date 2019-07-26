package models

import enumeratum.{Enum, EnumEntry}

import libicraft.{{cookiecutter.service_package}}

case class Apple(
  color: String,
  kind: AppleKind,
  id: Int = 0,
)

sealed trait AppleKind extends EnumEntry {
  def toProto: {{cookiecutter.service_package}}.AppleKind =
    {{cookiecutter.service_package}}.AppleKind.fromName(entryName).get
}

object AppleKind extends Enum[AppleKind] {
  case object Good extends AppleKind
  case object Bad extends AppleKind

  val values = findValues

  def fromProto(proto: {{cookiecutter.service_package}}.AppleKind): AppleKind =
    AppleKind.withName(proto.name)
}
