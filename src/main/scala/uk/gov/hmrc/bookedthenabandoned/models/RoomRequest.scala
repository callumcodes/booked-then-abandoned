package uk.gov.hmrc.bookedthenabandoned.models

import cats.effect.IO
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.{Decoder, Encoder}
import org.http4s.EntityDecoder
import org.http4s.circe.jsonOf

case class RoomRequest(lastUsed: Int)
object RoomRequest {
  implicit val jsonDecoder: Decoder[RoomRequest] = deriveDecoder[RoomRequest]
  implicit val jsonEncoder: Encoder[RoomRequest] = deriveEncoder[RoomRequest]

  implicit val entityDecoder: EntityDecoder[IO, RoomRequest] = jsonOf[IO, RoomRequest]
}
