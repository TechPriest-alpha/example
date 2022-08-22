Attempt to implement very basic app with Spring and scala3.

So far there was a moderate success.

Certain facilities seem to be working more or less properly (injection, bean search). Though getting Scala type system
to work with Spring methods took some effort  (for future - scala error messages and available documentation are at best 'scarce')
.

Current issue is that Mocks won't proxy Scala classes, thus making certain checks in tests difficult.
On one hand - using mocks is a tradeoff, so probably its for good. On the other hand - more boilerplate might be required.