package uk.gov.hmrc.bookedthenabandoned.services

import java.time.Instant
import java.time.temporal.TemporalAmount

import cats.effect.IO
import io.circe._
import io.circe.generic.semiauto._
import org.http4s.EntityDecoder
import org.http4s.circe._
import io.circe.java8.time._

import scala.collection.mutable

trait RoomService {
  def rooms: List[Room]

  def update(id: String, lastUsed: Instant): Unit
}

object RoomService extends RoomService {

  private val roomsStore = mutable.Map[String, Instant]()

  private def checkInUse(instant: Instant): Boolean = instant.isAfter(Instant.now().minusSeconds(5 * 60))

  def rooms: List[Room] = {
    roomsStore.map {
      case (s, d) => Room(s, d, checkInUse(d))
    }.toList
  }

  override def update(id: String, lastUsed: Instant): Unit = {
    roomsStore += (id -> lastUsed)
  }
}

case class Room(id: String, lastUsed: Instant, inUse: Boolean)

object Room {
  implicit val jsonDecoder: Decoder[Room] = deriveDecoder[Room]
  implicit val jsonEncoder: Encoder[Room] = deriveEncoder[Room]

  implicit val entityDecoder: EntityDecoder[IO, Room] = jsonOf[IO, Room]
}

