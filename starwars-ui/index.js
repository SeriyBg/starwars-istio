const express = require('express')
const request = require('request')
const app = express()
const PORT = 3000
const FORWARD_HEADERS = ['user-agent', 'x-request-id', 'x-b3-traceid', 'x-b3-spanid', 'x-b3-parentspanid', 'x-b3-sampled', 'x-b3-flags', 'x-ot-span-context', 'x-debug']

function createOptions(url, req) {
    var headers = {};
    FORWARD_HEADERS.forEach(function(header) {
        var hVal = req.headers[header];
        if (hVal != null) {
            headers[header] = req.headers[header]
        }
    });
    return {
        url: url,
        headers: headers
    }
}

function tryParseJSON(body) {
    try {
        return JSON.parse(body)
    } catch(err) {
        return null
    }
}

app.use(express.static('public'));
app.set('view engine', 'ejs');

app.get('/', function (req, res) {  
  res.render('index')
});

app.get('/quote', function(req, res) {
    console.log("Requesting a character for a quote...");
    var quoteServiceUrl = process.env.QUOTE_SERVICE_URL || "http://localhost:8081"
    var options = createOptions(quoteServiceUrl + "/quote", req);
    request(options, function(err, response, body) {
        if (err) {
            res.render("quote", {quote: null, error: err})
            return;
        }
        if (!response) {
            res.render("quote", {quote: null, error: "Unable to parse response"})
            return;
        }
        console.log("Response: " + response.statusCode)
        var responseBody = tryParseJSON(response.body)
        if (responseBody != null) {
            res.render("quote", {quote: responseBody, error: null})
        } else {
            res.render("quote", {quote: null, error: response.body})
        }
    })
});

app.get('/dethstar', function(req, res) {
    console.log("Requesting death star to destriy a new planet...");
    var deathStarServiceUrl = process.env.DEATH_STAR_SERVICE_URL || "http://localhost:8082"
    var options = createOptions(deathStarServiceUrl + "/destroy", req);
    request(options, function(err, response, body) {
        if (err) {
            res.render("dethstar", {planet: null, error: err})
            return;
        }
        if (!response) {
            res.render("dethstar", {planet: null, error: "Unable to parse response"})
            return;
        }
        console.log("Response: " + response.statusCode)
        var responseBody = tryParseJSON(response.body)
        if (responseBody != null) {
            res.render("dethstar", {planet: responseBody, error: null})
        } else {
            res.render("dethstar", {planet: null, error: response.body})
        }
    })
});

app.listen(PORT, function () {
  console.log('Serving on port 3000...')
});
