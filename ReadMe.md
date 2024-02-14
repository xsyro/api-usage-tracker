# API Billing Service
This service is tasked with furnishing billing records to customers for their API usage.

## Approach
1. **Identify Data Sources:** Determine which HTTP services are responsible for generating data relevant to our billing system. This could include our API service for IP to location lookup, as well as any other services that generate usage data
2. **Standardize Data Format:** Ensure that all HTTP services produce data in a standardized format that can be easily processed and aggregated. In this case, we choose JSON data format which is commonly used for inter-service communication
3. **Implement Data Ingestion:** We'd provide an accessible HTTP endpoint to enable billing service ingest record for billing purpose
4. **Capture Billing as an Event Queue -** Upon HTTP request to billing-service. We provide that as an event in our queuing system for later processing. Thus, equally avoiding delay or lost of billing data from the caller service.
5. **Persist Records to our Logging Database -** Dequeue from the events system and persist to SQL. In the case of this, we've used In-memory H2-Database for demonstration purpose. We could later choose on what storage engine upon discussion on scalability and budgets. 
6. Aggregate Record for Audit and Reports Purpose

## How to Use
The Billing Service provides an API endpoint that provide details about your service. Thus, allowing billing service to ingest data from your service for it billing purpose.
Simply by using HTTP Request interceptor or middleware to cURL the `api-billing-service` and provide the necessary POST request body. Furthermore, this's advisable to be done in asynchronous fashion.

##### Find example below

###### **cURL**
````
POST http://127.0.0.1:8081/v1/api/api-billing/ingest
Content-Type: application/json

{
  "timestamp": 1707909068276,
  "customerId": 4,
  "endpoint": "https://whatismyip.example.comiplookup?ip=127.0.0.1",
  "serviceTag": "lookup-ip2location"
}
````

###### **NodeJs Example**

````
function logMiddleware(req, res, next) {
  //use axios to POST too the billing-api
  var data = {
    ...
    customerId: ???
    serviceTag: 'ip2locationLookup'
  }
  axios.post('http://127.0.0.1:8081/v1/api/api-billing/ingest', data).then(resp => {
    console.log(resp)
    next();
  })
}

router.post('/my-service/ip-2-location-look-up', logMiddleware, (req, res) => {
  // do something;

  return res.sendStatus(200);
});
````

#### How to Run
Point your terminal `cwd` to the project root directory and run `mvn spring-boot:run`

That's it! 