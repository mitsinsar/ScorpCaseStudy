# Scorp Case Study

Simple app that lists people which are fetched from mock network called `PeopleDataSource`.

Changes
---
There have been some changes on provided files. These changes are;

* `Person.kt` was renamed as `PersonResponse.kt` to make it obvious that this class belongs to `data` layer.
* `DataSource` was renamed as `PeopleDataSource` to make it more specific that it belongs to `people`.
* `FetchError` used to have `String`. Now it has `Throwable`, to be able to make error messages more user friendly/localized.

APK
---
To install the app, `.apk` can be found in `ScorpCaseStudy/app-debug.apk`. It is an unsigned apk (signed with debug key).

Tests
---
There is only one test class called `GetPeopleUseCaseTest` which tests `GetPeopleUseCaseImpl` class.

Dependency Graph
---
<img src="https://github.com/mitsinsar/ScorpCaseStudy/blob/ff63dc7456ec58e6659618deb2c911b62e859fb2/media/dependency_graph.png"
alt="Dependency graph"
width="358"
height="650">

Known issues
---
When `PeopleFragment` is launched, it fetches item list once, then checks if listed items are longer than the `RecyclerView`. If not, it fetches another item list. This causes an UI issue.

Solution would be to calculate `ViewHolder` and `RecyclerView` heights and find min item count should be fetched before updating the UI.
