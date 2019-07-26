package utils

import play.api.Logger


/**
 * Mixin play logging into class with class name as a qualifier
 */
trait Logging {
  protected val log = Logger(this.getClass)
}
