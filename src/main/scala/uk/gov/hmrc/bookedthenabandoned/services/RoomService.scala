package uk.gov.hmrc.bookedthenabandoned.services

import cats.effect.IO
import io.circe._
import io.circe.generic.semiauto._
import org.http4s.EntityDecoder
import org.http4s.circe._



object RoomService {
  def rooms: List[Room] = {
    List(
      Room("BP9001"),
      Room("BP9002"),
      Room("Pink Show and Tell")
    )
  }
}

case class Room(id: String)

object Room {
  implicit val jsonDecoder: Decoder[Room] = deriveDecoder[Room]
  implicit val jsonEncoder: Encoder[Room] = deriveEncoder[Room]

  implicit val entityDecoder: EntityDecoder[IO, Room] = jsonOf[IO, Room]
}

