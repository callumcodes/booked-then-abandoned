package uk.gov.hmrc.bookedthenabandoned

import java.time.LocalDateTime

import cats.effect.IO
import fs2.StreamApp
import io.circe._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeBuilder
import uk.gov.hmrc.bookedthenabandoned.services.RoomService

import scala.concurrent.ExecutionContext.Implicits.global

object RoomMonitorServer extends StreamApp[IO] with Http4sDsl[IO] {
  val service = HttpService[IO] {
    case GET -> Root / "hello" / name =>
      Ok(Json.obj("message" -> Json.fromString(s"Hello, ${name}")))
    case GET -> Root / "rooms" => {
      Ok(RoomService.rooms.asJson)
    }
    case POST -> Root / "room" / roomNumber =>
      val now = LocalDateTime.now()
      Ok(s"Logged movement in $roomNumber at $now")
  }

  def stream(args: List[String], requestShutdown: IO[Unit]) =
    BlazeBuilder[IO]
      .bindHttp(5000, "0.0.0.0")
      .mountService(service, "/")
      .serve
}

