package utils

case class DbException(msg: String) extends Exception(msg)
case class WrongArgumentException(msg: String) extends Exception(msg)
case class ExternalApiException(msg: String) extends Exception(msg)
case class ConsistencyException(msg: String) extends Exception(msg)

case class NotFoundException(msg: String) extends Exception(msg)
object NotFoundException {
  def apply(modelClass: String, id: Any): NotFoundException = NotFoundException(s"Object $modelClass($id) not found.")
}
