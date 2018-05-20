# Simple Proxy REST Receiver
This service provide a proxy to subscribe a Redis queue and forward the message to a REST call.

## Workflow
* Receive message from subscribed queue
* Deserialize the message and read the ID attribute value with the configured identity key
* Package the target URL along with the url template
* Make a REST call to to the target URL with the pre-defined configuration

#### Assumption
* The downstream systems are using basic HTTP Authentication
* entity key value attached to the end of the URL as a path variable.
