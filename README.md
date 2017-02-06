AngularJS API for Scala.js
================================
This is a Scala.js type-safe binding for [AngularJS 1.5.x](https://docs.angularjs.org/api)

AngularJS is a structural framework for dynamic web apps. It lets you use HTML as your template 
language and lets you extend HTML's syntax to express your application's components clearly and 
succinctly. AngularJS's data binding and dependency injection eliminate much of the code you 
would otherwise have to write.

The following AngularJS services have been implemented thus far:

| Service           | Artifact ID                    | Description                      |
|-------------------|--------------------------------|----------------------------------|
| $anchorScroll     | angularjs-anchor-scroll        | Anchor Scroll Service. |
| $animate          | angularjs-animate              | The $animate service exposes a series of DOM utility methods that provide support for animation hooks. |
| $cacheFactory     | angularjs-core                 | Factory that constructs cache objects and gives access to them. |
| $compile          | angularjs-core                 | Compiles an HTML string or DOM into a template and produces a template function, which can then be used to link scope and the template together. |
| $cookies          | angularjs-cookies              | Provides read/write access to browser's cookies. |
| $cookieStore      | angularjs-cookies              | Provides a key-value (string-object) storage, that is backed by session cookies. |
| $exceptionHandler | angularjs-core                 | Any uncaught exception in angular expressions is delegated to this service. |
| $filter           | angularjs-core                 | Selects a subset of items from array and returns it as a new array. |
| $http             | angularjs-core                 | The $http service is a core Angular service that facilitates communication with the remote HTTP servers via the browser's XMLHttpRequest object or via JSONP. |
| $injector         | angularjs-core                 | $injector is used to retrieve object instances as defined by provider, instantiate types, invoke methods, and load modules. |
| $interval         | angularjs-core                 | Angular's wrapper for window.setInterval. The fn function is executed every delay milliseconds. |
| $location         | angularjs-core                 | The $location service parses the URL in the browser address bar (based on the window.location) and makes the URL available to your application. |
| $log              | angularjs-core                 | Simple service for logging. Default implementation safely writes the message into the browser's console (if present). |
| $modal            | angularjs-ui-bootstrap         | Modal Dialogs - Angular UI Bootstrap |
| $nvd3             | angularjs-nvd3                 | An AngularJS directive for NVD3 re-usable charting library (based on D3).|
| $parse            | angularjs-core                 | Converts Angular expression into a function. |
| $q                | angularjs-core                 | A service that helps you run functions asynchronously, and use their return values (or exceptions) when they are done processing. |
| $resource         | angularjs-core                 | A factory which creates a resource object that lets you interact with RESTful server-side data sources. |
| $route            | angularjs-ui-router            | $route is used for deep-linking URLs to controllers and views (HTML partials). It watches $location.url() and tries to map the path to an existing route definition. |
| $sce              | angularjs-sanitize             | $sce is a service that provides Strict Contextual Escaping services to AngularJS. |
| $timeout          | angularjs-core                 | Angular's wrapper for window.setTimeout. The fn function is wrapped into a try/catch block and delegates any exceptions to $exceptionHandler service. |
| FacebookService   | angularjs-facebook             | AngularJS - Facebook service |
| FileUploader      | angularjs-nergvh-fileupload    | AngularJS File Uploader |
| md5               | angularjs-md5                  | A md5 crypto component for Angular.js. |
| toaster           | angularjs-toaster              | AngularJS Toaster is a customized version of "toastr" non-blocking notification javascript library. |


#### Build Requirements

* [ScalaJs.io v0.3.x](https://github.com/ldaniels528/scalajs.io)
* [SBT v0.13.13](http://www.scala-sbt.org/download.html)


#### Build/publish the SDK locally

```bash
 $ sbt clean publish-local
```

#### Examples

##### Service Example

```scala
import io.scalajs.npm.angularjs._
import io.scalajs.npm.angularjs.http._

module.serviceOf[MarketStatusService]("MarketStatusSvc")


class MarketStatusService($http: Http) extends Service {

  def getMarketStatus(implicit ec: ExecutionContext): Future[MarketStatus] = {
    $http.get[MarketStatus]("/api/tradingClock/status")
  }
  
}

@ScalaJSDefined
class MarketStatus(var stateChanged: Boolean,
                   var active: Boolean,
                   var sysTime: Double,
                   var delay: Double,
                   var start: Double,
                   var end: Double) extends js.Object 
```

##### Directive Example

```scala
module.directiveOf[ChangeArrowDirective]("changearrow")

class ChangeArrowDirective extends Directive[ChangeArrowDirectiveScope] {
  override val restrict = "E"
  override val scope = JS(value = "@value")
  override val transclude = true
  override val replace = false
  override val template = """<i ng-class="icon"></i>"""

  override def link(scope: ChangeArrowDirectiveScope, element: JQLite, attrs: Attributes) = {
    scope.$watch("value", { (newValue: js.UndefOr[Any], oldValue: js.UndefOr[Any]) =>
      scope.icon = newValue.toOption flatMap getNumericValue map {
        case v if v > 0 => "fa fa-arrow-up positive"
        case v if v < 0 => "fa fa-arrow-down negative"
        case _ => "fa fa-minus null"
      } orNull
    })
  }

  private def getNumericValue(newValue: Any): Option[Double] = newValue match {
    case n: Number => Some(n.doubleValue)
    case s: String if s.nonEmpty => Try(s.toDouble).toOption
    case _ => None
  }
}

@js.native
trait ChangeArrowDirectiveScope extends Scope {
  var value: js.UndefOr[Any] = js.native
  var icon: String = js.native
}

object ChangeArrowDirectiveScope {
  def apply(): ChangeArrowDirectiveScope = {
    val scope = new js.Object().asInstanceOf[ChangeArrowDirectiveScope]
    scope.icon = null
    scope
  }
}
```

##### Durations

ScalaJs.io provides implicit conversions so that you may use `scala.concurrent.duration.FiniteDuration`s with `$timeout`,
`$interval`, and any other services that use time in milliseconds.

```scala
$timeout(() => doSomething(), 5.minutes)
```

##### JSON data as Scala objects

ScalaJs.io allows you to utilize dynamic JavaScript objects or type-safe Scala objects using traits. 
Consider the following example:

```scala
$http.get[js.Dynamic]("/api/tradingClock/status") onComplete {
    case Success(status) => console.log(angular.toJson(status))
    case Failure(e) => console.error(e.getMessage)
}
```

Above we retrieve a JSON object (see below) in much the same fashion as you would using native JavaScript. Sometimes
one may want to retrieve the data as a `js.Dynamic` because of the flexibility it offers.

```json
{"stateChanged":false,"active":false,"sysTime":1392092448795,"delay":-49848795,"start":1392042600000,"end":1392066000000}
```

However, sometimes we instead want to retrieve the data as a type-safe Scala object. ScalaJs.io makes this as simple as:

```scala
$http.get[MarketStatus]("/api/tradingClock/status") onComplete {
    case Success(status) => console.log(angular.toJson(status))
    case Failure(e) => console.error(e.getMessage)
}

@js.native
trait MarketStatus extends js.Object {
    var stateChanged: Boolean = js.native
    var active: Boolean = js.native
    var sysTime: Double = js.native
    var delay: Double = js.native
    var start: Double = js.native
    var end: Double = js.native
}
```

Above, we retrieve a JSON object via the given API and return a Market Status object.

##### For Comprehensions

ScalaJs.io provides implicit conversions that convert a `HttpResponse[T]` into a `Future[T]`, we can also use `for`
comprehensions when we need to combine data from multiple API calls.

```scala
val outcome = for {
   symbols <- contestService.getHeldSecurities(playerId)
   quotes <- quoteService.getStockQuoteList(symbols)
 } yield quotes
```

The above code uses Scala's for comprehension to first retrieve a collection of symbols (via REST) and then use the 
symbols to then retrieve a collection of quotes (also via REST). 

To do the same in JavaScript would've required creating 
multiple promises (via $q) or ugly callbacks, and then there's the results gathering/error handling... which would also 
be more painful in JavaScript. 

Here's how it's handled via Scala.js:

```scala
outcome onComplete {
    case Success(updatedQuotes) => obj.quotes = updatedQuotes
    case Failure(e) =>
        toaster.error("Failed to load Held Securities")
        console.error(s"Failed to load Held Securities: ${e.getMessage}")
}
```


#### Artifacts and Resolvers

To add the Moment binding to your project, add the following to your build.sbt:  

```sbt
libraryDependencies += "io.scalajs.npm" %%% "angularjs-bundle" % "0.3.0.3"
```

Optionally, you may add the Sonatype Repository resolver:

```sbt   
resolvers += Resolver.sonatypeRepo("releases") 