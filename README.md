# GIF RECEIVER

## What is this?
This magnificent web application was specially developed by Gromak Maxim as a test task for Alfa Bank.

## How to run this amazing app?
- Clone repo, using command:`git clone https://github.com/GromakMaxim/alfabank-test.git`  
- Switch to root folder and build project:`gradlew build`
- Created docker-image using the command: `docker-compose build`
- Run app: `docker-compose up`
- Enjoy!

***

## Endpoint
There is no specific endpoint, just type in browser smth like http://localhost:29999/?code=ANG.  
As a result, you will be redirected to a page with a suitable animated gif.
The "code" parameter takes currency codes as an argument (the list is located [here](https://docs.openexchangerates.org/docs/currencies-json))

***
## Notes
As it was said in the task, most of the parameters are taken out in `application.properties` file.  
There you can set up a personal api key for the services `openexchangerates.org` and `giphy.com`, the currency in relation to which the exchange rate looks, and so on.

