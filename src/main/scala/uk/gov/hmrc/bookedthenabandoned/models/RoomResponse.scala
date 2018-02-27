package uk.gov.hmrc.bookedthenabandoned.models

import cats.effect.IO
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf
import uk.gov.hmrc.bookedthenabandoned.services.Room

case class RoomResponse(rooms: List[Room])
object RoomResponse {
  implicit val jsonDecoder: Decoder[RoomResponse] = deriveDecoder[RoomResponse]
  implicit val jsonEncoder: Encoder[RoomResponse] = deriveEncoder[RoomResponse]

  implicit val entityDecoder: EntityDecoder[IO, RoomResponse] = jsonOf[IO, RoomResponse]
}
