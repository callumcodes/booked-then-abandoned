package uk.gov.hmrc.bookedthenabandoned.services

import java.time.{LocalDate, LocalDateTime}

import cats.effect.IO
import io.circe._
import io.circe.generic.semiauto._
import org.http4s.EntityDecoder
import org.http4s.circe._
import io.circe.java8.time._

import scala.collection.mutable

trait RoomService {
  def rooms: List[Room]

  def update(room: Room): Unit
}

object RoomService extends RoomService {

  private val roomsStore = mutable.Map[String, LocalDateTime]()

  def rooms: List[Room] = {
    roomsStore.map {
      case (s, d) => Room(s, d)
    }.toList
  }

  override def update(room: Room): Unit = {
    roomsStore += (room.id -> room.lastUsed)
  }
}

case class Room(id: String, lastUsed: LocalDateTime)

object Room {
  implicit val jsonDecoder: Decoder[Room] = deriveDecoder[Room]
  implicit val jsonEncoder: Encoder[Room] = deriveEncoder[Room]

  implicit val entityDecoder: EntityDecoder[IO, Room] = jsonOf[IO, Room]
}

