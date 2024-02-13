# API Billing Service
This service is tasked with furnishing billing records to customers for their API usage.

## Approach
1. **Identify Data Sources:** Determine which HTTP services are responsible for generating data relevant to our billing system. This could include our API service for IP to location lookup, as well as any other services that generate usage data
2. **Standardize Data Format:** Ensure that all HTTP services produce data in a standardized format that can be easily processed and aggregated. In this case, we choose JSON data format which is commonly used for inter-service communication
3. **Implement Data Ingestion:** We'd provide an accessible HTTP endpoint to enable billing service ingest record for billing purpose
4. **Eventual Consistency through Event Queuing Approach -** 
5. Persist Records to our Logging Database
6. Aggregate Record for Audit and Reports Purpose

