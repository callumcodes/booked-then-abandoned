package uk.gov.hmrc.bookedthenabandoned

import java.time.Instant

import cats.effect.IO
import fs2.StreamApp
import io.circe._
import io.circe.syntax._
import org.http4s.HttpService
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.blaze.BlazeBuilder
import uk.gov.hmrc.bookedthenabandoned.models.RoomRequest
import uk.gov.hmrc.bookedthenabandoned.services.{Room, RoomService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try

object RoomMonitorServer extends StreamApp[IO] with Http4sDsl[IO] {
  val service = HttpService[IO] {
    case GET -> Root / "hello" / name =>
      Ok(Json.obj("message" -> Json.fromString(s"Hello, ${name}")))
    case GET -> Root / "rooms" => {
      Ok(RoomService.rooms.asJson)
    }
    case req@PUT -> Root / "room" / roomNumber =>
      req.decodeJson[RoomRequest].flatMap { request =>
        val instant = Instant.ofEpochSecond(request.lastUsed)
        RoomService.update(Room(roomNumber, instant))
        Ok(s"Logged movement in $roomNumber at $instant")
      }
  }

  def stream(args: List[String], requestShutdown: IO[Unit]) =
    BlazeBuilder[IO]
      .bindHttp(Try(System.getProperty("http.port").toInt).getOrElse(8080), "0.0.0.0")
      .mountService(service, "/")
      .serve
}

