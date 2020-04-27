# Security Okta

This project shows how to use okta on micro-services. There are two microservices:

- `service-okta-banner`: create a banner object
- `service-okta-color`: generate a random colour

The banner service calls the colour to know which is the colour of the banner.

`request => banner => color => banner => response`

# Sending the same token across services

Both projects are secured by okta. 

This means the service banner needs to receive the auth token and send it together with the request to service colour. 

# Debug the token

https://oidcdebugger.com/

- Authorize URI: https://dev-429406.okta.com/oauth2/default/v1/authorize
- Redirect URI: https://oidcdebugger.com/debug
- Client ID: TO_DEFINE
- Scope: openid
- Response type: Token
Response mode: form_post

# References
- https://developer.okta.com/docs/guides/protect-your-api/springboot/before-you-begin/
- https://developer.okta.com/blog/2019/05/31/spring-security-authentication
- https://developer.okta.com/blog/2019/05/22/java-microservices-spring-boot-spring-cloud
- https://dev-429406.okta.com/
- https://github.com/okta/samples-java-spring/tree/master/resource-server