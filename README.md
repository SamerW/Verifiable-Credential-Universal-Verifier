# Simple Universal Verifier (SUV)
Zayed University as part of Sindibad project and Crossword Cybersecurity Plc as part of the eSSIF-lab project designed the SUV verifier and have published the design and APIs, and are making the source code open source. The SUV is designed to plug into an 
OpenId Connect (OIDC) Relying Party (RP), and to receive the new Verifiable Presentation (VP) claim 
format that is being defined by the OIDC Connect group. The SUV is given the VP, in any format, 
containing any type of proof/crypto and it will attempt to validate it. It does this by evaluating 
the VP type, Distributed Identifier (DID) method, and proof type, and determining which Verifier to 
call from configuration information. The RP will need to run multiple Verifiers that are capable of 
verifying the full range of VPs provided by the wallets it wishes to support. Once the VP and its 
embedded Verifiable Credentials (VCs) have been verified, the SUV calls the TRust mAnagement 
INfrastructure (TRAIN) API to see if the issuer of each VC are trusted, and if they are, it then 
calls the policyMatch API which takes the set of verified VCs, calls the public policy registry to 
pick up the RP’s presentation policy, then evaluates the VCs against the policy. If the VCs satisfy 
the policy then the RP is returned either a set of OIDC claims or verified VCs, depending upon a 
configuration parameter.

## Swagger generated Spring Boot Server
This server was generated by the [swagger-codegen](https://github.com/swagger-api/swagger-codegen) project.  
By using the [OpenAPI-Spec](https://github.com/swagger-api/swagger-core), you can easily generate a server stub.  
This is an example of building a swagger-enabled server in Java using the SpringBoot framework.

The underlying library integrating swagger to SpringBoot is [springdoc-openapi](https://github.com/springdoc/springdoc-openapi)

Start your server as an simple java application

You can view the api documentation in swagger-ui by pointing to  
http://localhost:8080/

Change default port value in application.properties

## License

Open source project licensed under the
[GNU Lesser General Public License v3](https://www.gnu.org/licenses/lgpl-3.0.txt).
