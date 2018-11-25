const express = require('express')
const request = require('request')
const app = express()

app.use(express.static('public'));
app.set('view engine', 'ejs')

app.get('/', function (req, res) {  
  res.render('index')
})

app.get('/quote', function(req, res) {
    quoteServiceUrl = process.env.QUOTE_SERVICE_URL || "http://localhost:8081"
    request(quoteServiceUrl + "/quote", function(err, response, body) {
        if (err) {
            res.render("quote", {quote: null, error: err})
        }
        res.render("quote", {quote: JSON.parse(response.body), error: null})
    })
})

app.get('/dethstar', function(req, res) {
    deathStarServiceUrl = process.env.DEATH_STAR_SERVICE_URL || "http://localhost:8082"
})

app.listen(3000, function () {
  console.log('Serving on port 3000...')
})

