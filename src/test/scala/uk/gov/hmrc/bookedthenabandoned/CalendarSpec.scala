package uk.gov.hmrc.bookedthenabandoned

import cats.effect.IO
import org.http4s._
import org.http4s.implicits._
import org.specs2.matcher.MatchResult

class CalendarSpec extends org.specs2.mutable.Specification {

  "Calendar Events" >> {
    "return 200" >> {
      eventUriReturns200()
    }
    //    "return json" >> {
    //      eventUriReturnsEvents()
    //    }
  }

  private[this] val retEvents: Response[IO] = {
    val getHW = Request[IO](Method.GET, Uri.uri("/events"))
    RoomMonitorServer.service.orNotFound(getHW).unsafeRunSync()
  }

  private[this] def eventUriReturns200(): MatchResult[Status] =
    retEvents.status must beEqualTo(Status.Ok)

  //  private[this] def eventUriReturnsEvents() =
  //    retEvent.as[String].unsafeRunSync() must be ("Get calendar events")
}
